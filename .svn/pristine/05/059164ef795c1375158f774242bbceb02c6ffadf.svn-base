/**
* $Id: IDataAccess.java 11845 2010-01-21 20:43:58Z kascice $
*
* $Log: not supported by cvs2svn $
* Revision 1.1  2007/08/05 21:44:39  bauerd
* Initial Check in of reorganized components
*
* Revision 1.10  2006/09/27 20:46:28  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
/*
 * Created on Jul 12, 2005
 *
 *
 *
 */
package gov.nih.nci.ncia.db;

import java.util.List;


/**
 * @author chenjin
 *
 *
 *
 */
public interface IDataAccess {
    //public static final int LOCALTOOLKITAPI = 1;
    public static final int HIBERNATE3 = 2;
    //public static final int REMOTETOOLKITAPI = 3;

    public abstract void open() throws Exception;

    public abstract void close() throws Exception;

    public abstract List search(String HQL) throws Exception;

    public abstract void store(Object o);

    public abstract void flush();
}
