/*
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

/* First created by JCasGen Mon Apr 18 13:49:01 EEST 2016 */
package com.ontotext.s4.api.types.news;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Automatically generated type for com.ontotext.s4.api.types.news.Software
 * Updated by JCasGen Mon Apr 18 13:49:01 EEST 2016
 * XML source: ../desc/news_typesystem.xml
 * @generated */
public class Software extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Software.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Software() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Software(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Software(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Software(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: string

  /** getter for string - gets Feature <string> for type <com.ontotext.s4.api.types.news.Software>
   * @generated
   * @return value of the feature 
   */
  public String getString() {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.news.Software");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Software_Type)jcasType).casFeatCode_string);}
    
  /** setter for string - sets Feature <string> for type <com.ontotext.s4.api.types.news.Software> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setString(String v) {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_string == null)
      jcasType.jcas.throwFeatMissing("string", "com.ontotext.s4.api.types.news.Software");
    jcasType.ll_cas.ll_setStringValue(addr, ((Software_Type)jcasType).casFeatCode_string, v);}    
   
    
  //*--------------*
  //* Feature: class_feature

  /** getter for class_feature - gets Feature <class_feature> for type <com.ontotext.s4.api.types.news.Software>
   * @generated
   * @return value of the feature 
   */
  public String getClass_feature() {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_class_feature == null)
      jcasType.jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.news.Software");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Software_Type)jcasType).casFeatCode_class_feature);}
    
  /** setter for class_feature - sets Feature <class_feature> for type <com.ontotext.s4.api.types.news.Software> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setClass_feature(String v) {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_class_feature == null)
      jcasType.jcas.throwFeatMissing("class_feature", "com.ontotext.s4.api.types.news.Software");
    jcasType.ll_cas.ll_setStringValue(addr, ((Software_Type)jcasType).casFeatCode_class_feature, v);}    
   
    
  //*--------------*
  //* Feature: inst

  /** getter for inst - gets Feature <inst> for type <com.ontotext.s4.api.types.news.Software>
   * @generated
   * @return value of the feature 
   */
  public String getInst() {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_inst == null)
      jcasType.jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.news.Software");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Software_Type)jcasType).casFeatCode_inst);}
    
  /** setter for inst - sets Feature <inst> for type <com.ontotext.s4.api.types.news.Software> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setInst(String v) {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_inst == null)
      jcasType.jcas.throwFeatMissing("inst", "com.ontotext.s4.api.types.news.Software");
    jcasType.ll_cas.ll_setStringValue(addr, ((Software_Type)jcasType).casFeatCode_inst, v);}    
   
    
  //*--------------*
  //* Feature: type_feature

  /** getter for type_feature - gets Feature <type_feature> for type <com.ontotext.s4.api.types.news.Software>
   * @generated
   * @return value of the feature 
   */
  public String getType_feature() {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.news.Software");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Software_Type)jcasType).casFeatCode_type_feature);}
    
  /** setter for type_feature - sets Feature <type_feature> for type <com.ontotext.s4.api.types.news.Software> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setType_feature(String v) {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_type_feature == null)
      jcasType.jcas.throwFeatMissing("type_feature", "com.ontotext.s4.api.types.news.Software");
    jcasType.ll_cas.ll_setStringValue(addr, ((Software_Type)jcasType).casFeatCode_type_feature, v);}    
   
    
  //*--------------*
  //* Feature: sublcasses

  /** getter for sublcasses - gets Feature <sublcasses> for type <com.ontotext.s4.api.types.news.Software>
   * @generated
   * @return value of the feature 
   */
  public String getSublcasses() {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_sublcasses == null)
      jcasType.jcas.throwFeatMissing("sublcasses", "com.ontotext.s4.api.types.news.Software");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Software_Type)jcasType).casFeatCode_sublcasses);}
    
  /** setter for sublcasses - sets Feature <sublcasses> for type <com.ontotext.s4.api.types.news.Software> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSublcasses(String v) {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_sublcasses == null)
      jcasType.jcas.throwFeatMissing("sublcasses", "com.ontotext.s4.api.types.news.Software");
    jcasType.ll_cas.ll_setStringValue(addr, ((Software_Type)jcasType).casFeatCode_sublcasses, v);}    
   
    
  //*--------------*
  //* Feature: preferredLabel

  /** getter for preferredLabel - gets Feature <preferredLabel> for type <com.ontotext.s4.api.types.news.Software>
   * @generated
   * @return value of the feature 
   */
  public String getPreferredLabel() {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_preferredLabel == null)
      jcasType.jcas.throwFeatMissing("preferredLabel", "com.ontotext.s4.api.types.news.Software");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Software_Type)jcasType).casFeatCode_preferredLabel);}
    
  /** setter for preferredLabel - sets Feature <preferredLabel> for type <com.ontotext.s4.api.types.news.Software> 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPreferredLabel(String v) {
    if (Software_Type.featOkTst && ((Software_Type)jcasType).casFeat_preferredLabel == null)
      jcasType.jcas.throwFeatMissing("preferredLabel", "com.ontotext.s4.api.types.news.Software");
    jcasType.ll_cas.ll_setStringValue(addr, ((Software_Type)jcasType).casFeatCode_preferredLabel, v);}    
  }

    