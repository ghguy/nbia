package gov.nih.nci.ncia.zip;

import gov.nih.nci.ivi.utils.ZipEntryInputStream;
import gov.nih.nci.nbia.util.NBIAIOUtils;
import gov.nih.nci.ncia.search.SeriesSearchResult;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.IOUtils;

public class ZipReaderUtil {
	/**
	 * The input stream should be a zip containing (DICOM image) files that
	 * correspond to a given series.
	 *
	 * <p>In the results directory, construct an output directory
	 * hierarchy of project/patient/study/series based upon the series
	 * search result.
	 *
	 * <p>Unzip the zip stream into the series directory.
	 *
	 * @param progressDelegate callback to incrementally report bytes copied (up to caller to know total).
	 *                         this can be null.
	 */
	public static void readZipOfImageFilesForSeries(File resultsDirectory,
			                                        InputStream istream,
			                                        SeriesSearchResult seriesSearchResult,
			                                        NBIAIOUtils.ProgressInterface progressDelegate) throws Exception {
        ZipInputStream zis = new ZipInputStream(istream);
        ZipEntryInputStream zeis = null;

        while(true) {
        	try {
        		//advantage in buffering this stream?
        		zeis = new ZipEntryInputStream(zis);
        	}
        	catch (EOFException e) {
        		break;
            }

            String localLocation = resultsDirectory.getAbsolutePath() +
                                   File.separator +
                                   seriesSearchResult.getProject() +
                                   File.separator +
                                   seriesSearchResult.getPatientId() +
                                   File.separator +
                                   seriesSearchResult.getStudyInstanceUid() +
                                   File.separator +
                                   seriesSearchResult.getSeriesInstanceUid();

        	//System.out.println("writing:"+localLocation + File.separator + zeis.getName());
        	File projectPatientStudySeriesImageFile = new File(localLocation +
        			                                           File.separator +
        			                                           zeis.getName());
        	boolean mkdirResult = projectPatientStudySeriesImageFile.getParentFile().mkdirs();
			if(mkdirResult==false) {
				System.out.println("couldnt create directory - might already exist tho so not failing");
			}


            OutputStream fos = new FileOutputStream(projectPatientStudySeriesImageFile);
            try {
            	NBIAIOUtils.copy(zeis, fos, progressDelegate);
            }
            finally {
    			IOUtils.closeQuietly(fos);
    			IOUtils.closeQuietly(zeis);
            }
        }
        //let caller close istream
	}
}
