package gov.nih.nci.ncia.dbadapter;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.Annotation;
import gov.nih.nci.ncia.internaldomain.SubmissionHistory;
import gov.nih.nci.ncia.domain.operation.AnnotationOperation;
import gov.nih.nci.ncia.domain.operation.AnnotationSubmissionHistoryOperation;

import java.util.Date;
import org.apache.log4j.Logger;
import org.rsna.ctp.pipeline.Status;

public class AnnotationStorage {
	public enum AnnotationType {
		XML("xml"),
		ZIP("zip");

		AnnotationType(String annotationType) {
			this.annotationType = annotationType;
		}

		private String annotationType;

		public String toString() {
			return annotationType;
		}
	};

	/**
	 * This code used to be BuildQueriesMediator.storeZip and storeXml.
	 * Those two methods were exactly the same except for the annotation type.
	 *
	 * This method will create a row in the annotation table
	 */
	public Status storeAnnotation(IDataAccess ida,
			                      String studyInstanceUID,
			                      String seriesInstanceUID,
			                      String fileName,
			                      long fileSize,
			                      AnnotationType annotationType) {
		Annotation annotation = new Annotation();
        annotation.setAnnotationType(annotationType.toString());
        annotation.setFilePath(fileName);
        Integer i = Integer.valueOf((int)fileSize);
        annotation.setFileSize(i);
        annotation.setSubmissionDate(new Date());
        annotation.setSeriesInstanceUID(seriesInstanceUID);

        AnnotationOperation ao = new AnnotationOperation(ida);
        try{
        	ao.insertOrReplaceAnnotation(annotation,
        			                     fileName,
        			                     studyInstanceUID);
        }catch(Exception e) {
        	log.error("Exception in storeAnnotation " + e);
        	e.printStackTrace();
        	return Status.FAIL;
        }

        try {
        	AnnotationSubmissionHistoryOperation annotationSubmissionHistoryOperation =
        		new AnnotationSubmissionHistoryOperation(ida,
        				                                 studyInstanceUID,
        				                                 seriesInstanceUID);
        	SubmissionHistory submissionHistory =
        		(SubmissionHistory)annotationSubmissionHistoryOperation.validate(null);
            ida.store(submissionHistory);
        }
        catch(Exception e) {
        	log.error("Exception in storeAnnotation doing submission history " + e);
        	e.printStackTrace();
        	return Status.FAIL;

        }
		return Status.OK;
	}

	private static Logger log = Logger.getLogger(AnnotationStorage.class);
}
