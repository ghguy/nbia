package gov.nih.nci.ncia.deletion;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.nih.nci.ncia.deletion.dao.AnnotationDAO;
import gov.nih.nci.ncia.deletion.dao.ImageDAO;
import gov.nih.nci.ncia.deletion.dao.PatientDAO;
import gov.nih.nci.ncia.deletion.dao.SeriesDAO;
import gov.nih.nci.ncia.deletion.dao.StudyDAO;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.internaldomain.Study;
import gov.nih.nci.ncia.exception.DataAccessException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class ImageDeletionServiceImpl implements ImageDeletionService {
	private static Logger logger = Logger.getLogger(ImageDeletionServiceImpl.class);

	@Autowired
	private SeriesDAO seriesDao;
	@Autowired
	private AnnotationDAO annotationDao;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private StudyDAO studyDao;
	@Autowired
	private PatientDAO patientDao;

	private List<String> dicomFileNames;
	//JPG file end with xxx.dicom[512;512;-1].jpeg
	private List<String> annotationFile;

	@Transactional
	public Map<String, List<String>> removeSeries(String userName) throws DataAccessException{
		List<Integer> seriesList = seriesDao.listAllDeletedSeries();
		if (seriesList == null || seriesList.size() == 0)
		{
			//no need to do deletion
			logger.info("There is no series need to be deleted!!!");

			return null;
		}
		annotationDao.deleteAnnotation(seriesList, this);
		imageDAO.removeImages(seriesList, this); //including CT Images
		//record All studies by giving deleted series
		List<GeneralSeries> seriesObjects = seriesDao.getSeriesObject(seriesList);
		Map<Integer, Integer> studies = getStudies(seriesObjects);
		Map<Integer, Integer> patients = getPatients(seriesObjects);
		seriesDao.removeSeries(seriesList, userName);
		
		Map<Integer, Boolean>  patientsOfDeletedStudiesSet = new HashMap<Integer, Boolean>();
		//check study to see if they need to be removed or not
		Iterator<Integer> iterator = studies.keySet().iterator();
		while( iterator.hasNext() ){
			Integer studyId =  iterator.next();
			Study study = studyDao.getStudyObject(studyId);
			if (studyDao.checkStudyNeedToBeRemoved(studyId, studies.get(studyId)))
			{
				studyDao.removeStudy(study, userName);
				Integer patientId = study.getPatient().getId();
				patientsOfDeletedStudiesSet.put(patientId, true);
			}
		}

		//if study has not been removed, patient should not be removed.
			//check Patient to see if they need to be removed or not
		iterator = patients.keySet().iterator();
		while( iterator. hasNext() )
		{
			Integer patientId =  iterator.next();
			if (patientsOfDeletedStudiesSet.get(patientId) != null 
					&& patientsOfDeletedStudiesSet.get(patientId) == true
					&& patientDao.checkPatientNeedToBeRemoved(patientId, patients.get(patientId)))
			{
					patientDao.removePatient(patientDao.getPatientObject(patientId), userName);
			}
		
		}
		
		Map<String, List<String>> fileMap = new HashMap<String, List<String>>();
		fileMap.put("dicom", dicomFileNames);
		fileMap.put("annotation", annotationFile);

		return fileMap;


	}

	private Map<Integer, Integer> getPatients (List<GeneralSeries> seriesObjects)
	{
		//get all studies that series have been removed
		Map<Integer, List<Integer>> patientMap = new HashMap<Integer, List<Integer>>();
		for (GeneralSeries series : seriesObjects)
		{
			if (patientMap.get(series.getPatientPkId()) == null)
			{
				List<Integer> studyPkIdList = new ArrayList<Integer>();
				studyPkIdList.add(series.getStudy().getId());
				patientMap.put(series.getPatientPkId(), studyPkIdList);
			}
			else
			{
				List<Integer> studyPkIdList  = patientMap.get(series.getPatientPkId());
				if (!studyPkIdList.contains(series.getStudy().getId()))
				{
					studyPkIdList.add(series.getStudy().getId());
					patientMap.put(series.getPatientPkId(), studyPkIdList);
				}
			}
		}
		Map<Integer,Integer> studyHasBeenChangedInpatientMap = new HashMap<Integer, Integer>();
		
		Iterator<Integer> iterator = patientMap.keySet().iterator();
		while( iterator. hasNext() )
		{
			Integer patientId =  iterator.next();
			List<Integer> studiesPkIds = patientMap.get(patientId);
			studyHasBeenChangedInpatientMap.put(patientId, studiesPkIds.size());
		}
		return studyHasBeenChangedInpatientMap;
	}

	private Map<Integer, Integer> getStudies(List<GeneralSeries> seriesObjects)
	{
		Map<Integer, Integer> studyMap = new HashMap<Integer, Integer>();

		for (GeneralSeries series : seriesObjects)
		{
			if (studyMap.get(series.getStudy().getId()) == null)
			{
				int count=0;
				count++;
				studyMap.put(series.getStudy().getId(),new Integer(count));
			}else
			{
				Integer inc = studyMap.get(series.getStudy().getId());
				inc++;
				studyMap.put(series.getStudy().getId(), new Integer(inc));
			}
		}
		return studyMap;
	}

	public void setImageFileNames(List<String> files)
	{
		//Hold this list until deletion execution ends, then remove files. This is
		//because that we can make sure rollback happens, files are still there.
		this.dicomFileNames = files;
	}

	public List<String> getAnnotationFile() {
		return annotationFile;
	}

	public void setAnnotationFile(List<String> annotationFile) {
		this.annotationFile = annotationFile;
	}

	@Transactional
	public List<DeletionDisplayObject> getDeletionDisplayObject()
	{
		int count = 0;
		List<DeletionDisplayObject> object = new ArrayList<DeletionDisplayObject>();

		List<Integer> seriesList = seriesDao.listAllDeletedSeries();
		if (seriesList != null && seriesList.size() > 0)
		{
			List<GeneralSeries> seriesObjects = seriesDao.getSeriesObject(seriesList);
			for (GeneralSeries series : seriesObjects)
			{
				DeletionDisplayObject dObject = new DeletionDisplayObject();
				count++;
				dObject.setOrder(count);
				dObject.setSeriesUID(series.getSeriesInstanceUID());
				setProject(series, dObject);
				object.add(dObject);
			}
		}
		return object;
	}

	public void setProject(GeneralSeries series, DeletionDisplayObject dObject)
	{
		if (series.getStudy() == null || series.getStudy().getPatient() == null ||
				series.getStudy().getPatient().getDataProvenance() == null)
		{
			dObject.setProject("");
			dObject.setSite("");
		}
		else
		{
			dObject.setProject(series.getStudy().getPatient().getDataProvenance().getProject());
			dObject.setSite(series.getStudy().getPatient().getDataProvenance().getDpSiteName());
		}
	}
}
