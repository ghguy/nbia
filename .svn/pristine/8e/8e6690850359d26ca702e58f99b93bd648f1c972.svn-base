package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.dto.EquipmentDTO;
import gov.nih.nci.ncia.dto.SeriesDTO;
import gov.nih.nci.ncia.util.HqlUtils;
import gov.nih.nci.ncia.util.SiteData;
import gov.nih.nci.ncia.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

public class GeneralSeriesDAO extends AbstractDAO {

	public Collection<String> findProjectsOfVisibleSeries() {
		String hql =
	    	"select distinct dp.project "+
	    	"from TrialDataProvenance dp, Patient p, GeneralSeries gs "+
	    	"where dp.id = p.dataProvenance.id and gs.visibility = '1' and gs.patientPkId = p.id "+
	    	"order by dp.project";

        try {
	        dataAccess.open();

	        return (Collection<String>)dataAccess.search(hql);
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
	}

	public Collection<EquipmentDTO> findEquipmentOfVisibleSeries() {
		String hql =
        	"select distinct e.manufacturer, e.manufacturerModelName, e.softwareVersions "+
        	"from GeneralSeries s join s.generalEquipment e "+
        	"where s.visibility = '1' and e.manufacturer is not null "+
        	"order by e.manufacturer";

        try {
	        dataAccess.open();

	        Collection<EquipmentDTO> equipment = new ArrayList<EquipmentDTO>();
	        List<Object[]> equipmentRows = (List<Object[]>)dataAccess.search(hql);
	        for(Object[] equipmentRow : equipmentRows) {
	        	EquipmentDTO dto = new EquipmentDTO((String)equipmentRow[0],
	        			(String)equipmentRow[1],
	        					(String)equipmentRow[2]);
	        	equipment.add(dto);
	        }

	        return equipment;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
	}


	public Collection<String> findDistinctBodyPartsFromVisibleSeries() {
		String hql =
			"select distinct ucase(bodyPartExamined) "+
			"from GeneralSeries "+
			"where visibility = '1' "+
			"order by bodyPartExamined";

        try {
	        dataAccess.open();

	        return (Collection<String>)dataAccess.search(hql);
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
	}

	public Collection<String> findDistinctModalitiesFromVisibleSeries() {
		String hql =
			"select distinct modality "+
			"from GeneralSeries "+
			"where visibility = '1' and modality is not null "+
			"order by modality";

        try {
	        dataAccess.open();

	        return (Collection<String>)dataAccess.search(hql);
        }
        catch(Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
	}


    public List<SeriesDTO> getDataForSeries(Integer seriesPkId) throws Exception {
        // Create a list to pass to the other method
        List<Integer> ids = new ArrayList<Integer>(1);
        ids.add(seriesPkId);

        return findSeriesBySeriesPkId(ids);
    }


	/**
	 * This returns the series objects by their primary keys.  This method
	 * does NOT look at authorization of any kind.
	 */
	public List<SeriesDTO> findSeriesBySeriesPkId(Collection<Integer> seriesPkIds) {
        List<List<Integer>> chunks = Util.breakListIntoChunks(new ArrayList<Integer>(seriesPkIds),
                                                              CHUNK_SIZE);


        List<SeriesDTO> resultSets = new ArrayList<SeriesDTO>();

        String selectStmt = SQL_QUERY_SELECT;
        String fromStmt = SQL_QUERY_FROM;
        String whereStmt = "";


        try {
	        dataAccess.open();

	        // Run the query
	        long start = System.currentTimeMillis();
	        for (List<Integer> chunk : chunks) {
	            whereStmt = HqlUtils.buildInClauseUsingIntegers(SQL_QUERY_WHERE +"series.id IN ",
	            		                                        chunk);

		        List resultsData = dataAccess.search(selectStmt + fromStmt + whereStmt);
		        long end = System.currentTimeMillis();
		        logger.info("Data basket query: " + selectStmt + fromStmt + whereStmt);
		        logger.info("total query time: " + (end - start) + " ms");

		        // Map the rows retrieved from hibernate to the DataBasketResultSet objects.
		        for (Object item : resultsData) {
		            Object[] row = (Object[]) item;

		            SeriesDTO seriesDTO = new SeriesDTO();
		            seriesDTO.setPatientId(row[1].toString());
		            seriesDTO.setSeriesId(row[3].toString());
		            seriesDTO.setSeriesPkId((Integer) row[0]);
		            seriesDTO.setStudyId(row[2].toString());
		            seriesDTO.setStudyPkId((Integer) row[4]);
		            seriesDTO.setTotalImagesInSeries((Integer) row[5]);
		            seriesDTO.setTotalSizeForAllImagesInSeries((Long) row[6]);
		            seriesDTO.setProject(row[7].toString());
		            if (row[8] == null) {
		            	seriesDTO.setAnnotationsFlag(Boolean.FALSE);
		            }
		            else {
		            	seriesDTO.setAnnotationsFlag((Boolean) row[8]);
		            }
		            seriesDTO.setAnnotationsSize((row[9] != null) ? (Long) row[9] : 0);
		            seriesDTO.setSeriesNumber(Util.nullSafeString(row[10]));
		            seriesDTO.setDescription(Util.nullSafeString(row[11]));
		            seriesDTO.setModality(row[12].toString());
		            
		            resultSets.add(seriesDTO);
		        }
	        }

	        return resultSets;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	logger.error("Exception in DataBasketQueryHandler: " + e);
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }
    }

	/**
	 * Return all the series for a given list of series instance UIDs IGNORING
	 * authorization.
	 */
	public List<SeriesDTO> findSeriesBySeriesInstanceUID(List<String> seriesIds) throws Exception
	{
		return findSeriesBySeriesInstanceUID(seriesIds, null, null);
	}

	/**
	 * Return all the series for a given list of patients, but only when
	 * the series are authorized.
	 */
	public List<SeriesDTO> findSeriesByPatientId(List<String> patientIDs,
			                                     List<SiteData> authorizedSites,
			                                     List<String> authroizedSeriesSecurityGroups) throws Exception
	{
		try {
			dataAccess.open();
			List<GeneralSeries> gsList = getSeriesFromPatients(patientIDs,
                                                               authorizedSites,
                                                               authroizedSeriesSecurityGroups);
			return convertHibernateObjectToSeriesDTO(gsList);
		}
		catch(Exception e) {
			throw e;
		}
		finally{
			closeConnection(dataAccess);
		}
	}


	/**
	 * Return all the series for a given list of studies, but only when
	 * the series are authorized.
	 */
	public List<SeriesDTO> findSeriesByStudyInstanceUid(List<String> studyInstanceUids,
			                                            List<SiteData> authorizedSites,
			                                            List<String> authroizedSeriesSecurityGroups) throws Exception
	{

		if (studyInstanceUids == null || studyInstanceUids.size() <= 0){
			return null;
		}
		try
		{
			dataAccess.open();
			List<GeneralSeries> seriesList = getSeriesFromStudys(studyInstanceUids,
					                                             authorizedSites,
					                                             authroizedSeriesSecurityGroups);
			return convertHibernateObjectToSeriesDTO(seriesList);
		}catch(Exception e)
		{
			throw e;
		}finally
		{
			closeConnection(dataAccess);
		}
	}


	/**
	 * Return all the series for a given list of series instance UIDs, but only when
	 * the series are authorized.
	 */
	public List<SeriesDTO> findSeriesBySeriesInstanceUID(List<String> seriesIds,
			                                             List<SiteData> authorizedSites,
			                                             List<String> authorizedSeriesSecurityGroups) throws Exception
	{
		List<GeneralSeries> seriesList = null;
		List<SeriesDTO> seriesDTOList = null;

		if (seriesIds == null || seriesIds.size() <= 0){
			return null;
		}
		try
		{
			dataAccess.open();
			seriesList = getSeriesFromSeriesInstanceUIDs(seriesIds,
                                                         authorizedSites,
                                                         authorizedSeriesSecurityGroups);
			seriesDTOList = convertHibernateObjectToSeriesDTO(seriesList);
		}catch(Exception e)
		{
			throw e;
		}finally{
			closeConnection(dataAccess);
		}
		return seriesDTOList;
	}

	public SeriesDTO getGeneralSeriesByPKid(Integer seriesPkId)
	{
		SeriesDTO series = null;
		try{
			dataAccess.open();
			Criteria criteria = dataAccess.createCriteria(GeneralSeries.class);
			criteria.add(Restrictions.eq("id", seriesPkId));
			
			List<GeneralSeries> result = criteria.list();
			if (result != null && result.size() > 0)
			{
				GeneralSeries gs = (GeneralSeries)result.get(0);
				series = new SeriesDTO();
				series.setProject(gs.getStudy().getPatient().getDataProvenance().getProject());
				series.setDataProvenanceSiteName(gs.getStudy().getPatient().getDataProvenance().getDpSiteName());
			}
		}catch (Exception e){
			logger.error(e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		finally{
			closeConnection(dataAccess);
		}
		return series;
	}
	//////////////////////////////////////////PRIVATE/////////////////////////////////////////

    private static int CHUNK_SIZE = 500;

    private static String SQL_QUERY_SELECT = "SELECT series.id, patient.patientId, study.studyInstanceUID, series.seriesInstanceUID, study.id, series.imageCount, series.totalSize, dp.project, series.annotationsFlag, series.annotationTotalSize, series.seriesNumber, series.seriesDesc, series.modality ";
    private static String SQL_QUERY_FROM = "FROM Study study join study.generalSeriesCollection series join study.patient patient join patient.dataProvenance dp ";
    private static String SQL_QUERY_WHERE = "WHERE ";


	private static Logger logger = Logger.getLogger(GeneralSeriesDAO.class);

	private List<GeneralSeries> getSeriesFromStudys(List<String> studyIDs,
			                                        List<SiteData> authorizedSites,
			                                        List<String> authorizedSeriesSecurityGroups)
	throws Exception
	{
		List<GeneralSeries> seriesList = null;
		Criteria criteria = dataAccess.createCriteria(GeneralSeries.class);
		setSeriesSecurityGroups(criteria, authorizedSeriesSecurityGroups);
		criteria.add(Restrictions.eq("visibility", "1"));
		criteria = criteria.createCriteria("study");
		criteria.add(Restrictions.in("studyInstanceUID", studyIDs));
		criteria = criteria.createCriteria("patient");
		criteria = criteria.createCriteria("dataProvenance");
		setAuthorizedSiteData(criteria, authorizedSites);

		seriesList = criteria.list();

		return seriesList;
	}

	private List<GeneralSeries> getSeriesFromPatients(List<String> patientIDs,
			                                          List<SiteData> authorizedSites,
			                                          List<String> authorizedSeriesSecurityGroups)
	throws Exception
	{
		List<GeneralSeries> seriesList = null;

		Criteria criteria = dataAccess.createCriteria(GeneralSeries.class);
		setSeriesSecurityGroups(criteria, authorizedSeriesSecurityGroups);
		criteria.add(Restrictions.in("patientId", patientIDs));
		criteria.add(Restrictions.eq("visibility", "1"));
		criteria = criteria.createCriteria("study");
		criteria = criteria.createCriteria("patient");
		criteria = criteria.createCriteria("dataProvenance");
		setAuthorizedSiteData(criteria, authorizedSites);

		seriesList = criteria.list();
		return seriesList;
	}
	
	protected List<GeneralSeries> getSeriesFromSeriesInstanceUIDs(List<String> seriesIds,
			                                                    List<SiteData> authorizedSites,
			                                                    List<String> authorizedSeriesSecurityGroups)
	throws Exception
	{
		List<GeneralSeries> seriesList = null;
		Criteria criteria = dataAccess.createCriteria(GeneralSeries.class);
		if(authorizedSeriesSecurityGroups!=null) {
			setSeriesSecurityGroups(criteria, authorizedSeriesSecurityGroups);
		}
		criteria.add(Restrictions.in("seriesInstanceUID", seriesIds));
		criteria.add(Restrictions.eq("visibility", "1"));
		criteria = criteria.createCriteria("study");
		criteria = criteria.createCriteria("patient");
		criteria = criteria.createCriteria("dataProvenance");
		if(authorizedSites!=null) {
			setAuthorizedSiteData(criteria, authorizedSites);
		}

		seriesList = criteria.list();
		return seriesList;
	}

	private List<SeriesDTO> convertHibernateObjectToSeriesDTO(List<GeneralSeries> daos)
	throws Exception
	{
		List<SeriesDTO> returnList = new ArrayList<SeriesDTO>();
		if(daos != null)
		{
			for(int i = 0; i < daos.size(); i++)
			{
				SeriesDTO sd = new SeriesDTO();
				GeneralSeries gs = (GeneralSeries)(daos.get(i));
				sd.setAnnotationsFlag(gs.getAnnotationsFlag()==null?false:gs.getAnnotationsFlag());
				sd.setAnnotationsSize(gs.getAnnotationTotalSize());
				sd.setDescription(gs.getSeriesDesc());
				sd.setSeriesPkId(gs.getId());
				sd.setModality(gs.getModality());
				sd.setNumberImages(gs.getImageCount());
				sd.setPatientId(gs.getPatientId());
				sd.setSeriesId(gs.getSeriesInstanceUID());
				sd.setSeriesNumber(""+gs.getSeriesNumber());
				sd.setSeriesPkId(gs.getId());
				sd.setSeriesUID(gs.getSeriesInstanceUID());
				sd.setStudyId(gs.getStudy().getStudyInstanceUID());
				sd.setStudyPkId(gs.getStudy().getId());
				sd.setTotalImagesInSeries(gs.getImageCount());
				sd.setTotalSizeForAllImagesInSeries(gs.getTotalSize());
				sd.setProject(gs.getStudy().getPatient().getDataProvenance().getProject());
				returnList.add(sd);
			}
		}
		return returnList;
	}


	private static void setAuthorizedSiteData(Criteria criteria, List<SiteData> sites)
	{
		Disjunction disjunction = Restrictions.disjunction();


		for (SiteData sd : sites)
		{
			Conjunction con = new Conjunction();
			con.add(Restrictions.eq("dpSiteName",sd.getSiteName()));
			con.add(Restrictions.eq("project", sd.getCollection()));
			disjunction.add(con);
		}
		criteria.add(disjunction);
	}

	private static void setSeriesSecurityGroups(Criteria criteria, List<String> securityGroups)
	{
		Conjunction con = new Conjunction();

		if (securityGroups != null && securityGroups.size() != 0)
		{
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.isNull("securityGroup"));
			disjunction.add(Restrictions.in("securityGroup", securityGroups));
			con.add(disjunction);
			criteria.add(con);
		}
		else
		{
			criteria.add(Restrictions.isNull("securityGroup"));
		}
	}
}
