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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import com.aislab.accesscontrol.core.entities.Apply;
import com.aislab.accesscontrol.core.entities.AttributeDesignator;
import com.aislab.accesscontrol.core.entities.AttributeValue;
import com.aislab.accesscontrol.core.entities.Condition;
import com.aislab.accesscontrol.core.entities.Expression;
import com.aislab.accesscontrol.core.ui.dao.ConditionDAO;
import com.aislab.accesscontrol.core.ui.util.XACMLConstants;

import org.primefaces.model.TreeNode;
import org.primefaces.model.DefaultTreeNode;

/**
 * A session scoped, managed bean for user interfaces related to {@code AddCondition}
 * 
 * @author Salman Ahmed Ansari <10besesansari@seecs.edu.pk>
 * @author Arjumand Fatima <09bicseafatima@seecs.edu.pk>
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class AddCondition implements Serializable {

	/**
	 * A static {@code Logger} instance for logging
	 */
	static Logger log = Logger.getLogger(AddCondition.class.getName());

	boolean operationFail = true;

	private static final long serialVersionUID = 1L;
	private TreeNode root;
	int i = 0;

	private TreeNode selectedNode;
	ConditionDAO testDAO = new ConditionDAO();
	ArrayList<TreeNode> newNode = new ArrayList<TreeNode>();
	ArrayList<Apply> createdApply = new ArrayList<Apply>();
	ArrayList<Expression> createdExpression = new ArrayList<Expression>();
	// Number of Argument for the Function
	List<String> arguments = Arrays.asList("1", "2");

	// TO store Selected number of Arguments
	ArrayList<String> number = new ArrayList<String>();

	// To get selected number of Argument
	private String selectedNumber;

	// Available DataTypes
	List<String> dataType = Arrays.asList("String", "boolean", "integer",
			"double", "time", "date", "dateTime", "dayTimeDuration", "anyURI",
			"hexBinary", "base64Binary", "rfc822Name", "x500Name");

	// Function Type
	List<String> functionID = new ArrayList<String>();

	// To store single argument Functions
	List<String> functionOneArg = Arrays
			.asList("urn:oasis:names:tc:xacml:1.0:function:integer-abs",
					"urn:oasis:names:tc:xacml:1.0:function:double-abs",
					"urn:oasis:names:tc:xacml:1.0:function:round",
					"urn:oasis:names:tc:xacml:1.0:function:floor",
					"urn:oasis:names:tc:xacml:1.0:function:string-normalize-space",
					"urn:oasis:names:tc:xacml:1.0:function:string-normalize-to-lower-case",
					"urn:oasis:names:tc:xacml:1.0:function:double-to-integer",
					"urn:oasis:names:tc:xacml:1.0:function:integer-to-double",
					"urn:oasis:names:tc:xacml:1.0:function:not",
					"urn:oasis:names:tc:xacml:2.0:function:url-string-concatenate");

	private String condDescription = null;

	private Condition cond = new Condition();
	private Apply rootApply;
	private Apply apply = null;

	private String applyFuncId = "String";
	private String applyDesc;
	private String applyType = null;

	private String designatorId;
	private String designatorType;
	private String attributeDesignatorId;

	private String attributeValue;
	private String valueType;
	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

	public void initialize() {
		newNode = new ArrayList<TreeNode>();
		createdApply = new ArrayList<Apply>();
		createdExpression = new ArrayList<Expression>();

		condDescription = null;

		cond = new Condition();
		rootApply = new Apply();
		apply = null;

		applyFuncId = "String";
		applyDesc = null;
		applyType = null;

		designatorId = null;
		designatorType = null;
		attributeDesignatorId = null;

		attributeValue = null;
		valueType = null;

		root = new DefaultTreeNode("Root", null);
		TreeNode node0 = new DefaultTreeNode("Condition", root);
		selectedNode = node0;

		arguments = Arrays.asList("1", "2");
		number = new ArrayList<String>();
		selectedNumber = null;

	}
	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * Sets the {@code operationFail} property to {@code boolean} argument.
	 * 
	 * @param operationFail
	 */
	public void setOperationFail(boolean operationFail) {
		this.operationFail = operationFail;
		log.debug("Set  operationFail: " + operationFail);

	}
	public void setDataType(List<String> dataType) {
		this.dataType = dataType;
	}

	

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	
	public void setDesignatorId(String designatorId) {
		this.designatorId = designatorId;
	}
	
	public void setDesignatorType(String designatorType) {
		this.designatorType = designatorType;
	}
	

	public void setCondDescription(String condDescription) {
		System.out.println("Condition Description Set" + condDescription);
		this.condDescription = condDescription;
	}

	
	public void setApplyFuncId(String applyFuncId) {
		this.applyFuncId = applyFuncId;
	}

	

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}


	public void setApplyType(String applyType) {
		this.applyType = applyType;
		populateFunctionId();
	}

	
	
	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}
	
	public void setFunctionID(List<String> functionID) {
		this.functionID = functionID;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
		System.out.println("Node Selected Successfully! ");
	}
	public void setAttributeDesignatorId(String attributeDesignatorId) {
		this.attributeDesignatorId = attributeDesignatorId;
	}
	public void setSelectedNumber(String selectedNumber) {
		this.selectedNumber = selectedNumber;
		populateFunctionId();
	}
	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

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

	

	public List<String> getDataType() {
		return dataType;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public String getValueType() {
		return valueType;
	}
	public String getDesignatorId() {
		return designatorId;
	}

	public String getDesignatorType() {
		return designatorType;
	}

	public String getCondDescription() {
		return condDescription;
	}
	public String getApplyFuncId() {
		return applyFuncId;
	}

	public String getApplyType() {
		return applyType;
	}
	public String getApplyDesc() {
		return applyDesc;
	}
	public List<String> getArguments() {
		return arguments;
	}

	public List<String> getFunctionID() {
		return functionID;
	}

	public TreeNode getRoot() {
		root.setExpanded(true);
		return root;
	}

	public TreeNode getSelectedNode() {
		if (selectedNode != null)
			selectedNode.setExpanded(true);
		return selectedNode;
	}

	public String getAttributeDesignatorId() {
		return attributeDesignatorId;
	}

	public String getSelectedNumber() {
		return selectedNumber;
	}
	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

	

	public void onNodeSelect(NodeSelectEvent event) {
		this.selectedNode = event.getTreeNode();
		System.out.println("Node Selected Successfully! ");
	}
	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

	public void addApply() {

		System.out.println("addApply");

	}
	public AddCondition() {
		root = new DefaultTreeNode("Root", null);
		TreeNode node0 = new DefaultTreeNode("Condition", root);
		selectedNode = node0;
	}
	public void addAtrributeDesignator() {
		System.out.println("addDesignator");

	}

	public void addValue() {
		System.out.println("Adding Value");
	}
	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

	public void saveApply() {
		RequestContext context = RequestContext.getCurrentInstance();
		Apply testApply = new Apply(this.applyDesc, this.applyFuncId);
		if (apply == null) {
			rootApply = testApply;
			apply = testApply;
		} else {
			testApply.setApply(apply);
			apply = testApply;
		}

		TreeNode temp = new DefaultTreeNode("Apply: " + this.applyDesc,
				this.selectedNode);
		newNode.add(temp);
		number.add(selectedNumber);
		createdApply.add(apply);
		System.out.println("Leaving Apply");
		applyDesc = null;
		applyFuncId = null;
		applyType = null;
		selectedNumber = null;
		System.out.println(selectedNumber);

		context.showMessageInDialog(new FacesMessage(" Successful Execution",
				"Apply Added to Condition Successfully"));
		log.info("Closing dialog.");

		context.execute("addApplyDialog.hide()");
	}
	public void saveDesignator() {
		Expression testDesignator = new AttributeDesignator(
				this.attributeDesignatorId, this.designatorId,
				this.designatorType);
		testDesignator.setApply(apply);
		RequestContext context = RequestContext.getCurrentInstance();
		TreeNode temp = new DefaultTreeNode("Designator: " + this.designatorId,
				this.selectedNode);
		newNode.add(temp);
		// testDAO.createExpression(testDesignator);
		createdExpression.add(testDesignator);

		designatorId = null;
		designatorType = null;
		attributeDesignatorId = null;

		context.showMessageInDialog(new FacesMessage(" Successful Execution",
				"Designator Added to Condition Successfully"));
		log.info("Closing dialog.");

		context.execute("addDesignatorDialog.hide()");
	}
	public String saveCondition() {

		RequestContext context = RequestContext.getCurrentInstance();

		System.out.println(condDescription);

		if (this.condDescription == null) {
			// log.info("Description cannot be empty.");

			System.out.println("desc null");

			context.showMessageInDialog(new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Warning",
					"Description cannot be empty."));

			return null;
		}

		if (this.apply == null) {
			// log.info("Description cannot be empty.");

			System.out.println("apply null");

			context.showMessageInDialog(new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Warning",
					"Apply cannot be empty."));

			return null;
		}

		System.out.println("Saving Condition: " + condDescription);
		cond = new Condition(rootApply);
		cond.setDescription(condDescription);
		testDAO.createApply(createdApply);
		testDAO.createExpression(createdExpression);
		testDAO.createCondition(cond);

		this.operationFail = false;

		// Reinitialize
		initialize();
		// return "/HomePage?faces-redirect=true";

		context.closeDialog(this);

		return null;
	}
	public void saveValue() {
		Expression testValue = new AttributeValue(this.attributeValue,
				this.valueType);
		testValue.setApply(apply);
		RequestContext context = RequestContext.getCurrentInstance();
		TreeNode temp = new DefaultTreeNode("Value: " + this.attributeValue,
				this.selectedNode);
		newNode.add(temp);
		// testDAO.createExpression(testValue);
		createdExpression.add(testValue);
		attributeValue = null;
		valueType = null;

		context.showMessageInDialog(new FacesMessage(" Successful Execution",
				"Attribute Value Added to Condition Successfully"));
		log.info("Closing dialog.");

		context.execute("addValueDialog.hide()");
	}


	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

	public void cancelApply() {
		RequestContext context = RequestContext.getCurrentInstance();
		applyDesc = null;
		applyFuncId = "String";
		applyType = null;
		selectedNumber = null;
		context.execute("addApplyDialog.hide()");
	}
	

	
	
	public void cancelDesignator() {
		RequestContext context = RequestContext.getCurrentInstance();
		designatorId = null;
		designatorType = null;
		attributeDesignatorId = null;
		context.execute("addDesignatorDialog.hide()");
	}

	

	
	public void cancelValue() {
		RequestContext context = RequestContext.getCurrentInstance();
		attributeValue = null;
		valueType = null;
		context.execute("addValueDialog.hide()");
	}

	
	public void cancelCondition() {

		RequestContext context = RequestContext.getCurrentInstance();

		// Reinitialize
		initialize();
		// return "/HomePage?faces-redirect=true";

		context.closeDialog(this);
	}
	


	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

	public void populateFunctionId() {
		XACMLConstants xacmlConsts = new XACMLConstants();
		ArrayList<String> allFuncs = xacmlConsts.getMatchIds();
		functionID = new ArrayList<String>();
		if (this.applyType != null) {
			System.out.println("Data Type is : " + applyType);
			if (selectedNumber.equals("1")) {
				for (int t = 0; t < functionOneArg.size(); t++) {
					String fId = functionOneArg.get(t);

					if (fId != null) {
						if ((fId.contains(applyType) || fId.contains(applyType
								.toLowerCase(Locale.ENGLISH)))) {

							functionID.add(fId);
						}
					}

				}
			} else {
				for (int t = 0; t < allFuncs.size(); t++) {
					String fId = allFuncs.get(t);

					if (fId != null) {
						if ((fId.contains(applyType) || fId.contains(applyType
								.toLowerCase(Locale.ENGLISH)))
								&& !functionOneArg.contains(fId)) {

							functionID.add(fId);
						}
					}

				}
			}
			System.out.println("Function Ids Updated");
		}
	}
	/*-----------------------------------------------------------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------------------------------------------------------*/

	public void showAddMessage() {

		if (!isOperationFail()) {
			log.info("Added Environment Successfully.");

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(" Successful Execution",
							"Added Condition Successfully"));

			this.setOperationFail(true);
		}

	}

}