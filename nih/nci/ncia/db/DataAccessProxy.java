/**
* $Id: DataAccessProxy.java 11845 2010-01-21 20:43:58Z kascice $
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
public class DataAccessProxy implements IDataAccess {
    private IDataAccess access = null;

    /* (non-Javadoc)
     * @see gov.nih.nci.ncia.db.IDataAccess#open()
     */
    public void open() throws Exception {
        access.open();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.ncia.db.IDataAccess#close()
     */
    public void close() throws Exception {
        access.close();
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.ncia.db.IDataAccess#retrieve(java.lang.String)
     */
    public List search(String hql) throws Exception {
        return access.search(hql);
    }


    /* (non-Javadoc)
     * @see gov.nih.nci.ncia.db.IDataAccess#store(java.lang.Object)
     */
    public void store(Object o) {
        access.store(o);
    }

    public IDataAccess getInstance(int type) throws Exception {
        switch (type) {

        case IDataAccess.HIBERNATE3:
            access = Hibernate3DataAccess.getInstance();

            break;

        default:
            throw new Exception("type: " + type + " is not supported ");
        }

        return access;
    }

    public void flush() {
        access.flush();
    }
}
