/******************************************************************************
 * Project:    Extensible Access Control Framework for Cloud based Applications.
 *                     http://ais.seecs.nust.edu.pk/project/ 
 * Developed by: KTH- Applied Information Security Lab (AIS), 
 *                       NUST-SEECS, H-12 Campus, 
 *                       Islamabad, Pakistan. 
 *                       www.ais.seecs.nust.edu.pk
 * Funded by: National ICT R&D Fund, Ministry of Information Technology & Telecom,
 *                  http://www.ictrdf.org.pk/
 * Copyright (c) 2013-2015 All Rights Reserved, AIS-SEECS NUST & National ICT R&D Fund

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy and/or modify the Software, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. 

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *****************************************************************************/
package com.aislab.accesscontrol.core.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.aislab.accesscontrol.core.entities.EnvAttrValues;
import com.aislab.accesscontrol.core.entities.Environment;
import com.aislab.accesscontrol.core.entities.EnvironmentAttribute;
import com.aislab.accesscontrol.core.ui.dao.EnvAttrValuesDAO;
import com.aislab.accesscontrol.core.ui.dao.EnvironmentAttributeDAO;
import com.aislab.accesscontrol.core.ui.dao.EnvironmentDAO;

/**
 * A session scoped, managed bean for user interfaces related to
 * {@code Environment}
 * 
 * @author Arjumand Fatima <09bicseafatima@seecs.edu.pk>
 * @version 1.0
 * 
 * 
 * 
 */
@ManagedBean
@SessionScoped
public class EnvironmentController {
	/**
	 * A static {@code Logger} instance for logging
	 */
	static Logger log = Logger.getLogger(EnvironmentController.class.getName());

	/**
	 * A String variable to store the value of {@code EnvAttrId} attribute of
	 * {@code EnvironmentAttribute} while adding a new
	 * {@code EnvironmentAttribute} corresponding to an existing
	 * {@code Environment} instance.
	 */
	String newAttributeName = null;
	/**
	 * A String variable to store the value of {@code EnvAttrValue} attribute of
	 * {@code EnvAttrValues} while adding a new {@code EnvironmentAttribute}
	 * corresponding to an existing {@code Environment} instance.
	 */
	String newAttributeValue = null;
	/**
	 * A boolean variable to check whether any {@code Environment} instance is
	 * selected by the user so that the corresponding
	 * {@code EnvironmentAttribute} instance can be added. By default it is set
	 * to {@code TRUE} so that the corresponding add button is disabled while it
	 * is becomes {@code FALSE} if any {@code Environment} instance is selected
	 * in the user interface.
	 */
	boolean attrbtn = true;
	/**
	 * A boolean variable to check whether any {@code EnvironmentAttribute}
	 * instance is selected by the user so that the corresponding
	 * {@code EnvAttrValue} instance can be added. By default it is set to
	 * {@code TRUE} so that the corresponding add button is disabled while it is
	 * becomes {@code FALSE} if any {@code EnvironmentAttribute} instance is
	 * selected in the user interface.
	 */
	boolean attrValuebtn = true;
	/**
	 * A boolean variable to check whether the {@code Save} or {@code Cancel}
	 * button was pressed in a modification operation. By default it is set to
	 * {@code TRUE} while it is becomes {@code FALSE} if {@code Save} is
	 * pressed.
	 * 
	 */

	boolean operationFail = true;
	/**
	 * An instance of {@code EnvironmentDAO} for using methods to access data
	 * related to {@code Environment}
	 */
	EnvironmentDAO daoEnvironment = new EnvironmentDAO();
	/**
	 * An instance of {@code EnvironmentAttributeDAO} for using methods to
	 * access data related to {@code EnvironmentAttribute}
	 */
	EnvironmentAttributeDAO daoEnvironmentAttribute = new EnvironmentAttributeDAO();
	/**
	 * An instance of {@code EnvAttrValuesDAO} for using methods to access data
	 * related to {@code EnvAttrValues}
	 */
	EnvAttrValuesDAO daoEnvironmentAttributeValue = new EnvAttrValuesDAO();

	/**
	 * A String variable to store the value of {@code dataType} attribute of
	 * {@code EnvironmentAttribute} while adding a new
	 * {@code EnvironmentAttribute} corresponding to an exiting
	 * {@code Environment} instance.
	 */
	String selectedDataType = null;
	/**
	 * A {@code List} of {@code String} representing the available data types
	 * for adding a new {@code EnvironmentAttribute} corresponding to an exiting
	 * {@code Environment} instance.
	 */
	List<String> dataTypeList = Arrays.asList("String", "Integer", "Boolean",
			"anyURI");

	/**
	 * An instance of {@code Environment} used to store the {@code Environment}
	 * selected by the user from the user interface.
	 */
	public Environment selectedEnvironment;;
	/**
	 * An ArrayList of {@code Environment} used to display all the existing
	 * {@code Environment}s stored in the database.
	 */
	public ArrayList<Environment> environmentList = new ArrayList<Environment>();

	/**
	 * An ArrayList of {@code EnvironmentAttribute} used to display all the
	 * existing {@code EnvironmentAttribute}(s) stored in the database. These
	 * {@code EnvironmentAttribute}(s) are related to a particular
	 * {@code Environment} instance stored in {@code selectedEnvironment}.
	 */

	public ArrayList<EnvironmentAttribute> environmentAttributeList = new ArrayList<EnvironmentAttribute>();
	/**
	 * An instance of {@code EnvironmentAttribute} used to store the
	 * {@code EnvironmentAttribute} selected by the user from the user
	 * interface.
	 */
	public EnvironmentAttribute selectedEnvironmentAttributes;

	/**
	 * An ArrayList of {@code EnvAttrValues} used to display all the existing
	 * {@code EnvAttrValues}(s) stored in the database. These
	 * {@code EnvAttrValues}(s) are related to a particular
	 * {@code EnvironmentAttribute} instance stored in
	 * {@code selectedEnvironmentAttributes}.
	 */
	public ArrayList<EnvAttrValues> environmentAttributeValueList;
	/**
	 * An instance of {@code EnvAttrValues} used to store the
	 * {@code EnvAttrValues} selected by the user from the user interface.
	 */
	public EnvAttrValues selectedEnvironmentAttributeValue;

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/

	/**
	 * Sets the {@code selectedDataType} property to {@code String} argument
	 * 
	 * @param selectedDataType
	 */
	public void setSelectedDataType(String selectedDataType) {
			this.selectedDataType = selectedDataType;
		log.debug("Set  selectedDataType: " + selectedDataType);

	}

	/**
	 * Sets the {@code dataTypeList} property to {@code List} argument
	 * 
	 * @param dataTypeList
	 */
	public void setDataTypeList(List<String> dataTypeList) {
		this.dataTypeList = dataTypeList;
		log.debug("Set  dataTypeList: " + dataTypeList);
	}

	/**
	 * Sets the {@code attrbtn} property to {@code boolean} argument.
	 * 
	 * @param attrbtn
	 */
	public void setAttrbtn(boolean attrbtn) {
		this.attrbtn = attrbtn;
		log.debug("Set  attrbtn: " + attrbtn);
	}

	/**
	 * Sets the {@code attrValuebtn} property to {@code boolean} argument.
	 * 
	 * @param attrValuebtn
	 */
	public void setAttrValuebtn(boolean attrValuebtn) {
		this.attrValuebtn = attrValuebtn;
		log.debug("Set  attrValuebtn: " + attrValuebtn);
	}

	/**
	 * Sets the {@code operationFail} property to {@code boolean} argument.
	 * 
	 * @param operationFail
	 */

	public void setOperationFail(boolean operationFail) {
		this.operationFail = operationFail;
		log.debug("Set  operationFail: " + operationFail);
	}

	/**
	 * Sets the {@code newAttributeName} property to {@code String} argument.
	 * 
	 * @param newAttributeName
	 */
	public void setNewAttributeName(String newAttributeName) {
		this.newAttributeName = newAttributeName;
		log.debug("Set  newAttributeName: " + newAttributeName);
	}

	/**
	 * Sets the {@code newAttributeValue} property to {@code String} argument.
	 * 
	 * @param newAttributeValue
	 */
	public void setNewAttributeValue(String newAttributeValue) {
		this.newAttributeValue = newAttributeValue;
		log.debug("Set  newAttributeValue: " + newAttributeValue);
	}

	/**
	 * Sets the {@code daoEnvironment} property to {@code EnvironmentDAO}
	 * argument.
	 * 
	 * @param daoEnvironment
	 */
	public void setDaoEnvironment(EnvironmentDAO daoEnvironment) {
	this.daoEnvironment = daoEnvironment;
		log.debug("Set  daoEnvironment: " + daoEnvironment);
	}

	/**
	 * Sets the {@code selectedEnvironmentAttributeValue} property to
	 * {@code EnvAttrValues} argument.
	 * 
	 * @param selectedEnvironmentAttributeValue
	 */
	public void setSelectedEnvironmentAttributeValue(
			EnvAttrValues selectedEnvironmentAttributeValue) {
		this.selectedEnvironmentAttributeValue = selectedEnvironmentAttributeValue;
		log.debug("Set  selectedEnvironmentAttributeValue: "
				+ selectedEnvironmentAttributeValue);
	}

	/**
	 * Sets the {@code daoEnvironmentAttribute} property to
	 * {@code EnvironmentAttributeDAO} argument.
	 * 
	 * @param daoEnvironmentAttribute
	 */
	public void setDaoEnvironmentAttribute(
			EnvironmentAttributeDAO daoEnvironmentAttribute) {
	this.daoEnvironmentAttribute = daoEnvironmentAttribute;
		log.debug("Set  daoEnvironmentAttribute: " + daoEnvironmentAttribute);
	}

	/**
	 * Sets the {@code daoEnvironmentAttributeValue} property to
	 * {@code EnvAttrValuesDAO} argument.
	 * 
	 * @param daoEnvironmentAttributeValue
	 */
	public void setDaoEnvironmentAttributeValue(
			EnvAttrValuesDAO daoEnvironmentAttributeValue) {
			this.daoEnvironmentAttributeValue = daoEnvironmentAttributeValue;
		log.debug("Set  daoEnvironmentAttributeValue: "
				+ daoEnvironmentAttributeValue);
	}

	/**
	 * Sets the {@code selectedEnvironment} property to {@code Environment}
	 * argument.
	 * 
	 * @param selectedEnvironment
	 */
	public void setSelectedEnvironment(Environment selectedEnvironment) {
			this.setAttrbtn(false);
		this.environmentAttributeValueList = new ArrayList<EnvAttrValues>();
		this.selectedEnvironmentAttributes = null;
		this.selectedEnvironment = selectedEnvironment;

		log.debug("Set  selectedEnvironment: " + selectedEnvironment);
	}

	/**
	 * Sets the {@code EnvironmentList} property to {@code Environment}
	 * argument.
	 * 
	 * @param EnvironmentList
	 */
	public void setEnvironmentList(ArrayList<Environment> environmentList) {
			this.environmentList = environmentList;
		log.debug("Set  environmentList: " + environmentList);
	}

	/**
	 * Sets the {@code EnvironmentAttributeList} property to
	 * {@code EnvironmentAttribute} argument.
	 * 
	 * @param EnvironmentAttributeList
	 */
	public void setEnvironmentAttributeList(
			ArrayList<EnvironmentAttribute> environmentAttributeList) {
			this.environmentAttributeList = environmentAttributeList;
		log.debug("Set  environmentAttributeList: " + environmentAttributeList);
	}

	/**
	 * Sets the {@code selectedEnvironmentAttributes} property to
	 * {@code EnvironmentAttribute} argument.
	 * 
	 * @param selectedEnvironmentAttributes
	 */
	public void setSelectedEnvironmentAttributes(
			EnvironmentAttribute selectedEnvironmentAttributes) {
			this.setAttrValuebtn(false);
		this.selectedEnvironmentAttributes = selectedEnvironmentAttributes;
		log.debug("Set  selectedEnvironmentAttributes: "
				+ selectedEnvironmentAttributes);
	}

	/**
	 * Sets the {@code EnvironmentAttributeValueList} property to
	 * {@code EnvAttrValues} argument.
	 * 
	 * @param EnvironmentAttributeValueList
	 */

	public void setEnvironmentAttributeValueList(
			ArrayList<EnvAttrValues> environmentAttributeValueList) {
			this.environmentAttributeValueList = environmentAttributeValueList;
		log.debug("Set  environmentAttributeValueList: "
				+ environmentAttributeValueList);
	}

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/

	/**
	 * Returns the value of {@code selectedDataType} property
	 * 
	 * @return selectedDataType
	 */
	public String getSelectedDataType() {
		log.debug("Get  selectedDataType: " + selectedDataType);
		return selectedDataType;
	}

	/**
	 * Returns the value of {@code dataTypeList} property
	 * 
	 * @return dataTypeList
	 */
	public List<String> getDataTypeList() {
		log.debug("Get  dataTypeList: " + dataTypeList);
		return dataTypeList;
	}

	/**
	 * Returns {@code FALSE} if an instance of the {@code Environment} is
	 * selected by the user otherwise {@code TRUE}.
	 * 
	 * @return attrbtn
	 */

	public boolean isAttrbtn() {
		log.debug("Get  attrbtn: " + attrbtn);
		return attrbtn;
	}

	/**
	 * Returns {@code FALSE} if an instance of {@code EnvironmentAttribute} is
	 * selected by the user otherwise {@code TRUE}.
	 * 
	 * @return attrValuebtn
	 */
	public boolean isAttrValuebtn() {
		log.debug("Get  attrValuebtn: " + attrValuebtn);
		return attrValuebtn;
	}

	/**
	 * Returns {@code FALSE} if a {@code Save} button was pressed otherwise
	 * {@code TRUE}.
	 * 
	 * @return operationFail
	 */
	public boolean isOperationFail() {
			log.debug("Get  operationFail: " + operationFail);
		return operationFail;
	}

	/**
	 * Returns the value of {@code newAttributeName} property
	 * 
	 * @return newAttributeName
	 */
	public String getNewAttributeName() {
			log.debug("Get  newAttributeName: " + newAttributeName);
		return newAttributeName;
	}

	/**
	 * Returns the value of {@code newAttributeValue} property
	 * 
	 * @return newAttributeValue
	 */
	public String getNewAttributeValue() {
			log.debug("Get  newAttributeValue: " + newAttributeValue);
		return newAttributeValue;
	}

	/**
	 * Returns the value of {@code selectedEnvironmentAttributeValue} property
	 * 
	 * @return selectedEnvironmentAttributeValue
	 */
	public EnvAttrValues getSelectedEnvironmentAttributeValue() {
			log.debug("Get  selectedEnvironmentAttributeValue: "
				+ selectedEnvironmentAttributeValue);
		return selectedEnvironmentAttributeValue;
	}

	/**
	 * Returns the value of {@code daoEnvironment} property
	 * 
	 * @return daoEnvironment
	 */
	public EnvironmentDAO getDaoEnvironment() {
			log.debug("Get  daoEnvironment: " + daoEnvironment);
		return daoEnvironment;
	}

	/**
	 * Returns the value of {@code daoEnvironmentAttribute } property
	 * 
	 * @return daoEnvironmentAttribute
	 */

	public EnvironmentAttributeDAO getDaoEnvironmentAttribute() {
			log.debug("Get  daoEnvironmentAttribute: " + daoEnvironmentAttribute);
		return daoEnvironmentAttribute;
	}

	/**
	 * Returns the value of {@code daoEnvironmentAttributeValue} property
	 * 
	 * @return daoEnvironmentAttributeValue
	 */
	public EnvAttrValuesDAO getDaoEnvironmentAttributeValue() {
			log.debug("Get  daoEnvironmentAttributeValue: "
				+ daoEnvironmentAttributeValue);
		return daoEnvironmentAttributeValue;
	}

	/**
	 * Returns the value of {@code selectedEnvironment} property
	 * 
	 * @return selectedEnvironment
	 */
	public Environment getSelectedEnvironment() {
			log.debug("Get  selectedEnvironment: " + selectedEnvironment);

		return selectedEnvironment;

	}

	/**
	 * Returns the {@code ArrayList} of {@code Environment} instances available.
	 * 
	 * @return EnvironmentList
	 */
	public ArrayList<Environment> getEnvironmentList() {
			log.debug("Getting  environmentList ");
		return (ArrayList<Environment>) daoEnvironment.selectEnvironment();
	}

	/**
	 * Returns the value of {@code EnvironmentAttributeList } property
	 * corresponding to the {@code selectedEnvironment}
	 * 
	 * @return EnvironmentAttributeList
	 */
	public ArrayList<EnvironmentAttribute> getEnvironmentAttributeList() {
			if (this.selectedEnvironment != null)
			return (ArrayList<EnvironmentAttribute>) daoEnvironmentAttribute
					.selectEnvironmentAttributes(selectedEnvironment
							.getPkEnvironment());
		log.debug("Get  environmentAttributeList: " + environmentAttributeList);
		return this.environmentAttributeList;

	}

	/**
	 * Returns the value of {@code selectedEnvironmentAttributes} property
	 * 
	 * @return selectedEnvironmentAttributes
	 */
	public EnvironmentAttribute getSelectedEnvironmentAttributes() {
		log.debug("Get  selectedEnvironmentAttributes: "
				+ selectedEnvironmentAttributes);
		return selectedEnvironmentAttributes;
	}

	/**
	 * Returns the value of {@code EnvironmentAttributeValueList} property
	 * 
	 * @return EnvironmentAttributeValueList
	 */

	public ArrayList<EnvAttrValues> getEnvironmentAttributeValueList() {

	if (this.selectedEnvironmentAttributes != null)
			return (ArrayList<EnvAttrValues>) new EnvAttrValuesDAO()
					.populateEnvValueList(selectedEnvironmentAttributes
							.getPkEnvAttr());
		log.debug("Get  environmentAttributeValueList: "
				+ environmentAttributeValueList);
		return this.environmentAttributeValueList;

	}

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/
	/**
	 * Opens the
	 * {@code /System Learning/Environment/Add/Env_AddEnvironmentAttributeValue.xhtml}
	 * file in a Primefaces {@code  Dialog} component which offers the
	 * functionality of adding a new {@code EnvAttrValues} instance
	 * corresponding to an @{code EnvironmentAttribute} of an
	 * {@code Environment} instance.
	 */
	public void addEnvironmentAttributeValue() {
		
		RequestContext context = RequestContext.getCurrentInstance();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("dynamic", true);
		options.put("height", 115);
		options.put("width", 565);
		options.put("contentHeight", 100);
		options.put("contentWidth", 550);
		options.put("modal", true);
		options.put("draggable", false);

		context.openDialog(
				"/System Learning/Environment/Add/Env_AddEnvironmentAttributeValue",
				options, null);
		this.newAttributeValue = null;

	}

	/**
	 * Opens the
	 * {@code /System Learning/Environment/Add/Env_AddEnvironmentAttribute.xhtml}
	 * file in a Primefaces {@code  Dialog} component which offers the
	 * functionality of adding a new instance of @{code EnvironmentAttribute}
	 * corresponding to an existing {@code Environment} instance.
	 */
	public void addEnvironmentAttribute() {

		RequestContext context = RequestContext.getCurrentInstance();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("dynamic", true);
		options.put("height", 250);
		options.put("width", 565);
		options.put("contentHeight", 230);
		options.put("contentWidth", 550);
		options.put("modal", true);
		options.put("draggable", false);

		context.openDialog(
				"/System Learning/Environment/Add/Env_AddEnvironmentAttribute",
				options, null);
		this.newAttributeName = null;

	}

	/**
	 * Opens the {@code /System Learning/Environment/Add/Env_AddEnvironment.xhtml}
	 * page for creating a new {@code Environment} instance.
	 * 
	 * @return
	 */
	public void addEnvironment() {

		RequestContext context = RequestContext.getCurrentInstance();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("dynamic", true);
		options.put("height", 800);
		options.put("width", 1000);
		options.put("contentHeight", 750);
		options.put("contentWidth", 950);
		options.put("modal", true);
		options.put("draggable", false);

		context.openDialog("/System Learning/Environment/Add/Env_AddEnvironment",
				options, null);
	}

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/
	/**
	 * Deletes an instance of {@code EnvironmentAttribute} stored in
	 * {@code selectedEnvironmentAttributes} property
	 */
	public void deleteEnvironmentAttribute() {
if(selectedEnvironmentAttributes != null)		
{	this.daoEnvironmentAttribute
				.deleteEnvironmentAttribute(this.selectedEnvironmentAttributes
						.getPkEnvAttr());

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(" Successful Execution",
						"Deleted Successfully"));
		this.attrValuebtn = true;
		log.info("Environment attribute deleted successfully.");
}
else 
{
	log.info("Environment Attribute was not deleted successfully.");
}
	}

	/**
	 * Deletes the instance of {@code Environment} stored in
	 * {@code selectedEnvironment} property
	 */
	public void deleteEnvironment() {
if(this.selectedEnvironment != null)		
{		this.daoEnvironment.deleteEnvironment(this.selectedEnvironment
				.getPkEnvironment());

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(" Successful Execution",
						"Deleted Successfully"));
		this.attrbtn = true;
		this.attrValuebtn = true;
		log.info("Environment deleted successfully.");

}
else 
{
	log.info("Environment was not deleted successfully.");
}
	}

	/**
	 * Deletes the instance of {@code EnvAttrValues} stored in
	 * {@code selectedEnvironmentAttributeValue} property
	 */
	public void deleteEnvironmentAttributeValue() {

	if(this.selectedEnvironmentAttributeValue != null)
	{	this.daoEnvironmentAttributeValue
				.deleteEnvironmentAttributeValue(this.selectedEnvironmentAttributeValue
						.getPkEnvAttrVal());

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(" Successful Execution",
						"Deleted Successfully"));
		log.info("Environment attribute value deleted successfully.");
	}
	else 
	{
		log.info("Environment Attribute Value was not deleted successfully.");
	}
	}

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/
	/**
	 * Opens the
	 * {@code /System Learning/Environment/Update/Env_UpdateEnvironment.xhtml}
	 * file in a Primefaces {@code  Dialog} component which offers the
	 * functionality of updating the name and description of an existing
	 * {@code Environment} instance.
	 */
	public void updateEnvironment() {

		RequestContext context = RequestContext.getCurrentInstance();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("dynamic", true);
		options.put("height", 230);
		options.put("width", 580);
		options.put("contentHeight", 210);
		options.put("contentWidth", 565);
		options.put("modal", true);
		options.put("draggable", false);

		context.openDialog(
				"/System Learning/Environment/Update/Env_UpdateEnvironment",
				options, null);
	}

	/**
	 * Opens the
	 * {@code /System Learning/Environment/Update/Env_UpdateEnvironmentAttribute.xhtml}
	 * file in a Primefaces {@code  Dialog} component which offers the
	 * functionality of updating the existing {@code EnvironmentAttribute} of an
	 * {@code Environment} instance.
	 */
	public void updateEnvironmentAttribute() {
			RequestContext context = RequestContext.getCurrentInstance();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("dynamic", true);
		options.put("height", 130);
		options.put("width", 580);
		options.put("contentHeight", 115);
		options.put("contentWidth", 565);
		options.put("modal", true);
		options.put("draggable", false);

		context.openDialog(
				"/System Learning/Environment/Update/Env_UpdateEnvironmentAttribute",
				options, null);

	}

	/**
	 * Opens the
	 * {@code /System Learning/Environment/Update/Env_UpdateEnvironmentAttributeValue.xhtml}
	 * file in a Primefaces {@code  Dialog} component which offers the
	 * functionality of updating the value of an existing {@code EnvAttrValues}
	 * corresponding to an @{code EnvironmentAttribute} of an
	 * {@code Environment} instance.
	 */
	public void updateEnvironmentAttributeValue() {

		RequestContext context = RequestContext.getCurrentInstance();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("dynamic", true);
		options.put("height", 130);
		options.put("width", 580);
		options.put("contentHeight", 115);
		options.put("contentWidth", 565);
		options.put("modal", true);
		options.put("draggable", false);

		context.openDialog(
				"/System Learning/Environment/Update/Env_UpdateEnvironmentAttributeValue",
				options, null);

	}

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/
	/**
	 * Saves the updated value of {@code selectedEnvironmentAttributeValue}
	 * property in the database.
	 */
	public void saveUpdateEnvironmentAttributeValue() {

		RequestContext context = RequestContext.getCurrentInstance();

		if (this.selectedEnvironmentAttributeValue.getEnvAttrValue() == null) {
			
			context.openDialog("/Misc/ValueWarning");

			return;

		} else {

			this.daoEnvironmentAttributeValue.updateEnvAttrValue(
					this.selectedEnvironmentAttributeValue.getPkEnvAttrVal(),
					this.selectedEnvironmentAttributeValue.getEnvAttrValue());
			
			context.closeDialog(this);

			this.operationFail = false;
			log.info("Updated environment attribute value saved successfully.");

			return;
		}

	}

	/**
	 * Saves the updated value of {@code selectedEnvironmentAttributes} property
	 * in the database.
	 */
	public void saveUpdateEnvironmentAttribute() {

		RequestContext context = RequestContext.getCurrentInstance();

		if (this.selectedEnvironmentAttributes.getEnvAttrId() == null) {
		
			context.openDialog("/Misc/NameWarning");

			return;

		} else {
			
			this.daoEnvironmentAttribute.updateEnvironmentAttr(
					this.selectedEnvironmentAttributes.getPkEnvAttr(),
					this.selectedEnvironmentAttributes.getEnvAttrId());
				context.closeDialog(this);
				log.info("Updated environment attribute saved successfully.");
				
			return;
		}

	}

	/**
	 * Saves the updated value of {@code selectedEnvironment} property in the
	 * database.
	 */
	public void saveUpdateEnvironment() {
		
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.selectedEnvironment.getEnvironmentName() == null) {
		
			context.openDialog("/Misc/NameWarning");
			return;

		} else {
			
			this.daoEnvironment.updateEnvironment(
					this.selectedEnvironment.getPkEnvironment(),
					this.selectedEnvironment.getDescription(),
					this.selectedEnvironment.getEnvironmentName());
			
			context.closeDialog(this);
			this.operationFail = false;
			log.info("Updated environment saved successfully.");

			return;
		}

	}

	/**
	 * Saves the newly added instance of {@code EnvAttrValues}in the database
	 * corresponding to an existing {@code EnvironmentAttribute} instance
	 * referred by {@code selectedEnvironmentAttributes}
	 */
	public void saveAddEnvironmentAttributeValue() {
		
		RequestContext context = RequestContext.getCurrentInstance();

		if (this.newAttributeValue == null) {
			log.info("Environment Attribute Value was not added successfully.");

			context.openDialog("/Misc/ValueWarning");

			return;

		} else {
		
			this.daoEnvironmentAttributeValue.createEnvAttrValue(
					this.selectedEnvironmentAttributes, this.newAttributeValue);
				context.closeDialog(this);
			log.info("Environment Attribute Value Added Successfully.");

			context.showMessageInDialog(new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Successful Execution",
					"Environment Attribute Value Added Successfully."));

			this.newAttributeValue = null;
			this.operationFail = false;
			return;
		}

	}

	/**
	 * Saves the newly added instance of {@code EnvironmentAttribute}in the
	 * database corresponding to an existing {@code Environment} instance
	 * referred by {@code selectedEnvironment}
	 */
	public void saveAddEnvironmentAttribute() {
		
		RequestContext context = RequestContext.getCurrentInstance();

		if (this.newAttributeName == null) {

			context.openDialog("/Misc/NameWarning");
			log.info("Environment attribute was not saved successfully.");

			return;

		} else {
			
			this.daoEnvironmentAttribute.createEnvironmentAttrValue(
					this.selectedEnvironment, this.newAttributeName,
					this.selectedDataType, this.newAttributeValue);

			context.closeDialog(this);
			context.showMessageInDialog(new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Successful Execution",
					"Environment Attribute Added Successfull."));

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Successful Execution",
							"Environment Attribute Added Successfull."));
			this.newAttributeName = null;
			this.newAttributeValue = null;
			this.operationFail = false;
			this.selectedDataType = null;
			log.info("Environment attribute saved successfully.");

			return;
		}

	}

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/

	/**
	 * Cancels the operation of updating an existing {@code EnvAttrValues}
	 * instance.
	 */
	public void cancelUpdateEnvironmentAttributeValue() {
		RequestContext context = RequestContext.getCurrentInstance();
		log.info("Updating environment attribute value was cancelled.");

		context.closeDialog(this);

	}

	/**
	 * Cancels the operation of updating an existing
	 * {@code EnvironmentAttribute} instance.
	 */
	public void cancelUpdateEnvironmentAttribute() {

		RequestContext context = RequestContext.getCurrentInstance();
		log.info("Updating environment attribute was cancelled.");

		context.closeDialog(this);

	}

	/**
	 * Cancels the operation of updating an existing {@code Environment}
	 * instance.
	 */
	public void cancelUpdateEnvironment() {
		RequestContext context = RequestContext.getCurrentInstance();
		log.info("Updating environment was cancelled.");

		context.closeDialog(this);
	}

	/**
	 * Cancels the operation of adding a new {@code EnvAttrValues} corresponding
	 * to an existing {@code EnvironmentAttribute} instance
	 */
	public void cancelAddEnvironmentAttributeValue() {
		RequestContext context = RequestContext.getCurrentInstance();
		log.info("Adding environment attribute value was cancelled.");

		context.closeDialog(this);
		this.newAttributeValue = null;
	}

	/**
	 * Cancels the operation of adding an existing {@code EnvironmentAttribute}
	 * instance corresponding to an existing {@code Environment} instance.
	 */
	public void cancelAddEnvironmentAttribute() {
		RequestContext context = RequestContext.getCurrentInstance();
		log.info("Adding environment attribute was cancelled.");

		context.closeDialog(this);
		this.newAttributeName = null;
		this.selectedDataType = null;

	}

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/
	/**
	 * Displays a message inside a Primefaces {@code growl} component in case of
	 * successful add operation.
	 */
	public void showAddMessage() {

		if (!isOperationFail()) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(" Successful Execution",
							"Added Successfully"));

			this.setOperationFail(true);
		}

	}

	/**
	 * Displays a message inside a Primefaces {@code growl} component in case of
	 * successful update operation.
	 */
	public void showUpdateMessage() {

		if (!isOperationFail()) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(" Successful Execution",
							"Updated Successfully"));

			this.setOperationFail(true);
		}

	}

	/*------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------*/
	/**
	 * Disables the buttons for adding {@code EnvironmentAttribute} and
	 * {@code EnvAttrValues} instances corresponding to an existing
	 * {@code Environment} instance.
	 */
	public void onEnvironmentUnSelect() {
		this.attrbtn = true;
		this.attrValuebtn = true;

	}

	/**
	 * Disables the button for adding {@code EnvAttrValues} instance
	 * corresponding to an existing {@code EnvironmentAttribute} instance.
	 */
	public void onEnvironmentAttributeUnSelect() {
		this.attrValuebtn = true;

	}

	/**
	 * Disables the button for adding {@code EvnAttrValues} instance and enables
	 * the button for adding {@code EnvironmentAttribute} instance whenever an
	 * {@code Environment} instance is selected.
	 */
	public void onEnvironmentSelect() {
		this.attrbtn = false;
		this.attrValuebtn = true;

	}
}
