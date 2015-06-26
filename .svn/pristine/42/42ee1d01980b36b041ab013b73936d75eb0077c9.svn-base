/**
 * 
 */
package gov.nih.nci.ncia.domain.operation;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.ClinicalTrial;
import gov.nih.nci.ncia.internaldomain.TrialSite;
import gov.nih.nci.ncia.util.DicomConstants;

import java.util.Map;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author lethai
 *
 */
public class TrialSiteOperation extends DomainOperation {
	Logger log = Logger.getLogger(TrialSiteOperation.class);
	
	private ClinicalTrial trial;
	public TrialSiteOperation(IDataAccess ida) {
		this.ida = ida;
	}
	public Object validate(Map numbers) throws Exception {
		String temp;
	    String hql = "from TrialSite as site where ";
	
	    TrialSite site = new TrialSite();
	    
	    try {
	
	    if ((temp = (String) numbers.get(DicomConstants.CLINICAL_TRIAL_SITE_ID)) != null) {
	        site.setTrialSiteId(temp.trim());
	        hql += (" site.trialSiteId = '" + temp.trim() + "' ");
	    } else {
	        hql += " site.trialSiteId is null ";
	    }	
	    
	    List ret = (List)ida.search(hql);
	    if(ret != null  && ret.size() > 0) {
	    	if (ret.size() == 1) {
	    		site = (TrialSite)ret.get(0);
	    	}
	    	else 
	    	if (ret.size() > 1) {
	    		throw new Exception("Trial_Site table has has duplicate records, please contact Data Team to fix data, then upload data again");
	    	}
	    }
	    site.setTrial(trial);
	
	    if ((temp = (String) numbers.get(
	                    DicomConstants.CLINICAL_TRIAL_SITE_NAME)) != null) {
	        site.setTrialSiteName(temp.trim());
	    }
	    }catch(Exception e) {
	    	log.error("Exception in TrialSiteOperation " + e);
	    	throw new Exception("Exception in TrialSiteOperation " + e);
	    }
	    return site;
	}
	/**
	 * @param trial the trial to set
	 */
	public void setClinicalTrial(ClinicalTrial trial) {
		this.trial = trial;
	}
}
