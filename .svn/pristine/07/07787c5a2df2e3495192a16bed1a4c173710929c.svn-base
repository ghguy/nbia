/**
* $Id: MarkupDelegate.java 5092 2008-06-20 13:41:20Z kascice $
*
* $Log: not supported by cvs2svn $
* Revision 1.4  2007/10/12 16:15:33  panq
* Removed unused code.
*
*/

package gov.nih.nci.ncia.viewer;

import gov.nih.nci.ncia.dto.MarkupDTO;
import gov.nih.nci.ncia.markup.MarkupManager;
import gov.nih.nci.ncia.markup.MarkupManagerImpl;

public class MarkupDelegate {

	private MarkupManager mgr;

    /**
     * Constructor
     */
    public MarkupDelegate() { 
        mgr = new MarkupManagerImpl();
    }

    public void saveMarkup(String seriesUid, String loginName, String data){
        MarkupDTO dto = new MarkupDTO();
        dto.setMarkupData(data);
        dto.setSeriesUID(seriesUid);
        dto.setUsrLoginName(loginName);
        mgr.saveMarkup(dto);
    }
    
    public String getMarkups(String seriesUid) {
        MarkupDTO dto = new MarkupDTO();
        dto.setSeriesUID(seriesUid);
        return mgr.getMarkups(dto);
    }
    
    public boolean markupExist(String seriesUid) {
        MarkupDTO dto = new MarkupDTO();
        dto.setSeriesUID(seriesUid);
        return mgr.markupExist(dto);
    }
}
