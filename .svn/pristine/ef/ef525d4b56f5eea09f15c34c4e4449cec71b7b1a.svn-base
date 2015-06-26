/**
 *
 */
package gov.nih.nci.nbia.dao;

import gov.nih.nci.nbia.dto.ImageDTO;
import gov.nih.nci.ncia.db.DataAccessProxy;
import gov.nih.nci.ncia.db.Hibernate3DataAccess;
import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.GeneralImage;
import gov.nih.nci.ncia.internaldomain.TrialDataProvenance;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author lethai
 *
 */
public class ImageDAO {
	private static Logger logger = Logger.getLogger(ImageDAO.class);
    private Hibernate3DataAccess dataAccess;

    public ImageDAO(){
    	try {
            dataAccess = (Hibernate3DataAccess) new DataAccessProxy().getInstance(IDataAccess.HIBERNATE3);
        } catch (Exception e) {
            throw new RuntimeException(
                "Could not initialize Hibernate3DataAccess in ImageDAO");
        }
    }

    /**
     * Return all the images for a given series.  Optionally exclude
     * sop instance uid's from the returned list.
     */
    public List<ImageDTO> findImagesBySeriesUid(String seriesUid,
    		                                    String exclusionSopUidList){
    	String query="";
    	System.out.println("seriesUid: " + seriesUid + " sopUidList: " + exclusionSopUidList);
    	if(exclusionSopUidList.equals("")){
			System.out.println("sopUidList is empty" );
    		 query = "select distinct gimg from GeneralImage gimg join gimg.dataProvenance dp where gimg.seriesInstanceUID = '"+
            seriesUid + "'";
    	}else{
			System.out.println("sopUidList is not null" );
    		query = "select distinct gimg from GeneralImage gimg join gimg.dataProvenance dp where gimg.seriesInstanceUID = '"+
            seriesUid + "' and gimg.SOPInstanceUID not in (" + exclusionSopUidList + ")";
    	}

    	try {
            dataAccess.open();

System.out.println("query: " + query);
            List<GeneralImage> results = dataAccess.search(query);
            List<ImageDTO> imageResults = new ArrayList<ImageDTO>();

            if(results == null || results.isEmpty()){
            	logger.info("No image found for request seriesuid="+seriesUid);
            	return imageResults;
            }
            TrialDataProvenance tdp = results.get(0).getDataProvenance();
            String ssg = results.get(0).getGeneralSeries().getSecurityGroup();
            for(GeneralImage gi: results){
            	ImageDTO image = new ImageDTO(gi.getSOPInstanceUID(),
            			gi.getFilename(),
            			gi.getDicomSize(),
            			tdp.getProject(),
            			tdp.getDpSiteName(),
            			ssg);
            	imageResults.add(image);
            }
            return imageResults;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("Error getting GeneralImage for SeriesUid: " + seriesUid + " " + e.getMessage());
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
