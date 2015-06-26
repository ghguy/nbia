package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.deletion.ImageDeletionService;
import gov.nih.nci.ncia.internaldomain.Annotation;
import gov.nih.nci.ncia.exception.DataAccessException;

import java.util.List;

public interface AnnotationDAO {
	public void deleteAnnotation(List<Integer> seriesIDs, ImageDeletionService ids);
	public void processDeletion(List<Annotation> annotations) throws DataAccessException ;
	public List<Annotation> getAnnotations(List<Integer> seriesIds) throws DataAccessException ;
}
