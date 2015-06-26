package gov.nih.nci.ncia.beans.basket;

import gov.nih.nci.ncia.basket.BasketSeriesItemBean;
import gov.nih.nci.ncia.dao.DownloadDataDAO;

import java.util.Map;

public class DownloadRecorder {
    public void recordDownload(Map<String, BasketSeriesItemBean> seriesItemMap, 
    		                   String loginName) throws Exception {
        DownloadDataDAO downloadDAO = new DownloadDataDAO();

        for(String key : seriesItemMap.keySet()){
            BasketSeriesItemBean bsib = seriesItemMap.get(key);
            long size = bsib.getExactSize();
            downloadDAO.record(bsib.getSeriesId(), 
            		           loginName, 
            		           "classic", 
            		           size);
        }
    }
}
