package gov.nih.nci.ncia.domain.operation;

import gov.nih.nci.ncia.db.HibernateDataAccess;
import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.Patient;
import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.internaldomain.SubmissionHistory;
import gov.nih.nci.ncia.internaldomain.TrialDataProvenance;

import java.util.Date;
import java.util.Map;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class AnnotationSubmissionHistoryOperation extends DomainOperation {


	public AnnotationSubmissionHistoryOperation(IDataAccess ida,
			                                    String studyInstanceUid,
			                                    String seriesInstanceUid) {
		this.ida = ida;
		
		this.seriesInstanceUid = seriesInstanceUid;
        this.studyInstanceUid = studyInstanceUid;
	}
	
	public Object validate(Map numbers) throws Exception {
		
		//yep, always create a new one regardless
		SubmissionHistory submissionHistory = new SubmissionHistory();
        
        submissionHistory.setSubmissionDate(new Date());
        submissionHistory.setStudyInstanceUID(studyInstanceUid);
        submissionHistory.setSeriesInstanceUID(seriesInstanceUid);
        submissionHistory.setOperationType(SubmissionHistory.ANNOTATION_SUBMISSION_OPERATION);
        
        String patientId = figureOutPatientId(studyInstanceUid);
        if(patientId==null) {
        	throw new Exception("Cannot figure out patient ID for annotation submission:"+seriesInstanceUid);      	
        }
        else {
        	submissionHistory.setPatientId(patientId);
        	
            TrialDataProvenance tdp = figureOutTrialDataProvenance(patientId);
            if(tdp==null) {
            	throw new Exception("Cannot figure out Trial Data Provenance for annotation submission:"+seriesInstanceUid);      	
            }
            submissionHistory.setProject(tdp.getProject());
            submissionHistory.setSite(tdp.getDpSiteName());
        }
		return submissionHistory;
	}
	

    ///////////////////////////////PRIVATE////////////////////////////////
    private String studyInstanceUid;
    private String seriesInstanceUid;
    
    private TrialDataProvenance figureOutTrialDataProvenance(String patientId) {

        Criteria criteria = ((HibernateDataAccess)ida).createCriteria(Patient.class);
        criteria.setProjection(Projections.property("dataProvenance"));
        criteria.add(Restrictions.eq("patientId", patientId));

        
        List tdpList = criteria.list();
        if(tdpList.size()==0) {
        	return null;
        }
        else {
        	return (TrialDataProvenance)tdpList.get(0);
        }
    }
    
    
    private String figureOutPatientId(String studyInstanceUid) {

        Criteria criteria = ((HibernateDataAccess)ida).createCriteria(Study.class);
        criteria.setProjection(Projections.property("patient"));
        criteria.add(Restrictions.eq("studyInstanceUID", studyInstanceUid));

        
        List patientList = criteria.list();
        if(patientList.size()==0) {
        	return null;
        }
        else {
        	Patient patient = (Patient)patientList.get(0);
        	return (String)patient.getPatientId();
        }
    }
}
