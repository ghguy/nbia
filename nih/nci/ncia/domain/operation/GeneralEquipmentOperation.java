/**
 * 
 */
package gov.nih.nci.ncia.domain.operation;

import gov.nih.nci.ncia.db.IDataAccess;
import gov.nih.nci.ncia.internaldomain.GeneralEquipment;
import gov.nih.nci.ncia.util.DicomConstants;

import java.util.List;
import java.util.Map;

/**
 * @author lethai
 *
 */
public class GeneralEquipmentOperation extends DomainOperation {
	
	public GeneralEquipmentOperation(IDataAccess ida) {
		this.ida = ida;
	}
	
	private boolean checkManufacturer(Map nums)
	{
		boolean hasIt = false;
		
		if ((String)nums.get(DicomConstants.MANUFACTURER) != null)
		{
			hasIt = true;
		}
		
		return hasIt;
	}
	
	public Object validate(Map numbers) throws Exception {
        String hql = "from GeneralEquipment as equip where ";
        GeneralEquipment equip = new GeneralEquipment();
        boolean hasManufacturer = checkManufacturer(numbers);
        try {
        	if (hasManufacturer)
        	{
        		hql = createHQL(numbers, hql, equip);
        	}
        	else
        	{
        		 hql += " equip.manufacturer is null ";
        		 hql += " and equip.institutionName is null ";
        		 hql += " and equip.manufacturerModelName is null ";
        		 hql += " and equip.softwareVersions is null ";
        	}
        	
	        List rs = (List)ida.search(hql);
	        if(rs != null && rs.size() > 0) {
	        	if (rs.size() == 1) {
	        		equip = (GeneralEquipment) rs.get(0);
	        	}else if (rs.size() > 1) {
	        		throw new Exception("General_Equipment table has duplicate records, please contact Data Team to fix data, then upload data again");
	        	}        	
	        }/*else {
	        	System.out.println(" zero record returned for GeneralEquipment");
	        }*/
        }catch(Exception e) {
        	//log.error("Exception in GeneralEquipmentOperation " + e);
        	throw new Exception("Exception in GeneralEquipmentOperation " + e);
        }
        
        return equip;		
	}
	
	private String createHQL(Map numbers, String hql, GeneralEquipment equip)
	{
		String temp = "";
    	if ((temp = (String) numbers.get(DicomConstants.MANUFACTURER)) != null) {
            equip.setManufacturer(temp.trim());
            hql += (" equip.manufacturer = '" + temp.trim() + "' ");
        } else {
            hql += " equip.manufacturer is null ";
        }

        if ((temp = (String) numbers.get(DicomConstants.INSTITUTION_NAME)) != null) {
            equip.setInstitutionName(temp.trim());
            hql += (" and equip.institutionName = '" + temp.trim() + "' ");
        } else {
            hql += " and equip.institutionName is null ";
        }

        if ((temp = (String) numbers.get(DicomConstants.MANUFACTURER_MODEL_NAME)) != null) {
            equip.setManufacturerModelName(temp.trim());
            hql += (" and equip.manufacturerModelName = '" + temp.trim() +
            "' ");
        } else {
            hql += " and equip.manufacturerModelName is null ";
        }

        if ((temp = (String) numbers.get(DicomConstants.SOFTWARE_VERSIONS)) != null) {
            equip.setSoftwareVersions(temp.trim());
            hql += (" and equip.softwareVersions = '" + temp.trim() + "' ");
        } else {
            hql += " and equip.softwareVersions is null ";
        }
        
        return hql;
	}
}
