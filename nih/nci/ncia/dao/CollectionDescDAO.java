package gov.nih.nci.ncia.dao;

import gov.nih.nci.ncia.dto.CollectionDescDTO;
import gov.nih.nci.ncia.internaldomain.CollectionDesc;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * handle query for Editing collection description feature
 * @author lethai
 *
 */
public class CollectionDescDAO extends AbstractDAO {
	private static Logger logger = Logger.getLogger(CollectionDescDAO.class);
	/**
	 * retrieve list of collection name from trial data provenance table
	 */
	public List<String> findCollectionNames(){
		String hql =
	    	"select distinct dp.project "+
	    	"from TrialDataProvenance dp";	    	

        try {
	        dataAccess.open();

	        return (List<String>)dataAccess.search(hql);
        }
        catch(Exception e) {
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }		
	}
	
	public CollectionDescDTO findCollectionDescByCollectionName(String collectionName){
		List<CollectionDesc> collectionDescList = null;
		CollectionDescDTO dto = new CollectionDescDTO();
		dto.setCollectionName(collectionName);

        try {
	        dataAccess.open();
	        Criteria criteria = dataAccess.createCriteria(CollectionDesc.class);
	        criteria.add(Restrictions.eq("collectionName", collectionName));
			collectionDescList = criteria.list();

			if(collectionDescList != null && collectionDescList.size() == 1)	{
				CollectionDesc c = collectionDescList.get(0);
				dto.setDescription(c.getDescription());
				dto.setId(c.getId());
				dto.setUserName(c.getUserName());
			}
			return dto;
        }
        catch(Exception e) {
        	throw new RuntimeException(e);
        }
        finally {
        	closeConnection(dataAccess);
        }		
	}
	public long save(CollectionDescDTO collectionDescDTO){
		
		System.out.println("Save: id = " +collectionDescDTO.getId() + " description: " + collectionDescDTO.getDescription() +
				" collectionName: " + collectionDescDTO.getCollectionName());
		//find out whether this record already exist,
		//if yes, do update
		//else do insert
		Integer id = isCollectionExist(collectionDescDTO.getCollectionName());
		if(id != null){
			collectionDescDTO.setId(id);
			update(collectionDescDTO);
		}else{
			insert(collectionDescDTO);
		}
		return 1L;
	}
	protected void update(CollectionDescDTO collectionDescDTO){
		CollectionDesc collectionDesc = convertDTOToObject(collectionDescDTO);
		System.out.println("updating collection description");
		try {
			dataAccess.open();
			dataAccess.update(collectionDesc);
			dataAccess.flush();
			dataAccess.commit();
			System.out.println("pkId: " + collectionDesc.getId());
		} catch (Exception e) {
			error("updating", e);
		} finally {
			closeConnection(dataAccess);
		}
	}
	
	protected void insert(CollectionDescDTO collectionDescDTO){
		CollectionDesc collectionDesc = convertDTOToObject(collectionDescDTO);
		System.out.println("inserting record to collection_descriiption");
		try {
			dataAccess.open();
			dataAccess.store(collectionDesc);
			dataAccess.flush();
			dataAccess.commit();
			System.out.println("pkId: " + collectionDesc.getId());
		} catch (Exception e) {
			error("inserting", e);
		} finally {
			closeConnection(dataAccess);
		}
	}
	/**
	 * search the collectionDesc to determine if the collectionName already exists
	 * @param collectionName
	 * @return true if the collectionName exists, false otherwise.
	 */
	protected Integer isCollectionExist(String collectionName){
		CollectionDescDTO collectionDesc = findCollectionDescByCollectionName(collectionName);
		System.out.println("id: " + collectionDesc.getId());
		if(collectionDesc.getId() != null){
			return collectionDesc.getId();
		}
		return null;
		
	}
	
	private CollectionDesc convertDTOToObject(CollectionDescDTO collectionDescDTO){
		CollectionDesc collectionDesc = new CollectionDesc();
		collectionDesc.setCollectionName(collectionDescDTO.getCollectionName());
		collectionDesc.setDescription(collectionDescDTO.getDescription());
		collectionDesc.setUserName(collectionDescDTO.getUserName());
		collectionDesc.setId(collectionDescDTO.getId());
		collectionDesc.setCollectionDescTimestamp(new Date());
		return collectionDesc;
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
