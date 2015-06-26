package gov.nih.nci.ncia.dynamicsearch;

import java.util.Map;

//this object doesnt really seem like a data field
//i'd expect a data field to map up to each "source item" in the
//datasourceitem.xml
//
//currently this object is being used as a table for data field/source item
//type.... should be renamed to DataFieldTypeMap.
public class DataFieldTypeMap {
	private static Map<String, String> typeTable = DataFieldParser.getItemType();
	//Since Visibility is not showing up on the GUI, just manual add this in hashMap.
	static {
		typeTable.put("visibility", "java.lang.String");
	}
	
	public static String getDataFieldType(String field)
	{
		String type = typeTable.get(field.trim());
		return type;
	}
	
	private DataFieldTypeMap() {
		
	}
}
