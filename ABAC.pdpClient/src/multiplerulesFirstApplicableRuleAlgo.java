import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jboss.security.xacml.core.model.context.ActionType;
import org.jboss.security.xacml.core.model.context.AttributeType;
import org.jboss.security.xacml.core.model.context.EnvironmentType;
import org.jboss.security.xacml.core.model.context.RequestType;
import org.jboss.security.xacml.core.model.context.ResourceType;
import org.jboss.security.xacml.core.model.context.SubjectType;
import org.jboss.security.xacml.factories.RequestAttributeFactory;
import org.junit.Test;
import org.picketlink.identity.federation.api.soap.SOAPSAMLXACML;
import org.picketlink.identity.federation.api.soap.SOAPSAMLXACML.Result;

import pdp.identity_federation.picketlink.testing;


public class multiplerulesFirstApplicableRuleAlgo {
	
	private boolean sendRequest = true;  
	  
	   private String endpoint = "http://localhost:13389/pdp/SOAPServlet";  
	  
	   private String issuer = "testIssuer";  
	   
	   public static void main (String args[]) throws Exception
	   {
		   testing as = new testing();
		   as.testPermit();
	   }
	  
	   @Test  
	   public void testPermit() throws Exception  
	   {  
	      if(sendRequest)  
	      {  
	         //Create an XACML Request  
	         RequestType xacmlRequest = getXACMLRequest(true);  
	         SOAPSAMLXACML soapSAMLXACML = new SOAPSAMLXACML();  
	  
	         Result result = soapSAMLXACML.send(endpoint, issuer, xacmlRequest);  
	         assertTrue("No fault", result.isFault() == false);  
	         assertTrue("Decision available", result.isResponseAvailable());  
	         assertTrue("Permit", result.isPermit());  
	      }  
	   }  
	  
	 
	  
	  
	   private RequestType getXACMLRequest( boolean permit)  
	   {  
	      RequestType requestType = new RequestType();  
	      requestType.getSubject().add(createSubject());  
	      requestType.getResource().add(createResource(permit));  
	     requestType.setAction(createAction());  
	     
	     
	      return requestType;  
	   }  
	  
	   private SubjectType createSubject()  
	   {  
	      //Create a subject type  
	      SubjectType subject = new SubjectType();  
	      subject.setSubjectCategory("urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");  
	  
	      subject.getAttribute().addAll(getSubjectAttributes());   
	  
	      return subject;  
	   }  
	  
	   public ResourceType createResource(boolean permit)  
	   {    
	      ResourceType resourceType = new ResourceType();  
	  
	      AttributeType attResourceID_1 = RequestAttributeFactory.createStringAttributeType(  
	    		  "urn:oasis:names:tc:xacml:1.0:resource:filename", "doctor",   
	            "grading file"); 
	      
	     
	  
	     
	      //Add the attributes into the resource  
	      resourceType.getAttribute().add(attResourceID_1);
	      
	     
	     
	  
	     
	  
	        
	  
	      return resourceType;  
	   }  
	  
	   private ActionType createAction()  
	   {  
	      ActionType actionType = new ActionType();  
	      AttributeType attActionID_1 = RequestAttributeFactory.createStringAttributeType(  
	            "urn:oasis:names:tc:xacml:1.0:action:fileopen", "jboss.org", "read"); 
	      
	      AttributeType attActionID_2 = RequestAttributeFactory.createStringAttributeType(  
		            "urn:oasis:names:tc:xacml:1.0:action:editfile", "jboss.org", "write"); 
	      actionType.getAttribute().add(attActionID_1);
	      actionType.getAttribute().add(attActionID_2);
	      return actionType;  
	   }  
	   
	   
	   
	  
	  
	   private List<AttributeType> getSubjectAttributes()  
	   {  
	      List<AttributeType> attrList = new ArrayList<AttributeType>();  
	  
	      //create the subject attributes  
	  
	      //SubjectID - Bob  
	      AttributeType attSubjectID_1 = RequestAttributeFactory.createStringAttributeType(  
	            "urn:oasis:names:tc:xacml:1.0:subject:name", "doctor", "doctor"); 
	      
	     
	  
	      
	      attrList.add(attSubjectID_1);  
	      
	  
	      return attrList;  
	   }  
	  
	   
	  
	   private XMLGregorianCalendar getXMLTime( String time)  
	   {  
	      DatatypeFactory dtf;  
	      try  
	      {  
	         dtf = DatatypeFactory.newInstance();  
	      }  
	      catch (DatatypeConfigurationException e)  
	      {  
	         throw new RuntimeException(e);  
	      }  
	      return  dtf.newXMLGregorianCalendar(time);  
	   }  

}
