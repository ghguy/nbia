/**
* $Id: QueryHistoryComparator.java 10492 2009-10-19 13:03:17Z kascice $
*
* $Log: not supported by cvs2svn $
* Revision 1.1  2007/08/07 12:05:11  bauerd
* *** empty log message ***
*
* Revision 1.1  2007/08/05 21:44:38  bauerd
* Initial Check in of reorganized components
*
* Revision 1.3  2006/09/27 20:46:27  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
package gov.nih.nci.ncia.beans.savedquery;

import gov.nih.nci.ncia.dto.QueryHistoryDTO;

import java.util.Comparator;
import java.util.Date;


public class QueryHistoryComparator implements Comparator<QueryHistoryDTO> {
    public int compare(QueryHistoryDTO one, QueryHistoryDTO two) {
        if ((one == null) || (two == null)) {
            return 0;
        }

        return ((Date) two.getExecutionTime()).compareTo(((Date) one.getExecutionTime()));
    }
}