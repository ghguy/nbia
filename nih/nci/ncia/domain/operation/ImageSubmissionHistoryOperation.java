package gov.nih.nci.ncia.domain.operation;

import java.util.Date;
import java.util.Map;

import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.Patient;
import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.internaldomain.SubmissionHistory;
import gov.nih.nci.ncia.internaldomain.TrialDataProvenance;
import gov.nih.nci.ncia.util.DicomConstants;

public class ImageSubmissionHistoryOperation extends DomainOperation {

	/**
	 * This will add a row to the submission_history table.  
	 * 
	 * @param replacement whether the image had already been submitted and is now being submitted again
	 * @param dataProvenance the provenance info for the image
	 * @param patient the patient for the image
	 * @param study the study for the image
	 * @param series the series for the image
	 */
	public ImageSubmissionHistoryOperation(boolean replacement,
			                               TrialDataProvenance dataProvenance,
			                               Patient patient,
			                               Study study,
			                               GeneralSeries series) {
		this.replacement = replacement;
		
		this.dataProvenance = dataProvenance;
		this.patient = patient;
		this.study = study;
		this.series = series;
	}

	public Object validate(Map numbers) throws Exception {
		
		//yep, always create a new one regardless
		SubmissionHistory submissionHistory = new SubmissionHistory();
		
		String sopInstanceUid = (String) numbers.get(DicomConstants.SOP_INSTANCE_UID);
		if(sopInstanceUid!=null) {
        	submissionHistory.setSOPInstanceUID(sopInstanceUid.trim());
        }
		else {
			throw new RuntimeException("Cannot have image submission history without SOP instance UID");
		}
        submissionHistory.setSubmissionDate(new Date());
        submissionHistory.setPatientId(patient.getPatientId());
        submissionHistory.setStudyInstanceUID(study.getStudyInstanceUID());
        submissionHistory.setSeriesInstanceUID(series.getSeriesInstanceUID());
        submissionHistory.setProject(dataProvenance.getProject());
        submissionHistory.setSite(dataProvenance.getDpSiteName());

        if(replacement) {
        	submissionHistory.setOperationType(SubmissionHistory.REPLACE_IMAGE_SUBMISSION_OPERATION);
        }
        else {
        	submissionHistory.setOperationType(SubmissionHistory.NEW_IMAGE_SUBMISSION_OPERATION);
        }
        
		return submissionHistory;
	}
	
    ///////////////////////////////PRIVATE////////////////////////////////
    private TrialDataProvenance dataProvenance;
    private Patient patient;
    private Study study;
    private GeneralSeries series;

    private boolean replacement;
}
