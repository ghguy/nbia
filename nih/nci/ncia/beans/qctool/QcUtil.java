package gov.nih.nci.ncia.beans.qctool;

import javax.faces.model.SelectItem;

public class QcUtil {
	   /**
     * Gets the options for number of displaying items for QC Result.
     *
     * @return array of predefined numbers for displaying search result
     */
    public SelectItem[] getDispItemNums() {
        SelectItem[] dispItemNums = new SelectItem[4];
        dispItemNums[0] = new SelectItem("10");
        dispItemNums[1] = new SelectItem("25");
        dispItemNums[2] = new SelectItem("50");
        dispItemNums[3] = new SelectItem("100");
        return dispItemNums;
    }
}
