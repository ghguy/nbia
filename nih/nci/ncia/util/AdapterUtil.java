/**
 *
 */
package gov.nih.nci.ncia.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author lethai
 *
 */
public class AdapterUtil {
	private static Logger log = Logger.getLogger(AdapterUtil.class);
	public static Date stringToDate(String numbers) throws Exception {
        Date retval = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            if (numbers != null) {
                retval = sdf.parse(numbers);
            }
        } catch (ParseException pe) {
            log.error("ParseException in StringToDate: " + numbers);
        }

        return retval;
    }



	public static String convertToAgeGroup(String age) throws Exception {
        if (!age.endsWith("Y")) {
            return "";
        }

        int ageNumber = Integer.parseInt(age.substring(0, age.length() - 1));

        if (ageNumber < 5) {
            return "000Y";
        } 
        else {
	        for(int i=1;i<10;i++) {
	        	if(ageNumber < i*10+5) {
	        		return "0"+(i*10)+"Y";
	        	}
	        }
        }
        
        return "100Y";
    }

    private AdapterUtil() {
	}
}
