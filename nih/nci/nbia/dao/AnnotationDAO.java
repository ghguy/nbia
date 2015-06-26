package gov.nih.nci.nbia.dao;

import gov.nih.nci.nbia.dto.AnnotationDTO;
import gov.nih.nci.ncia.db.DataAccessProxy;
import gov.nih.nci.ncia.db.Hibernate3DataAccess;
import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.Annotation;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class AnnotationDAO  {

	private Hibernate3DataAccess dataAccess;
	private static Logger logger = Logger.getLogger(AnnotationDAO.class);

	public AnnotationDAO(){
		try {
            dataAccess = (Hibernate3DataAccess) new DataAccessProxy().getInstance(IDataAccess.HIBERNATE3);
        } catch (Exception e) {
            throw new RuntimeException(
                "Could not initialize Hibernate3DataAccess in AnnotationDAO");
        }
	}

	public List<AnnotationDTO> findAnnotationBySeriesUid(String seriesInstanceUid){

		List<AnnotationDTO> annotationList = new ArrayList<AnnotationDTO>();

		 String query = "select distinct ann from Annotation ann  where ann.seriesInstanceUID = '"+
	        seriesInstanceUid + "'";
	        try {
	            dataAccess.open();

	            List<Annotation> results = dataAccess.search(query);
	            for(Annotation a:results){
	            	AnnotationDTO dto = new AnnotationDTO(a.getFilePath(), a.getFileName(),
	            			a.getAnnotationType(), a.getFileSize());
	            	annotationList.add(dto);

	            }
	            return annotationList;

	        } catch (Exception e) {
	           logger.error("Error getting annotation for Series: " + seriesInstanceUid + " " + e.getMessage());
	        }finally{
	        	closeConnection(dataAccess);
	        }
	        return null;

	}

	/**
     * Close out the connection to the db.  Move this into super class if we
     * ever set one up so all DAO have this. (and make it protected)
     */
    private static void closeConnection(Hibernate3DataAccess dataAccess) {
       if (dataAccess != null) {
           try {
               dataAccess.close();
           }
           catch(Exception e) {
               logger.error("Unable to close database connection ", e);
           }
       }
    }

}
