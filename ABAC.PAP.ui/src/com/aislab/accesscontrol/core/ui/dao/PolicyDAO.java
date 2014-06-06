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

package com.aislab.accesscontrol.core.ui.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import com.aislab.accesscontrol.core.entities.Obligations;
import com.aislab.accesscontrol.core.entities.Policy;
import com.aislab.accesscontrol.core.entities.Rule;
import com.aislab.accesscontrol.core.entities.Target;
import com.aislab.accesscontrol.core.ui.util.HibernateUtil;

/**
 * Class for querying Database for queries related to Policies
 * 
 * @author Yumna Ghazi <09bicseyghazi@seecs.edu.pk>
 * @author Ummair Asghar <10beseuasghar@seecs.edu.pk>
 * @author Salman Ahmed Ansari <10besesansari@seecs.edu.pk>
 * @version 1.0
 * 
 */

public class PolicyDAO {

	/**
	 * A SessionFactory variable to configure the database session
	 */
	public static SessionFactory sessionFactory;

	/**
	 * A Session variable to store the session opened
	 */
	public static Session session;

	/**
	 * A Transaction variable used to start a transaction in a session
	 */
	public static Transaction tx;

	/**
	 * A Query variable used to retrieve information from database
	 */
	Query query;

	/**
	 * Selecting all the Policies from the database
	 * 
	 * @return List of all the policies
	 */
	@SuppressWarnings("unchecked")
	public List<Policy> selectPolicy() {
		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		query = session.createQuery("from Policy");
		List<Policy> pol = query.list();

		tx.commit();
		session.close();
		return pol;
	}

	/**
	 * Selecting a specific Policy from the database
	 * 
	 * @param pkPolicy
	 *            primary key of the Policy
	 * @return required policy
	 */
	public Policy selectPolicy(Long pkPolicy) {
		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		query = session.createQuery("from Policy p where p.pkPolicy = "
				+ pkPolicy);
		Policy pol = (Policy) query.uniqueResult();

		tx.commit();
		session.close();
		return pol;
	}

	/**
	 * Creating Policy with provided arguments
	 * 
	 * @param policyId
	 *            , Id of the Policy
	 * @param ruleCombAlgo
	 *            , Rule Combining Algorithm of the Policy
	 * @param targ
	 *            , Target of the Policy
	 * @param description
	 *            , Description of the Policy
	 * @param rules
	 *            , Applied Rules of the Policy
	 */
	public void createPolicy(String policyId, String ruleCombAlgo, Target targ,
			String description, Set<Rule> rules) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		// Creating a new object of Policy with Provided Arguments
		Policy pol = new Policy(targ, policyId, ruleCombAlgo, description,
				rules);

		session.persist(pol);
		tx.commit();
		session.close();
	}

	/**
	 * Updating an already created Policy
	 * 
	 * @param pkPolicy
	 *            Primary Key of the policy that is to be modified
	 * @param rules
	 *            applied rules on the Policy
	 */
	public void updatePolicy(Long pkPolicy, Set<Rule> rules) {
		
		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		Policy pol = (Policy) session.load(Policy.class, pkPolicy);
		// Policy pol = (Policy) query.uniqueResult();
		pol.setRules(rules);

		session.saveOrUpdate(pol);
		tx.commit();
		session.close();
	}

	/**
	 * Updating already created Policy
	 * 
	 * @param pkPolicy
	 *            , primary key of the Policy
	 * @param name
	 *            , name of the Policy
	 * @param description
	 *            , description of the Policy
	 * @param ruleCombAlgo
	 *            , rule combining algorithm of the Policy
	 * @param targ
	 *            , target of the Policy
	 * @param rules
	 *            , Rules applicable on the Policy
	 */
	public void updatePolicy(Long pkPolicy, String name, String description,
			String ruleCombAlgo, Target targ, Set<Rule> rules) {
	
		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		query = session.createQuery("from Policy p where p.pkPolicy ="
				+ pkPolicy);
		Policy pol = (Policy) query.uniqueResult();
		pol.setPolicyId(name);
		pol.setDescription(description);
		pol.setRuleCombAlgo(ruleCombAlgo);
		pol.setTarget(targ);
		pol.setRules(rules);

		session.persist(pol);
		tx.commit();
		session.close();
	}

	/**
	 * Updating Policy Rules
	 * 
	 * @param pkPolicy
	 *            Primary key of the Policy
	 * @param rules
	 *            List containing rules applied on the Policy
	 */
	public void updatePolicyRule(Long pkPolicy, Set<Rule> rules) {
				sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		Policy pol = (Policy) session.load(Policy.class, pkPolicy);
		// Setting Rules in the POlicy
		pol.setRules(rules);
		session.persist(pol);
		tx.commit();
		session.close();
	}

	/**
	 * UPdating already created Policy
	 * 
	 * @param pkPolicy
	 *            Primary key of the Policy
	 * @param rules
	 *            List of rules applied on the Policy
	 */
	public void updatePolicy(Long pkPolicy, List<Rule> rules) {
		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		query = session.createQuery("from Policy p where p.pkPolicy ="
				+ pkPolicy);
		// Getting Policy
		Policy pol = (Policy) query.uniqueResult();

		pol.setRules(new HashSet<Rule>(rules));

		session.persist(pol);
		tx.commit();
		session.close();

	}

	/**
	 * Deleting Rules from a Policy
	 * 
	 * @param pol
	 *            Policy
	 * @param deleteRule
	 *            Already present Rules that are to be removed
	 */
	@SuppressWarnings("unchecked")
	public void deletePolicyRules(Policy pol, Rule deleteRule) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		if (!session.contains(pol))
			session.load(Policy.class, pol.getPkPolicy());
		List<Rule> updatedRuleSet = (List<Rule>) session.createQuery(
				"select p.rules from Policy p where p.pkPolicy = "
						+ pol.getPkPolicy()).list();

		Iterator<Rule> iter = updatedRuleSet.iterator();
		while (iter.hasNext()) {
			if (iter.next().getPkRule() == deleteRule.getPkRule())
				iter.remove();
		}

		pol.setRules(new HashSet<Rule>(updatedRuleSet));
		session.saveOrUpdate(pol);
		tx.commit();
		session.close();
	}

	/**
	 * Deleting creted Policy
	 * 
	 * @param pkPolicy
	 *            primary key of the Policy
	 */

	public void deletePolicy(Long pkPolicy) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		query = session.createQuery("from Policy p where p.pkPolicy = "
				+ pkPolicy);
		Policy pol = (Policy) query.uniqueResult();

		session.delete(pol);
		tx.commit();
		session.close();
	}

	/**
	 * Selecting applied rules on a policy
	 * 
	 * @param polPk
	 *            primary key of the policy
	 * @return list containing Rules that are applied on specified Policy
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Rule> selectRules(Long polPk) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		query = session
				.createQuery("select pol.rules from Policy pol where pol.pkPolicy ="
						+ polPk);
		ArrayList<Rule> appliedRules = (ArrayList<Rule>) query.list();

		tx.commit();
		session.close();
		return appliedRules;

	}

	/**
	 * Selecting applied obligation on a policy
	 * 
	 * @param polPk
	 *            primary key of the Policy
	 * @return List containing all the obligations applied on the Policy
	 */
	@SuppressWarnings("unchecked")
	public List<Obligations> selectObligations(Long polPk) {
		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		query = session
				.createQuery("select pol.obligations from Policy pol where pol.pkPolicy ="
						+ polPk);
		List<Obligations> appliedObligations = query.list();

		tx.commit();
		session.close();
		return appliedObligations;

	}

	/**
	 * Getting target of the policy
	 * 
	 * @param pkPolicy
	 *            primary key of the policy
	 * @return target of the specified policy
	 */
	public Target getTarget(Long pkPolicy) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Target targ = (Target) session.createQuery(
				"select p.target from Policy p where p.pkPolicy = " + pkPolicy)
				.uniqueResult();
		tx.commit();
		session.close();
		return targ;
	}

	/**
	 * Selecting Target
	 * 
	 * @param targetPk
	 *            orimary key of the target
	 * @return specified target
	 */
	public Target selectTarget(Long targetPk) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		query = session.createQuery("from Target tar where tar.pkTarget = "
				+ targetPk);
		Target tar = (Target) query.uniqueResult();

		tx.commit();
		session.close();
		return tar;

	}

	/**
	 * Deleting Policy
	 * 
	 * @param pkPolicy
	 *            primary key of the Policy
	 */
	public void deleteTarget(Long pkPolicy) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		query = session.createQuery("from Policy where pkPolicy =" + pkPolicy);
		Policy policy = (Policy) query.uniqueResult();
		policy.setTarget(null);

		session.update(policy);
		tx.commit();
		session.close();

	}

	/**
	 * Getting Rules that are not present in the Policy
	 * 
	 * @param pol
	 *            primary key of the policy
	 * @return List containing all the rules that are not present in the policy
	 */
	@SuppressWarnings("unchecked")
	public List<Rule> selectRulesToAdd(Policy pol) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		List<Rule> rulList = (List<Rule>) session
				.createQuery(
						"from Rule r where r.pkRule not in (select rul.pkRule from Policy p join p.rules rul where p.pkPolicy = "
								+ pol.getPkPolicy() + ")").list();

		tx.commit();
		session.close();
		return rulList;

	}

	/**
	 * Adding rules in the policy
	 * 
	 * @param pol
	 *            Specified Policy
	 * @param rul
	 *            List of rules that needs to be included in the policy
	 */
	@SuppressWarnings("unchecked")
	public void addPolicyRule(Policy pol, List<Rule> rul) {

		sessionFactory = HibernateUtil.configureSessionFactory();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		List<Rule> rulList = (List<Rule>) session.createQuery(
				"select p.rules from Policy p where p.pkPolicy = "
						+ pol.getPkPolicy()).list();

		rulList.addAll(rul);
		pol.setRules(new HashSet<Rule>(rulList));

		session.saveOrUpdate(pol);

		tx.commit();
		session.close();
	}
}
