package gov.nih.nci.ncia.domain.operation;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.TrialDataProvenance;
import gov.nih.nci.ncia.util.DicomConstants;

import java.util.Map;
import java.util.List;

public class TrialDataProvenanceOperation extends DomainOperation{
	
	public TrialDataProvenanceOperation(IDataAccess ida) {
		this.ida = ida;
	}
	
	public Object validate(Map numbers) throws Exception {
		String temp;
	    String hql = "from TrialDataProvenance as tdp where ";
	
	    TrialDataProvenance tdp = new TrialDataProvenance();
	
	    try {
		    if ((temp = (String) numbers.get(DicomConstants.PROJECT_NAME)) != null) {
		        hql += ("lower(tdp.project) = '" + temp.trim().toLowerCase() +
		        "' and ");
		        tdp.setProject(temp.trim());
		    } else {
		        hql += "tdp.project is null and ";
		    }
		
		    if ((temp = (String) numbers.get(DicomConstants.SITE_ID)) != null) {
		        hql += ("tdp.dpSiteId = '" + temp.trim() + "' and ");
		        tdp.setDpSiteId(temp.trim());
		    } else {
		        hql += "tdp.dpSiteId is null and ";
		    }
		
		    if ((temp = (String) numbers.get(DicomConstants.SITE_NAME)) != null) {
		        hql += ("lower(tdp.dpSiteName) = '" + temp.trim().toLowerCase() +
		        "' ");
		        tdp.setDpSiteName(temp.trim());
		    } else {
		        hql += "tdp.dpSiteName is null ";
		    }
		
		    List ret = (List)ida.search(hql);
		    if(ret != null  && ret.size() > 0) {
		    	if (ret.size() == 1) {
		    		tdp = (TrialDataProvenance) ret.get(0);
		    	}
		    	else if (ret.size() > 1)
		    	{
		    		throw new Exception("Trial_Data_Provenance table (" 
		    				+ (String)numbers.get(DicomConstants.PROJECT_NAME) + "/" 
		    				+ (String) numbers.get(DicomConstants.SITE_NAME) 
		    				+") has duplicate records, please contact Data Team to fix data, then upload data again");
		    	}
		    }
	    }catch(Exception e) {
	    	log.error("Exception in TrialDataProvenanceOperation " + e);
	    	throw new Exception("Exception in TrialDataProvenanceOperation: " + e);
	    }
	
	    return tdp;		
	}

}
