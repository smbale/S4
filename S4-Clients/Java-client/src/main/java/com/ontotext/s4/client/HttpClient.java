/*
 * S4 Java client library
 * Copyright 2016 Ontotext AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ontotext.s4.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Client responsible for communication with the S4 API. Handles authentication,
 * serialization and deserialization of JSON request and response bodies.
 */
public class HttpClient {

    private final ObjectMapper MAPPER = new ObjectMapper().disable(
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .setInjectableValues(new InjectableValues.Std().addValue(HttpClient.class, this));

    private final XmlMapper XML_MAPPER = (XmlMapper)new XmlMapper().disable(
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .setInjectableValues(new InjectableValues.Std().addValue(HttpClient.class, this));

    /**
    * The standard base URI for the S4 API.
    */
    public static final URL DEFAULT_BASE_URL;
    static {
        try {
            DEFAULT_BASE_URL = new URL("https://text.s4.ontotext.com/");
        } catch(MalformedURLException e) {
            // can't happen
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
    * The HTTP basic authentication header that will be appended to all requests.
    */
    private String authorizationHeader;

    /**
    * The base URL that will be used to resolve any relative request URIs.
    */
    private URL baseUrl;

    /**
    * Create a client that uses the {@link #DEFAULT_BASE_URL default base URL}.
    *
    * @param apiKeyId S4 API key identifier for authentication
    * @param keySecret S4 API key secret
    */
    public HttpClient(String apiKeyId, String keySecret) {
    	this(DEFAULT_BASE_URL, apiKeyId, keySecret);
    }

    /**
    * Create a client using a specified base URL (for advanced use only - the default URL will work for all normal cases).
    *
    * @param url API base URL
    * @param apiKeyId API key identifier for authentication
    * @param keySecret API key secret
    */
    public HttpClient(URL url, String apiKeyId, String keySecret) {
        baseUrl = url;
        try {
            // HTTP header is "Basic base64(username:password)"
            authorizationHeader = "Basic " + DatatypeConverter.printBase64Binary((apiKeyId + ":" + keySecret).getBytes("UTF-8"));
        } catch(UnsupportedEncodingException uee) {
            // should never happen
            throw new RuntimeException("JVM claims not to support UTF-8 encoding...", uee);
        }
    }

    public URL getBaseUrl() {
    	return baseUrl;
    }

    /**
    * Make an API request and parse the JSON response into a new object.
    *
    * @param target the URL to request (relative URLs will resolve against the {@link #getBaseUrl() base URL}).
    * @param method the request method (GET, POST, DELETE, etc.)
    * @param responseType the Java type corresponding to a successful response message for this URL
    * @param requestBody the object that should be serialized to JSON as the request body.
    *                    If <code>null</code>, no request body is sent
    * @param extraHeaders any additional HTTP headers, specified as an alternating sequence of header names and values
    * @param <T> Type
    * @return for a successful response, the deserialized response body, or <code>null</code> for a 201 response
    * @throws HttpClientException if an exception occurs during processing,
    *           or the server returns a 4xx or 5xx error response
    */
    public <T> T request(
            String target, String method, TypeReference<T> responseType, Object requestBody, Map<String, String> extraHeaders)
            throws HttpClientException {

        try {
            HttpURLConnection connection = sendRequest(target, method, requestBody, extraHeaders);
            return readResponseOrError(connection, responseType, true, extraHeaders);
        } catch(IOException e) {
            throw new HttpClientException(e);
        }
    }

    /**
    * Make an API request and return the raw data from the response as an
    * InputStream.
    *
    * @param target the URL to request (relative URLs will resolve against the {@link #getBaseUrl() base URL}).
    * @param method the request method (GET, POST, DELETE, etc.)
    * @param requestBody the object that should be serialized to JSON as the request body.
    *          If <code>null</code> no request body is sent
    * @param extraHeaders any additional HTTP headers, specified as an alternating sequence of header names and values
    * @return for a successful response, the response stream, or <code>null</code> for a 201 response
    * @throws HttpClientException if an exception occurs during processing,
    *           or the server returns a 4xx or 5xx error response
    */
    public InputStream requestForStream(String target, String method, Object requestBody, Map<String, String> extraHeaders)
            throws HttpClientException {

        try {
            HttpURLConnection connection = sendRequest(target, method, requestBody, extraHeaders);
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                // successful response with no content
                return null;
            } else if(responseCode >= 400) {
                readError(connection);
                return null; // not reachable, readError always throws exception
            } else if(responseCode >= 300) {
                // redirect - all redirects we care about from the S4
                // APIs are 303. We have to follow them manually to make
                // authentication work properly.
                String location = connection.getHeaderField("Location");
                // consume body
                InputStream stream = connection.getInputStream();
                IOUtils.copy(stream, new NullOutputStream());
                IOUtils.closeQuietly(stream);
                // follow the redirect
                return requestForStream(location, method, requestBody, extraHeaders);
            } else {
                return connection.getInputStream();
            }
        } catch(IOException e) {
            throw new HttpClientException(e);
        }
    }

    /**
    * Handles the sending side of an HTTP request, returning a connection
    * from which the response (or error) can be read.
    */
    private HttpURLConnection sendRequest(String target, String method, Object requestBody, Map<String, String> extraHeaders)
            throws IOException {

        URL requestUrl = new URL(baseUrl, target);
        HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();
        connection.setRequestMethod(method);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestProperty("Authorization", authorizationHeader);

        for (String key : extraHeaders.keySet()) {
            connection.setRequestProperty(key, extraHeaders.get(key));
        }

        if(requestBody != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStream out = connection.getOutputStream();
            try {
                MAPPER.writeValue(out, requestBody);
            } finally {
            	out.flush();
                out.close();
            }
        }
        return connection;
    }

    /**
    * Read a response or error message from the given connection, handling any 303 redirect responses
    * if <code>followRedirects</code> is true.
    */
    private <T> T readResponseOrError(HttpURLConnection connection, TypeReference<T> responseType, boolean followRedirects, Map<String, String> extraHeaders)
          throws HttpClientException {

        InputStream stream = null;
        try {
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                // successful response with no content
                return null;
            }
            String encoding = connection.getContentEncoding();
            if("gzip".equalsIgnoreCase(encoding)) {
                stream = new GZIPInputStream(connection.getInputStream());
            } else {
                stream = connection.getInputStream();
            }

            if(responseCode < 300 || responseCode >= 400 || !followRedirects) {
                try {
                    return MAPPER.readValue(stream, responseType);
                } finally {
                    stream.close();
                }
            } else {
                // redirect - all redirects we care about from the S4
                // APIs are 303. We have to follow them manually to make
                // authentication work properly.
                String location = connection.getHeaderField("Location");
                // consume body
                IOUtils.copy(stream, new NullOutputStream());
                IOUtils.closeQuietly(stream);
                // follow the redirect
                return get(location, responseType, extraHeaders);
            }
        } catch(Exception e) {
            readError(connection);
            return null; // unreachable, as readError always throws exception
        }
    }

    /**
    * Read an error response from the given connection and throw a
    * suitable {@link HttpClientException}. This method always throws an
    * exception, it will never return normally.
    */
    private void readError(HttpURLConnection connection) throws HttpClientException {
        InputStream stream;
        try {
            String encoding = connection.getContentEncoding();
            if("gzip".equalsIgnoreCase(encoding)) {
                stream = new GZIPInputStream(connection.getInputStream());
            } else {
                stream = connection.getInputStream();
            }

            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");

            try {
                JsonNode errorNode = null;
                if(connection.getContentType().contains("json")) {
                    errorNode = MAPPER.readTree(stream);
                } else if(connection.getContentType().contains("xml")) {
                    errorNode = XML_MAPPER.readTree(stream);
                }

                throw new HttpClientException("Server returned response code " + connection.getResponseCode(), errorNode);

                } finally {
                    reader.close();
                }
            } catch(HttpClientException e2) {
                 throw e2;
        } catch(Exception e2) {
            throw new HttpClientException("Error communicating with server", e2);
        }
    }

    /**
    * Perform an HTTP GET request, parsing the JSON response to create a new object.
    *
    * @param target the URL to request (relative URLs will resolve against the {@link #getBaseUrl() base URL}).
    * @param responseType the Java type corresponding to a successful response message for this URL
    * @param extraHeaders any additional HTTP headers, specified as an alternating sequence of header names and values
    * @return for a successful response, the deserialized response body, or <code>null</code> for a 201 response
    * @throws HttpClientException if an exception occurs during
    *           processing, or the server returns a 4xx or 5xx error
    *           response (in which case the response JSON message will be
    *           available as a {@link JsonNode} in the exception).
    */
    public <T> T get(String target, TypeReference<T> responseType, Map<String, String> extraHeaders) throws HttpClientException {
        return request(target, "GET", responseType, null, extraHeaders);
    }
}
