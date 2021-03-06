# Copyright 2016 Ontotext AD
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from SPARQLWrapper import SPARQLWrapper

queryString = """
PREFIX dc: <http://purl.org/dc/elements/1.1/>
INSERT { <http://example/egbook> dc:title  "This is an example title" }
WHERE {}
"""

sparql = SPARQLWrapper("https://rdf.s4.ontotext.com/<user-id>/" +
                       "<db-id>/repositories/<repo-name>/statements")

sparql.setQuery(queryString)

# Necessary - An update operation cannot be executed without ID credentials
sparql.setCredentials("<s4-api-key>", "<s4-key-secret>")

sparql.method = "POST"

sparql.query()
