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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.UnselectEvent;

import com.aislab.accesscontrol.core.entities.Policy;
import com.aislab.accesscontrol.core.entities.Rule;
import com.aislab.accesscontrol.core.entities.Target;
import com.aislab.accesscontrol.core.ui.dao.PolicyDAO;
import com.aislab.accesscontrol.core.ui.dao.RuleDAO;

/**
 * A session scoped, managed bean for user interfaces related to {@code Policy}
 * 
 * @author Salman Ahmad Ansari <10besesansari@seecs.edu.pk>
 * @author Arjumand Fatima <09bicseafatima@seecs.edu.pk>
 * @version 1.0
 * 
 */
@ManagedBean
@SessionScoped
public class PolicyController {
	/**
	 * A static {@code Logger} instance for logging
	 */
	static Logger log = Logger.getLogger(PolicyController.class.getName());

	/**
	 * An instance of {@code PolicyDAO} for using methods to access data related
	 * to {@code Policy}
	 */
	PolicyDAO daoPolicy = new PolicyDAO();
	/**
	 * An instance of {@code RuleDAO} for using methods to access data related
	 * to {@code Rule}
	 */
	RuleDAO daoRule = new RuleDAO();

	/**
	 * A {@code List} variable to provide all the available rule combining
	 * algorithms.
	 */
	List<String> algorithmList = Arrays.asList("First applicable",
			"Deny overrides", "Ordered deny overrides", "Permit overrides",
			"Ordered permit overrides");

	/**
	 * An instance of {@code Policy} used to store the {@code Policy} selected
	 * by the user from the user interface.
	 */
	public Policy selectedPolicy = new Policy();
	// List of all selected Policies
	/**
	 * An ArrayList of {@code Policy} used to display all the existing
	 * {@code Policy} instances stored in the database.
	 */
	ArrayList<Policy> policyList = new ArrayList<Policy>();

	/**
	 * An ArrayList of {@code Rule} used to display all the existing
	 * {@code Rule} instances that can be added to the {@code Policy} instance
	 * while updating it. This {@code Policy} instance is specified by the
	 * {@code selectedPolicy} property.
	 */
	public ArrayList<Rule> ruleList = new ArrayList<Rule>();
	/**
	 * A {@code Target} instance while updating an existing {@code Policy}
	 * instance.
	 */
	public Target appliedTarget = null; // update policy

	/**
	 * A {@code String} variable to store the updated value of {@code policyId}
	 * attribute of the {@code Policy} instance specified by the
	 * {@code selectedPolicy} property.
	 */
	public String name = null;
	/**
	 * A {@code String} variable to store the updated value of
	 * {@code description} attribute of the {@code Policy} instance specified by
	 * the {@code selectedPolicy} property.
	 */
	public String description = null;

	/**
	 * An {@code ArrayList} to provide all the existing {@code Rule} instances
	 * that can be added in the {@code Policy} instance specified by the
	 * {@code selectedPolicy} property while updating it.
	 */
	ArrayList<Rule> allRule = new ArrayList<Rule>();
	/**
	 * An {@code ArrayList} containing all the {@code Rule} instances that need
	 * to be added to an existing {@code Policy} instance specified by the
	 * {@code selectedPolicy} property.
	 */
	ArrayList<Rule> selectedAddRules = new ArrayList<Rule>();

	/**
	 * A {@code List} variable to provide all the available {@code Rule}
	 * instances related to a {@code Policy} instance specified by the
	 * {@code selectedPolicy} property.
	 */
	List<Rule> selectedRules;

	/**
	 * An instance of {@code Rule} used to store the {@code Rule} selected by
	 * the user from the user interface.
	 */
	public Rule selectedRule = new Rule();
	/**
	 * A boolean variable to check whether the {@code Save} or {@code Cancel}
	 * button was pressed in a modification operation. By default it is set to
	 * {@code TRUE} while it is becomes {@code FALSE} if {@code Save} is
	 * pressed.
	 * 
	 */
	boolean operationFail = true;
	/**
	 * A boolean variable to check whether any {@code Policy} instance is
	 * selected by the user so that the corresponding {@code Rule} instance can
	 * be added. By default it is set to {@code TRUE} so that the corresponding
	 * add button is disabled while it is becomes {@code FALSE} if any
	 * {@code Policy} instance is selected in the user interface.
	 */
	boolean attrbtn = true;

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/

	/**
	 * Sets the {@code algorithmList} property to {@code List} argument.
	 * 
	 * @param algorithmList
	 */
	public void setAlgorithmList(List<String> algorithmList) {
		this.algorithmList = algorithmList;
		log.debug("Set  algorithmList: " + algorithmList);

	}

	/**
	 * Sets the {@code allRule} property to {@code ArrayList} argument.
	 * 
	 * @param allRule
	 */
	public void setAllRule(ArrayList<Rule> allRule) {
		this.allRule = allRule;
		log.debug("Set  allRule: " + allRule);
	}

	/**
	 * Sets the {@code selectedAddRules} property to {@code ArrayList} argument.
	 * 
	 * @param selectedAddRules
	 */
	public void setSelectedAddRules(ArrayList<Rule> selectedAddRules) {
		this.selectedAddRules = selectedAddRules;
		log.debug("Set  selectedAddRules: " + selectedAddRules);
	}

	/**
	 * Sets the {@code selectedRules} property to {@code List} argument.
	 * 
	 * @param selectedRules
	 */
	public void setSelectedRules(List<Rule> selectedRules) {

		this.selectedRules = selectedRules;
		log.debug("Set  selectedRules: " + selectedRules);
	}

	/**
	 * Sets the {@code selectedRule} property to {@code Rule} argument.
	 * 
	 * @param selectedRule
	 */
	public void setSelectedRule(Rule selectedRule) {
		this.selectedRule = selectedRule;
		log.debug("Set  selectedRule: " + selectedRule);
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
	 * Sets the {@code attrbtn} property to {@code boolean} argument.
	 * 
	 * @param attrbtn
	 */
	public void setAttrbtn(boolean attrbtn) {
this.attrbtn = attrbtn;
		log.debug("Set  attrbtn: " + attrbtn);
	}

	/**
	 * Sets the {@code daoPolicy} property to {@code PolicyDAO} argument.
	 * 
	 * @param daoPolicy
	 */
	public void setDaoPolicy(PolicyDAO daoPolicy) {
this.daoPolicy = daoPolicy;
		log.debug("Set  daoPolicy: " + daoPolicy);
	}

	/**
	 * Sets the {@code selectedPolicy} property to {@code Policy} argument.
	 * 
	 * @param selectedPolicy
	 */
	public void setSelectedPolicy(Policy selectedPolicy) {
this.selectedPolicy = selectedPolicy;
		// Ensuring that a policy is selected
		if (selectedPolicy != null) {
			this.name = this.selectedPolicy.getPolicyId();
			this.description = this.selectedPolicy.getDescription();
			this.setAttrbtn(false);

		}
		log.debug("Set  selectedPolicy: " + selectedPolicy);

	}

	/**
	 * Sets the {@code policyList} property to {@code ArrayList} argument.
	 * 
	 * @param policyList
	 */
	public void setPolicyList(ArrayList<Policy> policyList) {
this.policyList = policyList;
		log.debug("Set  policyList: " + policyList);
	}

	/**
	 * Sets the {@code ruleList} property to {@code ArrayList} argument.
	 * 
	 * @param ruleList
	 */
	public void setRuleList(ArrayList<Rule> ruleList) {
this.ruleList = ruleList;
		log.debug("Set  ruleList: " + ruleList);
	}

	/**
	 * Sets the {@code appliedTarget} property to {@code Target} argument.
	 * 
	 * @param appliedtTarget
	 */
	public void setAppliedTarget(Target appliedtTarget) {
this.appliedTarget = appliedtTarget;
		log.debug("Set  appliedTarget: " + appliedTarget);
	}

	/**
	 * Sets the {@code daoRule} property to {@code RuleDAO} argument.
	 * 
	 * @param daoRule
	 */
	public void setDaoRule(RuleDAO daoRule) {
this.daoRule = daoRule;
		log.debug("Set  daoRule: " + daoRule);
	}

	/**
	 * Sets the {@code name} property to {@code String} argument.
	 * 
	 * @param name
	 */
	public void setName(String name) {
this.name = name;
		log.debug("Set  name: " + name);
	}

	/**
	 * Sets the {@code description} property to {@code String} argument.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
	this.description = description;
		log.debug("Set  description: " + description);
	}

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/
	/**
	 * Returns the value of {@code daoPolicy} property.
	 * 
	 * @return daoPolicy
	 */
	public PolicyDAO getDaoPolicy() {
	log.debug("Get  daoPolicy: " + daoPolicy);
		return daoPolicy;
	}

	/**
	 * Returns the value of {@code selectedAddRules} property.
	 * 
	 * @return selectedAddRules
	 */
	public ArrayList<Rule> getSelectedAddRules() {
			log.debug("Get  selectedAddRules: " + selectedAddRules);
		return selectedAddRules;
	}

	/**
	 * Returns the value of {@code allRule} property.
	 * 
	 * @return allRule
	 */
	public ArrayList<Rule> getAllRule() {
			log.debug("Getting allRule ");
		return (ArrayList<Rule>) daoPolicy
				.selectRulesToAdd(this.selectedPolicy);

	}

	/**
	 * Returns the value of {@code policyRules} property.
	 * 
	 * @return policyRules
	 */
	public ArrayList<Rule> getPolicyRules() {
			if (this.selectedPolicy != null) {
			this.selectedRules = daoPolicy.selectRules(this.selectedPolicy
					.getPkPolicy());
		}
		log.debug("Getting policyRules");
		return (ArrayList<Rule>) daoRule.selectRule();
	}

	/**
	 * Returns the value of {@code selectedRules} property.
	 * 
	 * @return selectedRules
	 */
	public List<Rule> getSelectedRules() {
			if (this.selectedPolicy != null) {
			log.debug("Getting  selectedRules ");
			return daoPolicy.selectRules(this.selectedPolicy.getPkPolicy());
		}
		log.debug("Get  selectedRules: " + selectedRules);
		return selectedRules;
	}

	/**
	 * Returns the value of {@code selectedRule} property.
	 * 
	 * @return selectedRule
	 */
	public Rule getSelectedRule() {
			log.debug("Get  selectedRule: " + selectedRule);
		return selectedRule;
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
	 * Returns {@code FALSE} if an instance of the {@code Action} is selected by
	 * the user otherwise TRUE.
	 * 
	 * @return attrbtn
	 */
	public boolean isAttrbtn() {
			log.debug("Get  attrbtn: " + attrbtn);
		return attrbtn;
	}

	/**
	 * Returns the value of {@code selectedPolicy} property.
	 * 
	 * @return selectedPolicy
	 */
	public Policy getSelectedPolicy() {
			log.debug("Get  selectedPolicy: " + selectedPolicy);
		return selectedPolicy;
	}

	/**
	 * Returns the value of {@code policyList} property.
	 * 
	 * @return policyList
	 */
	public ArrayList<Policy> getPolicyList() {
			policyList = (ArrayList<Policy>) daoPolicy.selectPolicy();
		log.debug("Getting  policyList ");
		return (ArrayList<Policy>) daoPolicy.selectPolicy();

	}

	/**
	 * Returns the value of {@code ruleList} property.
	 * 
	 * @return ruleList
	 */
	public ArrayList<Rule> getRuleList() {
			if (selectedPolicy != null) {
			log.debug("Getting  ruleList ");
			return (ArrayList<Rule>) daoPolicy.selectRules(selectedPolicy
					.getPkPolicy());
		}
		log.debug("Get  ruleList: " + ruleList);
		return this.ruleList;
	}

	/**
	 * Returns the value of {@code appliedAlgo} property.
	 * 
	 * @return appliedAlgo
	 */
	public String getAppliedAlgo() {
			if (selectedPolicy != null) {
			log.debug("Get  AppliedAlgo: " + selectedPolicy.getRuleCombAlgo());
			return selectedPolicy.getRuleCombAlgo();
		}
		log.debug("Get  AppliedAlgo: " + null);
		return null;
	}

	/**
	 * Returns the value of {@code appliedTarget} property.
	 * 
	 * @return appliedTarget
	 */
	public Target getAppliedTarget() {
			if (selectedPolicy != null) {
			appliedTarget = daoPolicy.getTarget(selectedPolicy.getPkPolicy());
		}
		log.debug("Get  appliedTarget: " + appliedTarget);

		return appliedTarget;
	}

	/**
	 * Returns the value of {@code daoRule} property.
	 * 
	 * @return daoRule
	 */
	public RuleDAO getDaoRule() {
			log.debug("Get  daoRule: " + daoRule);
		return daoRule;
	}

	/**
	 * Returns the value of {@code name} property.
	 * 
	 * @return name
	 */
	public String getName() {
			log.debug("Get  name: " + name);
		return name;
	}

	/**
	 * Returns the value of {@code description} property.
	 * 
	 * @return description
	 */
	public String getDescription() {
			log.debug("Get  description: " + description);
		return description;
	}

	/**
	 * Returns the value of {@code algorithmList} property.
	 * 
	 * @return algorithmList
	 */
	public List<String> getAlgorithmList() {
			log.debug("Get  algorithmList: " + algorithmList);
		return algorithmList;
	}

	/**
	 * Returns the {@code description} attribute of the {@code Target} instance
	 * related associated with the {@code Policy} instance specified by the
	 * primary key given as {@code String} argument.
	 * 
	 * @param pkPolicy
	 * @return description
	 */
	public String getPolicyTarget(String pkPolicy) {
			Target t = daoPolicy.getTarget(Long.parseLong(pkPolicy));
		if (t != null) {
			log.debug("Get  PolicyTarget: " + t.getDescription());
			return t.getDescription();
		} else {
			log.debug("Get  PolicyTarget: " + "Target Empty ");
			return "Target Empty";
		}
	}

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/

	/**
	 * Opens the {@code /Policy Creation/Policy/Add/Pol_AddPolicy.xhtml} file in a
	 * Primefaces {@code  Dialog} component which offers the functionality of
	 * adding a new {@code Policy} instance.
	 */
	public String addPolicy() {
		selectedPolicy = null;
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

		context.openDialog("/Policy Creation/Policy/Add/Pol_AddPolicy", options,
				null);
		return null;

	}

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/

	/**
	 * Opens the {@code /Policy Creation/Policy/Update/Pol_UpdatePolicy.xhtml} file
	 * in a Primefaces {@code  Dialog} component which offers the functionality
	 * of updating an existing {@code Policy} instance.
	 */
	public void updatePolicy() {
	RequestContext context = RequestContext.getCurrentInstance();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("scrollable", true);
		options.put("dynamic", true);
		options.put("height", 450);
		options.put("width", 665);
		options.put("contentHeight", 430);
		options.put("contentWidth", 650);
		options.put("modal", true);
		options.put("draggable", false);

		context.openDialog("/Policy Creation/Policy/Update/Pol_UpdatePolicy",
				options, null);

	}

	/**
	 * Opens the {@code /Policy Creation/Policy/Add/Pol_AddApplicableRule.xhtml} file in
	 * a Primefaces {@code  Dialog} component which offers the functionality of
	 * adding a new instance of @{code Rule} corresponding to an existing
	 * {@code Policy} instance.
	 */
	public void updatePolicyRule() {

		RequestContext context = RequestContext.getCurrentInstance();
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("scrollable", true);
		options.put("dynamic", true);
		options.put("height", 395);
		options.put("width", 665);
		options.put("contentHeight", 380);
		options.put("contentWidth", 650);
		options.put("modal", true);
		options.put("draggable", false);

		context.openDialog("/Policy Creation/Policy/Add/Pol_AddApplicableRule",
				options, null);

	}

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/

	/**
	 * Cancels the operation of updating an existing {@code Policy} instance.
	 */
	public void cancelUpdatePolicy() {
		appliedTarget = null;
		allRule = null;

		RequestContext context = RequestContext.getCurrentInstance();
		log.info("Updating policy was cancelled.");
		context.closeDialog(this);

	}

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/
	/**
	 * Saves the updated {@code Policy} instance in the database.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveUpdatePolicy() {
			RequestContext context = RequestContext.getCurrentInstance();

		// Ensuring that user has entered a PolicyID
		if (this.selectedPolicy.getPolicyId() == null) {
			log.info("Name cannot be empty.");
			
			context.execute("noNameDialog.show()");
			return;

		}

		// Ensuring that user has entered a Description
		if (this.selectedPolicy.getDescription() == null) {
			log.info("Description cannot be empty.");
			context.execute("noDescriptionDialog.show()");
			return;

		}

		// Ensuring that user has selected a rule Combining Algorithm
		if (this.selectedPolicy.getRuleCombAlgo() == null
				|| this.selectedPolicy.getRuleCombAlgo().equals(
						"Select Rule Combining Algorithm ")) {
			log.info("Rule combining algorithm must be provided.");
			context.execute("noRuleCombDialog.show()");
			return;

		}
		// Casting ArrayList into Set, because updatePolicy only accepts Set
		Set rules = new HashSet(
				(ArrayList<Rule>) daoPolicy.selectRules(selectedPolicy
						.getPkPolicy()));
		// Updating Policy
		daoPolicy.updatePolicy(selectedPolicy.getPkPolicy(), name, description,
				selectedPolicy.getRuleCombAlgo(), appliedTarget, rules);
		// Reinitializing all the local variables

		appliedTarget = null;
		allRule = null;
		this.setOperationFail(false);
			context.closeDialog(this);
			log.info("Updated policy saved successfully.");

	}

	/**
	 * Adds the selected {@code Rule} instances in the already existing
	 * {@code Policy} instance specified by the {@code selectedPolicy} property.
	 */
	public void saveUpdatePolicyRule() {
RequestContext context = RequestContext.getCurrentInstance();

		// Ensuring updatedPolicy is not null
		if (selectedPolicy != null) {
			log.info("Updating policy's associated rule instance.");
				daoPolicy.addPolicyRule(this.selectedPolicy, selectedAddRules);
			appliedTarget = null;
			allRule = null;
			this.setOperationFail(false);
			this.selectedAddRules.clear();
			log.info("Updated rule associated with policy saved successfully.");
		}
		else {
			log.info("Updated rule associated with policy was not saved successfully.");
			
		}
		context.closeDialog(this);

	}

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/

	/**
	 * Deletes the instance of {@code Policy} stored in {@code selectedPolicy}
	 * property
	 */
	public void deletePolicy() {
if(this.selectedPolicy != null)
{
	// Deleting the selected Policy

		this.daoPolicy.deletePolicy(this.selectedPolicy.getPkPolicy());
		// reinitializing all the local variables
		this.appliedTarget = null;
		this.allRule = null;

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(" Successful Execution",
						"Deleted Successfully"));
		log.info("Policy deleted successfully.");
}
else 
{
	log.info("Policy was not deleted successfully.");
}
	}

	/**
	 * Deletes an instance of {@code Rule} stored in {@code selectedRule}
	 * property.
	 */
	public void deletePolicyRule() {

	if((this.selectedPolicy != null) && (this.selectedRule != null))
	{// Deleting Rule from a selected Rule
		this.daoPolicy
				.deletePolicyRules(this.selectedPolicy, this.selectedRule);

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(" Successful Execution",
						"Deleted Successfully"));
		log.info("Policy's associated rule deleted successfully.");
	}
	else 
	{
		log.info("Rule associated with Poliy was not deleted successfully.");
	}
	}

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/

	/**
	 * Unselects the already selected {@code Target} instance while updating an
	 * existing {@code Policy} instance.
	 * 
	 * @param event
	 */
	public void targetUnselect(UnselectEvent event) {
		appliedTarget = null;

	}

	/**
	 * Unselects the already selected {@code Rule} instance while updating an
	 * existing {@code Policy} instance.
	 */
	public void ruleUnselect() {
		this.selectedRule = null;

	}

	/**
	 * Disables the buttons for adding {@code Rule} instances corresponding to
	 * an existing {@code Policy} instance.
	 */
	public void onPolicyUnSelect() {
		this.attrbtn = true;

	}

	/*---------------------------------------------------------------------------------------*/
	/*---------------------------------------------------------------------------------------*/
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

}
