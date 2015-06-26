/**
 * 
 */
package gov.nih.nci.ncia.domain.operation;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.GeneralEquipment;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.Patient;
import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.util.AdapterUtil;
import gov.nih.nci.ncia.util.DicomConstants;

/**
 * @author lethai
 *
 */
public class SeriesOperation extends DomainOperation {
	Logger log = Logger.getLogger(SeriesOperation.class);
	
	private Patient patient ;
	private Study study;
    private GeneralEquipment equip;
 //   private boolean visibility;
    
	public SeriesOperation(IDataAccess ida) {
		this.ida = ida;
	}
	/** 
	 * This method query NCIA repository for existing records related to Series prior to update or insert
	 * @param numbers Hashtable contains dicom headers information
	 * @throws Exception
	 */
	public Object validate(Map numbers) throws Exception{		
		
        String temp;
        String hql = "from GeneralSeries as series where ";
        GeneralSeries series = new GeneralSeries();

        try {
	        hql += (" series.study.id = " + study.getId());
	        hql += (" and series.generalEquipment.id = " + equip.getId());
	
	        series.setStudy(study);
	        series.setGeneralEquipment(equip);
	       
	        if ((temp = (String) numbers.get(DicomConstants.SERIES_INSTANCE_UID)) != null) {
	            series.setSeriesInstanceUID(temp.trim());
	            hql += (" and series.seriesInstanceUID = '" + temp.trim() + "' ");
	        } 
		    else {
		    	String noSeriesInstanceUidErrorMsg = "There is no Series Instance UID in the submitted image.";
		    	log.error(noSeriesInstanceUidErrorMsg);
		        throw new Exception(noSeriesInstanceUidErrorMsg);
		    }
	        
	        List rs =(List) ida.search(hql);	        
	        if(rs != null && rs.size()> 0) {
	        	//System.out.println("Series size returned: " + rs.size());
	        	if (rs.size() == 1)
	        	{
	        		series = (GeneralSeries) rs.get(0);
	        	}else if (rs.size() > 1)
	        	{
	        		throw new Exception("General_series table has duplicate records, please contact Data Team to fix data, then upload data again");
	        	}
	        }
	
	        populateSeriesFromNumbers(numbers, series);
	        
	        series.setPatientPkId(patient.getId());
	        series.setPatientId(patient.getPatientId());
	
//	        if (this.visibility) {
//	            series.setVisibility("1");
//	        }
//	        else
//	        {
//	        	series.setVisibility("0");
//	        }
	        //enforce to set visibility to be "Not Yet Reviewed"
	        //regardless what CTP annonymizer says.
	        if (series.getVisibility() == null)
	        {
	        	series.setVisibility("0");
	        }
        }catch(Exception e) {
        	//log.error("Exception in SeriesOperation " + e);
        	throw new Exception("Exception in SeriesOperation " + e);
        }
        return series;		
	}
	/**
	 * @param equip the equip to set
	 */
	public void setEquip(GeneralEquipment equip) {
		this.equip = equip;
	}
	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	/**
	 * @param study the study to set
	 */
	public void setStudy(Study study) {
		this.study = study;
	}

	
    private static void populateSeriesFromNumbers(Map numbers, 
                                                  GeneralSeries series) throws Exception {
        String temp;
       
    	if ((temp = (String) numbers.get(DicomConstants.MODALITY)) != null) {
            series.setModality(temp.trim());
        }
       
        if ((temp = (String) numbers.get(DicomConstants.LATERALITY)) != null) {
            series.setLaterality(temp.trim());
        }	
        if ((temp = (String) numbers.get(DicomConstants.SERIES_DATE)) != null) {
            series.setSeriesDate(AdapterUtil.stringToDate(temp.trim()));
        }	
        if ((temp = (String) numbers.get(DicomConstants.PROTOCOL_NAME)) != null) {
            series.setProtocolName(temp.trim());
        }	
        if ((temp = (String) numbers.get(DicomConstants.SERIES_DESCRIPTION)) != null) {
            series.setSeriesDesc(temp.trim());
        }	
        if ((temp = (String) numbers.get(DicomConstants.BODY_PART_EXAMINED)) != null) {
            series.setBodyPartExamined(temp.trim());
        }
        
        if ((temp = (String) numbers.get(
                        DicomConstants.CLINICAL_TRIAL_PROTOCOL_ID)) != null) {
            series.setTrialProtocolId(temp.trim());
        }	
        if ((temp = (String) numbers.get(
                        DicomConstants.CLINICAL_TRIAL_PROTOCOL_NAME)) != null) {
            series.setTrialProtocolName(temp.trim());
        }	
        if ((temp = (String) numbers.get(
                        DicomConstants.CLINICAL_TRIAL_SITE_NAME)) != null) {
            series.setTrialSiteName(temp.trim());
        }	
        if ((temp = (String) numbers.get(DicomConstants.STUDY_DATE)) != null) {
            series.setStudyDate(AdapterUtil.stringToDate(temp.trim()));
        }	
        if ((temp = (String) numbers.get(DicomConstants.STUDY_DESCRIPTION)) != null) {
            series.setStudyDesc(temp.trim());
        }	
        if ((temp = (String) numbers.get(
                        DicomConstants.ADMITTING_DIAGNOSES_DESCRIPTION)) != null) {
            series.setAdmittingDiagnosesDesc(temp.trim());
        }	
        if ((temp = (String) numbers.get(DicomConstants.PATIENT_AGE)) != null) {
            series.setPatientAge(temp.trim());
        }	
        if ((temp = (String) numbers.get(DicomConstants.PATIENT_SEX)) != null) {
            series.setPatientSex(temp.trim());
        }	
        if ((temp = (String) numbers.get(DicomConstants.PATIENT_WEIGHT)) != null) {
            series.setPatientWeight(Double.valueOf(temp.trim()));
        }	
        if ((temp = (String) numbers.get(DicomConstants.PATIENT_AGE)) != null) {
            series.setAgeGroup(AdapterUtil.convertToAgeGroup(temp.trim()));
        }	
        if ((temp = (String) numbers.get(DicomConstants.SERIES_ID)) != null) {
            series.setSeriesNumber(Integer.valueOf(temp.trim()));
        }	
        if ((temp = (String) numbers.get(
                        DicomConstants.SYNCHRONIZATION_FRAME_OF_REF_UID)) != null) {
            series.setSyncFrameOfRefUID(temp.trim());
        }	
        if ((temp = (String) numbers.get(DicomConstants.FRAME_OF_REFERENCE_UID)) != null) {
            series.setFrameOfReferenceUID(temp.trim());
        }	    	
    }
}
