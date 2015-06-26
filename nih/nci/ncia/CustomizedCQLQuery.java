/**
 * 
 */
package gov.nih.nci.ncia;

import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Group;
import gov.nih.nci.cagrid.cqlquery.Predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * @author lethai
 *
 */
public class CustomizedCQLQuery {
	private static final String IMAGE_DOMAIN = "gov.nih.nci.ncia.domain.Image";
	private static final String SERIES_DOMAIN = "gov.nih.nci.ncia.domain.Series";
	private static final String STUDY_DOMAIN = "gov.nih.nci.ncia.domain.Study";
	private static final String PATIENT_DOMAIN = "gov.nih.nci.ncia.domain.Patient";
	private static final String SERIES_ROLE = "series";
	private static final String STUDY_ROLE = "study";
	private static final String PATIENT_ROLE = "patient";
	private static List<String> attributeList = null;		
	
	private static Logger logger = Logger
	.getLogger(CustomizedCQLQuery.class);
	
	
	/**
	 * This method takes the requested cqlQuery, modify it so that Image is the returned target. This is to be used in the 
	 * retrieveDicomData method only so that we can access SOPInstanceUID from Image object before using JDBC to query the 
	 * DICOM_FILE_URI for each image.
	 */
	public CQLQuery modifyCQLQuery(CQLQuery cqlQuery){
		
		gov.nih.nci.cagrid.cqlquery.Object o = (gov.nih.nci.cagrid.cqlquery.Object)cqlQuery.getTarget();
		
		if (o.getName().equalsIgnoreCase(IMAGE_DOMAIN)){
			logger.debug("target is image, do not need to do anything");
			return cqlQuery;
		}else if(o.getName().equalsIgnoreCase(SERIES_DOMAIN)){
			cqlQuery = processSeriesDomain(cqlQuery);			
			
		}else if(o.getName().equalsIgnoreCase(STUDY_DOMAIN)){	
			cqlQuery = processStudyDomain(cqlQuery);
			
		}else if(o.getName().equalsIgnoreCase(PATIENT_DOMAIN)){
			cqlQuery = processPatientDomain(cqlQuery);
			
		}
		
		return cqlQuery;
	}
	
	private CQLQuery processSeriesDomain(CQLQuery cqlQuery){
		gov.nih.nci.cagrid.cqlquery.Object o = (gov.nih.nci.cagrid.cqlquery.Object)cqlQuery.getTarget();
		gov.nih.nci.cagrid.cqlquery.Association assoc = o.getAssociation();
		gov.nih.nci.cagrid.cqlquery.Attribute attr = o.getAttribute();
		gov.nih.nci.cagrid.cqlquery.Group group = o.getGroup();		
		
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target.setName(IMAGE_DOMAIN);
		
		gov.nih.nci.cagrid.cqlquery.Association seriesAssociation = new gov.nih.nci.cagrid.cqlquery.Association();	
		seriesAssociation.setName(SERIES_DOMAIN);
		seriesAssociation.setRoleName(SERIES_ROLE);
		if(assoc != null){
			seriesAssociation.setAssociation(assoc);
		}	
		
		if(attr != null){
			logger.debug("attribute name is " + attr.getName() + " value is " + attr.getValue() );
			seriesAssociation.setAttribute(attr);
		}
		if(group != null){
			logger.debug("group logical relation is " + group.getLogicRelation() );
			seriesAssociation.setGroup(group);
		}
		target.setAssociation(seriesAssociation);
		cqlQuery.setTarget(target);
		
		return cqlQuery;
		
	}
	
	private CQLQuery processStudyDomain(CQLQuery cqlQuery){
		gov.nih.nci.cagrid.cqlquery.Object o = (gov.nih.nci.cagrid.cqlquery.Object)cqlQuery.getTarget();
		gov.nih.nci.cagrid.cqlquery.Association assoc = o.getAssociation();
		gov.nih.nci.cagrid.cqlquery.Attribute attr = o.getAttribute();
		gov.nih.nci.cagrid.cqlquery.Group group = o.getGroup();	
		
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target.setName(IMAGE_DOMAIN);
		
		gov.nih.nci.cagrid.cqlquery.Association seriesAssociation = new gov.nih.nci.cagrid.cqlquery.Association();	
		seriesAssociation.setName(SERIES_DOMAIN);
		seriesAssociation.setRoleName(SERIES_ROLE);
		
		gov.nih.nci.cagrid.cqlquery.Association studyAssociation = new gov.nih.nci.cagrid.cqlquery.Association();
		studyAssociation.setName(STUDY_DOMAIN);
		studyAssociation.setRoleName(STUDY_ROLE);
		
		seriesAssociation.setAssociation(studyAssociation);
		target.setAssociation(seriesAssociation);
		
		if(assoc != null){
			logger.debug(" association is " + assoc.getName());
			studyAssociation.setAssociation(assoc);
		}
		
		if(attr != null){
			logger.debug("attribute name is " + attr.getName() + " value is " + attr.getValue() );
			studyAssociation.setAttribute(attr);
		}
		if(group != null){
			logger.debug("group logical relation is " + group.getLogicRelation() );
			studyAssociation.setGroup(group);
		}
		cqlQuery.setTarget(target);		
		return cqlQuery;
	}
	
	private CQLQuery processPatientDomain(CQLQuery cqlQuery){
		gov.nih.nci.cagrid.cqlquery.Object o = (gov.nih.nci.cagrid.cqlquery.Object)cqlQuery.getTarget();
		gov.nih.nci.cagrid.cqlquery.Association assoc = o.getAssociation();
		gov.nih.nci.cagrid.cqlquery.Attribute attr = o.getAttribute();
		gov.nih.nci.cagrid.cqlquery.Group group = o.getGroup();	
		
		gov.nih.nci.cagrid.cqlquery.Object target = new gov.nih.nci.cagrid.cqlquery.Object();
		target.setName(IMAGE_DOMAIN);
		
		gov.nih.nci.cagrid.cqlquery.Association seriesAssociation = new gov.nih.nci.cagrid.cqlquery.Association();	
		seriesAssociation.setName(SERIES_DOMAIN);
		seriesAssociation.setRoleName(SERIES_ROLE);
		
		gov.nih.nci.cagrid.cqlquery.Association associationStudy = new gov.nih.nci.cagrid.cqlquery.Association();
		associationStudy.setName(STUDY_DOMAIN);
		associationStudy.setRoleName(STUDY_ROLE);
		seriesAssociation.setAssociation(associationStudy);
		
		gov.nih.nci.cagrid.cqlquery.Association associationPatient = new gov.nih.nci.cagrid.cqlquery.Association();
		associationPatient.setName(PATIENT_DOMAIN);
		associationPatient.setRoleName(PATIENT_ROLE);
		
		associationStudy.setAssociation(associationPatient);
		target.setAssociation(seriesAssociation);
		if(assoc != null){
			associationPatient.setAssociation(assoc);
		}
		
		if(attr != null){
			logger.debug("attribute name is " + attr.getName() + " value is " + attr.getValue() );
			associationPatient.setAttribute(attr);
		}
		if(group != null){
			logger.debug("group logical relation is " + group.getLogicRelation() );
			associationPatient.setGroup(group);
		}
		cqlQuery.setTarget(target);	
		return cqlQuery;
	}
	
	
	
	public static Map<String,String> parseCQLQuery(CQLQuery cqlQuery){
		gov.nih.nci.cagrid.cqlquery.Object o = (gov.nih.nci.cagrid.cqlquery.Object)cqlQuery.getTarget();
		Map<String, String > h = new HashMap<String, String>();		
		processTarget(o, h);	
		
		return h;
	}
	
	
	
	public CustomizedCQLQuery(){	
		if (attributeList == null){
			initAttributeListValues();	
		}
	}
	
	private static void processTarget(gov.nih.nci.cagrid.cqlquery.Object target, 
			                          Map<String, String> hm){
		//System.out.println("processing target: " + target.getName());
		if (target.getAttribute() != null) {
			processAttribute(target.getAttribute(), hm);
		}
		
		if(target.getAssociation() != null){
			processAssociation(target.getAssociation(), hm);
		}
	
		if(target.getGroup() != null){
			processGroup(target.getGroup(), hm);
		}
		/*System.out.println("done... printing out results..............");
		Set<String> keys = hm.keySet();
		Iterator itr = keys.iterator();
		while(itr.hasNext()){
			String s = (String)itr.next();
			System.out.println("key: " + s + " value: " + hm.get(s));
			//System.out.println("value inside hashmap: " + itr.next().toString());
		}*/
			
	}
	private static void processAssociation(Association association, Map<String,String> hm){
		//System.out.println("processing association " + association.getName() );
		if(association.getAttribute() != null){
			processAttribute(association.getAttribute(), hm);
		}
		if(association.getGroup() != null){
			processGroup(association.getGroup(), hm);
		}
		if(association.getAssociation() != null){
			processAssociation(association.getAssociation(), hm);
		}
	}
	
	
	private static void processAttribute(Attribute attribute, Map<String,String> hm){
		//System.out.println("processing attribute: " + attribute.getName());
		//System.out.println("predicate value: " + attribute.getPredicate().getValue());
		
		if(attribute.getPredicate().getValue().equals(Predicate._EQUAL_TO) &&
		   attributeList.contains(attribute.getName())){
			//System.out.println("attribute value: " + attribute.getValue());
			hm.put(attribute.getName(), attribute.getValue());			
		}			
	}
	
	
	private static void processGroup(Group group, Map<String,String> hm){
		//System.out.println("processing group: " + group.getLogicRelation());
		if (group.getAssociation() != null) {
			for (int i = 0; i < group.getAssociation().length; i++) {				
				processAssociation(group.getAssociation(i), hm);				
			}
		}
		
		if (group.getAttribute() != null) {	
			//System.out.println("group attribute..... length: " + group.getAttribute().length);
			for (int i = 0; i < group.getAttribute().length; i++) {				
				processAttribute(group.getAttribute(i), hm);				
			}
		}
		if (group.getGroup() != null) {
			//System.out.println("group group.....");
			for (int i = 0; i < group.getGroup().length; i++) {
				processGroup(group.getGroup(i), hm);				
			}
		}
	}
	
	private void initAttributeListValues(){
		attributeList = new ArrayList<String>();
		attributeList.add("sopInstanceUID");
		attributeList.add("patientId");
		attributeList.add("studyInstanceUID");
		attributeList.add("seriesInstanceUID");
		attributeList.add("project");
		attributeList.add("siteName");
	}
}
