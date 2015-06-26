package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.dto.CustomSeriesDTO;
import gov.nih.nci.ncia.dto.CustomSeriesListAttributeDTO;
import gov.nih.nci.ncia.dto.CustomSeriesListDTO;
import gov.nih.nci.ncia.dto.QcCustomSeriesListDTO;
import gov.nih.nci.ncia.internaldomain.CustomSeriesList;
import gov.nih.nci.ncia.internaldomain.CustomSeriesListAttribute;
import gov.nih.nci.ncia.internaldomain.GeneralSeries;
import gov.nih.nci.ncia.security.NCIAUser;
import gov.nih.nci.ncia.util.SiteData;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * handle all database transactions for the custom series list
 * 
 * @author lethai
 * 
 */
/**
 * @author lethai
 *
 */
public class CustomSeriesListDAO extends AbstractDAO {

	private static Logger logger = Logger.getLogger(CustomSeriesListDAO.class);
	private GeneralSeriesDAO generalSeriesDAO = new GeneralSeriesDAO();

	/**
	 * This should be package constructor called by factory.
	 */
	public CustomSeriesListDAO() {

	}

	/**
	 * query database table to check for existence of name
	 * 
	 * @param name
	 */
	public boolean isDuplicateName(String name) {
		List<CustomSeriesList> customList = null;
		try {
			dataAccess.open();
			Criteria criteria = dataAccess
					.createCriteria(CustomSeriesList.class);
			criteria.add(Expression.eq("name",name.trim()).ignoreCase());
			customList = criteria.list();
			if (customList.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			error("find duplicate name", e);
		}finally{
			closeConnection(dataAccess);
		}

		return false;
	}
	/**
	 * find series that belongs to public group
	 * @param seriesUids
	 * @param authorizedPublicSites
	 */
	public List<CustomSeriesDTO> findSeriesForPublicCollection(List<String> seriesUids,
									List<SiteData> authorizedPublicSites){
		List<GeneralSeries> seriesList = null;
		List<CustomSeriesDTO> seriesDTOList = null;

		if (seriesUids == null || seriesUids.size() <= 0) {
			return new ArrayList<CustomSeriesDTO>();
		}
		try {
			dataAccess.open();
			
			Criteria criteria = dataAccess.createCriteria(GeneralSeries.class);
			criteria.add(Restrictions.in("seriesInstanceUID", seriesUids));
			criteria.add(Restrictions.eq("visibility", "1"));
			criteria = criteria.createCriteria("study");
			criteria = criteria.createCriteria("patient");
			criteria = criteria.createCriteria("dataProvenance");
			if (authorizedPublicSites != null) {
				setAuthorizedSiteData(criteria,
						authorizedPublicSites);
			}

			seriesList = criteria.list();
			System.out.println("total series for public count: " + seriesList.size());
			seriesDTOList = convertHibernateObjectToCustomSeriesDTO(seriesList);
			System.out.println("total seriesDTO for public count: " + seriesDTOList.size());
		} catch (Exception e) {
			error("findSeriesBySeriesInstanceUids", e);
		} finally {
			closeConnection(dataAccess);
		}
		return seriesDTOList;
	}

	/**
	 * find all series that contains all the seriesuids and user has permission
	 * to see
	 * 
	 * @param seriesUids
	 * @param authorizedSites
	 * @param authorizedSeriesSecurityGroups
	 */
	public List<CustomSeriesDTO> findSeriesBySeriesInstanceUids(
			List<String> seriesUids, List<SiteData> authorizedSites,
			List<String> authorizedSeriesSecurityGroups) throws Exception {
		List<GeneralSeries> seriesList = null;
		List<CustomSeriesDTO> seriesDTOList = null;

		if (seriesUids == null || seriesUids.size() <= 0) {
			return new ArrayList<CustomSeriesDTO>();
		}
		try {
			dataAccess.open();
			seriesList = generalSeriesDAO
					.getSeriesFromSeriesInstanceUIDs(seriesUids,
							authorizedSites, authorizedSeriesSecurityGroups);
			seriesDTOList = convertHibernateObjectToCustomSeriesDTO(seriesList);
		} catch (Exception e) {
			error("findSeriesBySeriesInstanceUids", e);
		} finally {
			closeConnection(dataAccess);
		}
		return seriesDTOList;
	}
	
	/**
	 * Find all shared list by a given list of series instance uids
	 * @param seriesUids
	 */
	public List<QcCustomSeriesListDTO> findSharedListBySeriesInstanceUids(List<String> seriesUids){
		List<QcCustomSeriesListDTO> returnList = new ArrayList<QcCustomSeriesListDTO>();
		try {
			if (seriesUids != null && seriesUids.size() != 0){
			dataAccess.open();

			Criteria criteria = dataAccess.createCriteria(CustomSeriesListAttribute.class);

			ProjectionList projectionList = Projections.projectionList();

			projectionList.add(Projections.property("csl.userName"));
			projectionList.add(Projections.property("csl.name"));
			projectionList.add(Projections.property("seriesInstanceUid"));

			criteria.setProjection(Projections.distinct(projectionList));
		
			criteria.createAlias("parent", "csl");

			criteria.add(Restrictions.in("seriesInstanceUid", seriesUids));

			List<Object[]> results = criteria.list();
		
			 for (Object[] row : results){
				String uName = (String) row[0];
				String name = (String) row[1];
				String series = (String) row[2];			
				String email = findEmailByUserName(uName);				
				returnList.add(new QcCustomSeriesListDTO(uName, name, series, email));
			}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			error("findCustomSeriesListByUser", e);
		} finally {
			closeConnection(dataAccess);
		}
		return returnList;
	}
	
	public String findEmailByUserName(String uName) throws Exception { 
		Criteria criteria = dataAccess.createCriteria(NCIAUser.class);
		criteria.setProjection(Projections.property("email"));
	
		criteria.add(Restrictions.eq("loginName", uName.trim()));
		List<String> results = criteria.list();
		if (results != null && results.size() > 0)
		{	
			return results.get(0);
		}
		else
		{
			return null;
		}
	}

	/**
	 * update database with data in the dto
	 * 
	 */
	public long update(CustomSeriesListDTO editList, String userName, Boolean updatedSeries) {
		CustomSeriesList seriesList = new CustomSeriesList();
		seriesList.setName(editList.getName());
		seriesList.setComment(editList.getComment());
		seriesList.setHyperlink(editList.getHyperlink());
		seriesList.setId(editList.getId());
		seriesList.setCustomSeriesListTimestamp(new Date());
		seriesList.setUserName(userName);
		List<CustomSeriesListAttributeDTO> attributeDtos = editList
				.getSeriesInstanceUidsList();
		
		
		try {
			dataAccess.open();
			dataAccess.beginTransaction();
			if(updatedSeries){
				logger.debug("updatedSeries = " + updatedSeries + "  ... delete then insert... ");
				CustomSeriesList existingList = (CustomSeriesList)dataAccess.load(CustomSeriesList.class, editList.getId());
				Set<CustomSeriesListAttribute> children = existingList.getCustomSeriesListAttributes();				
				logger.debug("total children to delete: " + children.size());
				existingList.getCustomSeriesListAttributes().removeAll(children);
				for(Iterator<CustomSeriesListAttribute> itr = children.iterator(); itr.hasNext();){
					dataAccess.delete((CustomSeriesListAttribute)itr.next());
				}
				dataAccess.flush();
				existingList.setName(editList.getName());
				existingList.setComment(editList.getComment());
				existingList.setHyperlink(editList.getHyperlink());
				existingList.setCustomSeriesListTimestamp(new Date());
				existingList.setUserName(userName);
				
				for (CustomSeriesListAttributeDTO dto : attributeDtos) {
					CustomSeriesListAttribute attr = new CustomSeriesListAttribute();
					attr.setSeriesInstanceUid(dto.getSeriesInstanceUid());
					attr.setCustomSeriesListPkId(existingList.getId());
					existingList.addChild(attr);
				}
				dataAccess.update(existingList);
			}else{
				logger.debug("updatedSeries = " + updatedSeries + " ... updating..");
				dataAccess.update(seriesList);		
			}
			dataAccess.flush();
			dataAccess.commit();
		} catch (Exception e) {
			error("updating", e);
			dataAccess.rollback();
		} finally {
			closeConnection(dataAccess);
		}
		return 1L;
	}

	/**
	 * insert a new record for the custom series list
	 * 
	 * @param customList
	 */
	public long insert(CustomSeriesListDTO customList, String username) {
		CustomSeriesList seriesList = new CustomSeriesList();
		seriesList.setName(customList.getName());
		seriesList.setComment(customList.getComment());
		seriesList.setHyperlink(customList.getHyperlink());
		seriesList.setUserName(username);
		seriesList.setCustomSeriesListTimestamp(new Date());
		List<String> seriesUids = customList.getSeriesInstanceUIDs();

		for (String seriesInstanceUid : seriesUids) {
			CustomSeriesListAttribute attr = new CustomSeriesListAttribute();
			attr.setSeriesInstanceUid(seriesInstanceUid);
			seriesList.addChild(attr);
		}
		Integer pkId = 0;
		try {
			dataAccess.open();
			dataAccess.beginTransaction();
			dataAccess.store(seriesList);
			dataAccess.flush();

			pkId = seriesList.getId();
			dataAccess.commit();
		} catch (Exception e) {
			error("inserting", e);
		} finally {
			closeConnection(dataAccess);
		}
		return pkId;
	}

	
	/**
	 * find all the shared list for a given user
	 * @param username
	 */
	public List<CustomSeriesListDTO> findCustomSeriesListByUser(String username) {
		List<CustomSeriesList> customSeriesList = null;
		List<CustomSeriesListDTO> returnList = null;
		try {
			dataAccess.open();
			Criteria criteria = dataAccess
					.createCriteria(CustomSeriesList.class);
			criteria.add(Expression.eq("userName",username).ignoreCase());
			customSeriesList = criteria.list();
			returnList = convertHibernateObjectToCustomSeriesListDTOList(customSeriesList);
		} catch (Exception e) {
			error("findCustomSeriesListByUser", e);
		} finally {
			closeConnection(dataAccess);
		}

		return returnList;
	}

	public CustomSeriesListDTO findCustomSeriesListByName(String name) {
		List<CustomSeriesList> customSeriesList = null;
		CustomSeriesListDTO returnList = null;
		try {
			dataAccess.open();

			Criteria criteria = dataAccess
					.createCriteria(CustomSeriesList.class);
			criteria.add(Expression.eq("name",name).ignoreCase());
			customSeriesList = criteria.list();
			if (customSeriesList != null && customSeriesList.size() == 0) {
				return returnList;
			}
			returnList = convertHibernateObjectToCustomSeriesListDTO(customSeriesList);

			// System.out.println("returning search by name.................");
		} catch (Exception e) {
			error("findCustomSeriesListByName", e);
			// e.printStackTrace();
			// throw new RuntimeException(e);
		} finally {
			closeConnection(dataAccess);
		}

		return returnList;
	}

	public List<CustomSeriesListAttributeDTO> findCustomSeriesListAttribute(
			Integer customSeriesListPkId) {
		List<CustomSeriesListAttribute> customSeriesListAttribute = null;
		List<CustomSeriesListAttributeDTO> returnList = null;
		try {
			dataAccess.open();
			Criteria criteria = dataAccess
					.createCriteria(CustomSeriesListAttribute.class);
			criteria.add(Restrictions.eq("customSeriesListPkId",
					customSeriesListPkId));
			customSeriesListAttribute = criteria.list();
			returnList = convertHibernateObjectToCustomSeriesListAttributeDTO(customSeriesListAttribute);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(dataAccess);
		}

		return returnList;
	}

	private List<CustomSeriesListDTO> convertHibernateObjectToCustomSeriesListDTOList(
			List<CustomSeriesList> daos) throws Exception {
		List<CustomSeriesListDTO> returnList = new ArrayList<CustomSeriesListDTO>();
		 System.out.println("returned: " +daos.size());
		if (daos != null) {
			for (CustomSeriesList dataRow : daos) {
				Date d = dataRow.getCustomSeriesListTimestamp();
				CustomSeriesListDTO dto = new CustomSeriesListDTO();
				dto.setName(dataRow.getName());
				dto.setComment(dataRow.getComment());
				dto.setHyperlink((String) dataRow.getHyperlink());
				dto.setDate(d);
				dto.setId(dataRow.getId());
				dto.setUserName(dataRow.getUserName());
				Set<CustomSeriesListAttribute> children = dataRow.getCustomSeriesListAttributes();
				List<String> seriesUidsList = new ArrayList<String>();
				for (Iterator<CustomSeriesListAttribute> iter = children
						.iterator(); iter.hasNext();) {
					CustomSeriesListAttribute attr = iter.next();
					String seriesInstanceUid = attr.getSeriesInstanceUid();
					seriesUidsList.add(seriesInstanceUid);
				}
				dto.setSeriesInstanceUIDs(seriesUidsList);
				returnList.add(dto);
			}
		} 
		return returnList;
	}

	private CustomSeriesListDTO convertHibernateObjectToCustomSeriesListDTO(
			List<CustomSeriesList> daos) throws Exception {
		List<CustomSeriesListDTO> returnList = new ArrayList<CustomSeriesListDTO>();
		//if (daos != null) {
			// this should contain only 1 record for customSeriesList
			for (int i = 0; i < daos.size(); i++) {
				CustomSeriesListDTO dto = new CustomSeriesListDTO();
				CustomSeriesList series = (CustomSeriesList) daos.get(0);
				Set<CustomSeriesListAttribute> children = series
						.getCustomSeriesListAttributes();
				List<String> seriesUidsList = new ArrayList<String>();
				for (Iterator<CustomSeriesListAttribute> iter = children
						.iterator(); iter.hasNext();) {
					CustomSeriesListAttribute attr = iter.next();
					String seriesInstanceUid = attr.getSeriesInstanceUid();
					seriesUidsList.add(seriesInstanceUid);					
				}
				dto.setName(series.getName());
				dto.setComment(series.getComment());
				dto.setHyperlink(series.getHyperlink());
				dto.setDate(series.getCustomSeriesListTimestamp());
				dto.setId(series.getId());
				dto.setSeriesInstanceUIDs(seriesUidsList);
				dto.setUserName(series.getUserName());
				returnList.add(dto);
			}
		/*} else {
			throw new Exception("No record found the query.");
		}*/
		return returnList.get(0);
	}

	private List<CustomSeriesDTO> convertHibernateObjectToCustomSeriesDTO(
			List<GeneralSeries> daos) throws Exception {
		List<CustomSeriesDTO> returnList = new ArrayList<CustomSeriesDTO>();
		if (daos != null) {
			for (int i = 0; i < daos.size(); i++) {
				CustomSeriesDTO sd = new CustomSeriesDTO();
				GeneralSeries gs = (GeneralSeries) (daos.get(i));
				sd.setAnnotationsFlag(gs.getAnnotationsFlag() == null ? false
						: gs.getAnnotationsFlag());
				sd.setAnnotationsSize(gs.getAnnotationTotalSize());
				sd.setDescription(gs.getSeriesDesc());
				sd.setModality(gs.getModality());
				sd.setNumberImages(gs.getImageCount());
				sd.setPatientId(gs.getPatientId());
				sd.setSeriesUID(gs.getSeriesInstanceUID());
				sd.setStudyUid(gs.getStudy().getStudyInstanceUID());
				sd.setStudyPkId(gs.getStudy().getId());
				sd.setProject(gs.getStudy().getPatient().getDataProvenance()
						.getProject());
				sd.setSecurityGroup(gs.getSecurityGroup());
				returnList.add(sd);
			}
		}
		return returnList;
	}

	private List<CustomSeriesListAttributeDTO> convertHibernateObjectToCustomSeriesListAttributeDTO(
			List<CustomSeriesListAttribute> daos) throws Exception {
		List<CustomSeriesListAttributeDTO> returnList = new ArrayList<CustomSeriesListAttributeDTO>();

		if (daos != null) {
			// this should contain only 1 record for customSeriesList
			for (int i = 0; i < daos.size(); i++) {
				CustomSeriesListAttribute rowData = daos.get(i);
				CustomSeriesListAttributeDTO dto = new CustomSeriesListAttributeDTO(
						rowData.getId(), rowData.getSeriesInstanceUid());
				returnList.add(dto);
			}
		}/* else {
			throw new Exception("No record found for the query.");
		}*/
		return returnList;
	}


	private static void setAuthorizedSiteData(Criteria criteria, List<SiteData> sites){
		Disjunction disjunction = Restrictions.disjunction();

		for (SiteData sd : sites){
			Conjunction con = new Conjunction();
			con.add(Restrictions.eq("dpSiteName",sd.getSiteName()));
			con.add(Restrictions.eq("project", sd.getCollection()));
			disjunction.add(con);
		}
		criteria.add(disjunction);
	}
	/**
	 * handle error
	 * 
	 * @param e
	 */
	private void error(String message, Exception e) {
		logger.error("Error " + message + " " + e);
		dataAccess.rollback();
		throw new RuntimeException(e);
	}
}