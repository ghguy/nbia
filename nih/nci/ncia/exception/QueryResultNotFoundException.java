/**
* $Id: QueryResultNotFoundException.java 4417 2008-04-18 20:43:12Z saksass $
*
* $Log: not supported by cvs2svn $
* Revision 1.1  2007/08/05 21:52:15  bauerd
* *** empty log message ***
*
* Revision 1.1  2007/08/05 21:48:51  bauerd
* *** empty log message ***
*
* Revision 1.4  2006/09/27 20:46:28  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
/*
 * Created on Aug 17, 2005
 *
 *
 *
 */
package gov.nih.nci.ncia.exception;


/**
 * @author Prashant Shah - NCICB/SAIC
 *
 *
 *
 */
public class QueryResultNotFoundException extends Exception {
    private String errorMessage;

    /**
     *
     */
    public QueryResultNotFoundException() {
        super();
        errorMessage = "Cannot find Query Resultset.  Patient Collection is null";
    }

    /**
     * @param message
     */
    public QueryResultNotFoundException(String message) {
        super(message);
        errorMessage = message;
    }

    /**
     * @param message
     * @param cause
     */
    public QueryResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
        errorMessage = message;
    }

    /**
     * @param cause
     */
    public QueryResultNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @return Returns the errorMessage.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
