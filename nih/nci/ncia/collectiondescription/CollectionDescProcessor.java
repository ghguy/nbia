/**
 * 
 */
package gov.nih.nci.ncia.collectiondescription;

import gov.nih.nci.ncia.dao.CollectionDescDAO;
import gov.nih.nci.ncia.dto.CollectionDescDTO;

import java.util.List;

/**
 * process request send from the UI
 * @author lethai
 *
 */
public class CollectionDescProcessor {
	private CollectionDescDAO collectionDescDAO;
	public CollectionDescProcessor(){
		collectionDescDAO = new CollectionDescDAO();
	}
	
	public List<String> getCollectionNames(){
		return collectionDescDAO.findCollectionNames();		
	}
	
	public CollectionDescDTO getCollectionDescByCollectionName(String collectionName){
		CollectionDescDTO collectionDescDTO =  collectionDescDAO.findCollectionDescByCollectionName(collectionName);
		//System.out.println("getCollectionDescByCollectionName: pkId = " + collectionDescDTO.getId() + " collectionName = " + collectionDescDTO.getCollectionName());
		return collectionDescDTO;
	}
	
	public long update(CollectionDescDTO dto){
		//System.out.println("Processor: CollectionName: " + dto.getCollectionName() + " \t description: " + dto.getDescription());
		return collectionDescDAO.save(dto);
	}
}
