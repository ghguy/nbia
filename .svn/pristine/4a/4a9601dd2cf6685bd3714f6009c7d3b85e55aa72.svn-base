package gov.nih.nci.cagrid.ncia.service;

import static java.lang.System.out;
import gov.nih.nci.ivi.utils.ZipEntryOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

class ZipWorker implements Runnable {
	public ZipWorker(OutputStream outputStream, 
			         Map<String, String> fileNames) {
		this.outputStream = outputStream;
		this.fileNames = fileNames;
	}
	
	public void run() {
        // now write to the output stream. for this test, use a zip
        // stream.
        // this is really to deal with the fact that we don't have a
        // good way to delimit the files.

        ZipEntryOutputStream zeos = null;
        BufferedInputStream dicomIn = null;
        ZipOutputStream zos = new ZipOutputStream(
                new BufferedOutputStream(outputStream));

        Set<String> keyset = fileNames.keySet();
        Iterator<String> iter = keyset.iterator();
        long start = System.currentTimeMillis();
        while (iter.hasNext()) {
            String sop =  iter.next();
             //System.out.println("sop: " + sop);
            String filePath = fileNames.get(sop);
             //System.out.println("filePath: " + filePath);
            try {
                zeos = new ZipEntryOutputStream(zos, sop,
                        ZipEntry.DEFLATED, -1);
                dicomIn = new BufferedInputStream(
                        new FileInputStream(filePath));
                
                byte[] data = new byte[BUFFER];
                int bytesRead = 0;
                while ((bytesRead = (dicomIn.read(data, 0, data.length))) > 0) {
                    zeos.write(data, 0, bytesRead);
                }
            } catch (IOException e1) {
                System.err.println("ERROR writing to zip entry "
                        + e1.getMessage());
                logger.error("Error writing to zip entry: ", e1);
            } finally {
                try {
                    zeos.flush();
                    zeos.close();
                    dicomIn.close();
                    out.println("caGrid transferred at "
                            + new Date().getTime());
                } catch (IOException e) {
                    System.err.println("ERROR closing zip entry "
                            + e.getMessage());
                    logger.error("Error closing to zip entry: ", e);
                    e.printStackTrace();
                }
            }
        }

        try {
            zos.flush();
            zos.close();
        } catch (IOException e) {
            logger.error("Error closing to zip output stream : ", e);
        }

        long end = System.currentTimeMillis();
        logger.info("Total time to read and transfer "
                + fileNames.size() + " files: " + (end - start)
                + MILLI_SECONDS);
    }
	
	private OutputStream outputStream;
	private Map<String, String> fileNames;
    private static final int BUFFER = 8192;
    private static String MILLI_SECONDS = " milli seconds.";
    private static Logger logger = Logger.getLogger(NCIACoreServiceImpl.class);

}