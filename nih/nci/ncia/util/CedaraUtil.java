package gov.nih.nci.ncia.util;

import gov.nih.nci.ncia.basket.BasketSeriesItemBean;
import gov.nih.nci.ncia.beans.searchresults.StudyResultWrapper;
import gov.nih.nci.ncia.beans.searchresults.SeriesResultWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CedaraUtil {
    //move into separate object to provide unit test
    public static String constructUidParameterString(List<StudyResultWrapper> studyResults) {
        int count = 0;
        String uid = null;
        for (int i = 0; i < studyResults.size(); i++) {
        	StudyResultWrapper curr = studyResults.get(i);
            List<SeriesResultWrapper> sList = curr.getSeriesResults();

            for (int j = 0; j < sList.size(); j++) {
            	SeriesResultWrapper item = sList.get(j);

                if (item.isChecked()) {
                    item.setChecked(false);

                    if (count > 0 ) {
                        uid = uid + "&uid=" + item.getSeries().getSeriesInstanceUid();
                    }
                    else {
                    	uid = item.getSeries().getSeriesInstanceUid();
                    }
                    ++count;
                }
            }
        }    
        return uid;
    }  
    
    
    public static boolean containsRemoteSeries(Collection<BasketSeriesItemBean> seriesItems) {
        for (BasketSeriesItemBean item : seriesItems) {
            if (item.isSelected() && !item.getSeriesSearchResult().associatedLocation().isLocal())
            {
                return true;
            }    
        }
        return false;        
    }
    
    public static boolean containsMultiplePatients(Collection<BasketSeriesItemBean> seriesItems) {
        String localNodeName = NCIAConfig.getLocalNodeName();
        List<String> pIds = new ArrayList<String>();

        for (BasketSeriesItemBean item : seriesItems) {
            if (item.getGridLocation().equalsIgnoreCase(localNodeName) && item.isSelected())
            {
                String pid = item.getPatientId();
                if(!pIds.contains(pid))
                {
                    pIds.add(pid);
                }
                if (pIds.size() > 1)
                {
                    return true;
                }  
            }
        }
        return false;        
    }    

    public static String constructUidParameterString(Collection<BasketSeriesItemBean> seriesItems) {
        String localNodeName = NCIAConfig.getLocalGridURI();

        int count = 0;
        String uid = null;
        for (BasketSeriesItemBean item : seriesItems) {
            if (item.getGridLocation().equalsIgnoreCase(localNodeName) && item.isSelected()) {
                if (count > 0 ) {
                    uid = uid + "&uid=" + item.getSeriesId();
                }
                else {
                    uid = item.getSeriesId();
                }
                ++count;
            }
        }
        return uid;
    }    
}
