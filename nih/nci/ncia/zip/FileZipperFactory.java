/**
* $Id: FileZipperFactory.java 11888 2010-01-22 19:18:00Z kascice $
*
* $Log: not supported by cvs2svn $
* Revision 1.1  2007/08/05 21:52:15  bauerd
* *** empty log message ***
*
* Revision 1.1  2007/08/05 21:48:51  bauerd
* *** empty log message ***
*
* Revision 1.4  2006/09/27 20:46:27  panq
* Reformated with Sun Java Code Style and added a header for holding CVS history.
*
*/
package gov.nih.nci.ncia.zip;

import gov.nih.nci.ncia.util.NCIAConfig;


/**
 * Gets an instance of a file zipper according to settings in the config file
 *
 *
 */
public class FileZipperFactory {
    // Constants for zipper types
    private static final int JAVA_ZIPPER = 1;
    private static final int PASSWORD_ENCRYPT_ZIPPER = 2;

    /**
     * Determines the type of zipper to use and returns it
     *
     * @throws Exception
     */
    public static synchronized AbstractFileZipper getInstance()
        throws Exception {
        AbstractFileZipper zipper;

        switch (NCIAConfig.getZipperType()) {
        case JAVA_ZIPPER:
            zipper = new ZipFiles();

            break;

        case PASSWORD_ENCRYPT_ZIPPER:
        // Not supported at this time
        default:
            throw new Exception("Unknown zipper type");
        }

        return zipper;
    }
}
