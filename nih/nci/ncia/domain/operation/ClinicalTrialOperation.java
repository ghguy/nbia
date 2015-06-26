/**
 * 
 */
package gov.nih.nci.ncia.domain.operation;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.ClinicalTrial;
import gov.nih.nci.ncia.util.DicomConstants;

import java.util.List;
import java.util.Map;

/**
 * @author lethai
 *
 */
public class ClinicalTrialOperation extends DomainOperation {
	
	public ClinicalTrialOperation(IDataAccess ida) {
		this.ida = ida;
	}
	
	public Object validate(Map numbers) throws Exception {
	    ClinicalTrial trial = null;
	    try {
		    // check if the trial has existed in database			    
		    List rs = (List)ida.search(buildQueryForExistingClinicalTrial(numbers));
		    if(rs !=null && rs.size()> 0) {
		    	if (rs.size() == 1) {
		    		trial = (ClinicalTrial)rs.get(0);
		    	}
		    	else 
		    	if (rs.size() > 1) {
		    		throw new Exception("Clincal_Trial table has duplicate records, please contact Data Team to fix data, then upload data again");
		    	}
		    }
		    //doesnt exist
		    else {
		    	trial = new ClinicalTrial();
		    	setProtocolId(trial, numbers);
		    	setSponsorName(trial, numbers);
		    }
		    
		    //these are properties that can be revised through a resubmission
		    setProtocolName(trial, numbers);
	
		    setCoordinatingCenter(trial, numbers);
	    }
	    catch(Exception e) {
	    	//log.error("Exception in ClinicalTrialOperation " + e);
	    	throw new Exception("Exception in ClinicalTrialOperation " + e);
	    }
	
	    return trial;
	}
	
	/**
	 * This builds HQL to find an existing ClinicalTrial based upon what is in the
	 * numbers table - the protocol id and trial/sponsor name or sponsor name are
	 * used to identify the clinical trial
	 */
	private static String buildQueryForExistingClinicalTrial(Map numbers) {
		StringBuilder hql = new StringBuilder("from ClinicalTrial as trial where ");
	  
    	String protocolId = (String) numbers.get(DicomConstants.CLINICAL_TRIAL_PROTOCOL_ID);
	    if (protocolId != null) {	 
	        hql.append(" trial.protocolId = '" + protocolId.trim() + "' ");
	    } 
	    else {
	        hql.append(" trial.protocolId is null ");
	    }
	
	    String sponsorName = (String) numbers.get(DicomConstants.CLINICAL_TRIAL_SPONSOR_NAME);
	    String trialName = (String) numbers.get(DicomConstants.TRIAL_NAME);
	    if (sponsorName != null) {	        
	        hql.append(" and lower(trial.sponsorName) = '" +
	        		   sponsorName.trim().toLowerCase() + "' ");
	    } 
	    else 
	    if (trialName != null) {	        
	        hql.append(" and lower(trial.sponsorName) = '" +
	        		   trialName.trim().toLowerCase() + "' ");
	    } 
	    else {
	        hql.append(" and trial.sponsorName is null ");
	    }
	    return hql.toString();
	}
	
	private static void setSponsorName(ClinicalTrial trial, Map numbers) {
		String sponsorName = (String) numbers.get(DicomConstants.CLINICAL_TRIAL_SPONSOR_NAME);
	    String trialName = (String) numbers.get(DicomConstants.TRIAL_NAME);
	    if (sponsorName != null) {
	        trial.setSponsorName(sponsorName.trim());
	    } 
	    else 
	    if (trialName != null) {
	        trial.setSponsorName(trialName.trim());		
	    }
	    //else do nothing
	}
	
	private static void setProtocolId(ClinicalTrial trial, Map numbers) {
    	String protocolId = (String) numbers.get(DicomConstants.CLINICAL_TRIAL_PROTOCOL_ID);
	    if (protocolId != null) {
	    	trial.setProtocolId(protocolId.trim());		
	    }
	}
		
	
	private static void setProtocolName(ClinicalTrial trial, Map numbers) {
		String clinicalTrialProtocolName = (String) numbers.get(DicomConstants.CLINICAL_TRIAL_PROTOCOL_NAME);
	    if (clinicalTrialProtocolName != null) {
	    	trial.setProtocolName(clinicalTrialProtocolName.trim());
	    }		
	}
	
	private static void setCoordinatingCenter(ClinicalTrial trial, Map numbers) {
		String coordinatingCenter = (String) numbers.get(DicomConstants.CLINICAL_TRIAL_COORDINATING_CENTER_NAME);
	    if (coordinatingCenter != null) {
	    	trial.setCoordinatingCenter(coordinatingCenter.trim());
	    }		
	}
}
