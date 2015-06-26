package gov.nih.nci.ncia.deletion.dao;

import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.exception.DataAccessException;

public interface StudyDAO {
	public void removeStudy(Study study, String str) throws DataAccessException;
	public boolean checkStudyNeedToBeRemoved(Integer studyId, Integer count) throws DataAccessException;
	public int getTotalSeriesNumber(Integer studyId) throws DataAccessException;
	public Study getStudyObject(Integer studyId)throws DataAccessException;
}
