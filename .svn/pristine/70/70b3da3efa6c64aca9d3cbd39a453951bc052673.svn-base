package gov.nih.nci.ncia.domain;

import java.util.Collection;
import java.io.Serializable;
	/**
	* The imaging equipment used to obtain an in vivo image	**/
public class Equipment  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
		/**
	* Manufacturer’s serial number of the equipment that produced the composite instances.	**/
	private String deviceSerialNumber;
	/**
	* Retreives the value of deviceSerialNumber attribute
	* @return deviceSerialNumber
	**/

	public String getDeviceSerialNumber(){
		return deviceSerialNumber;
	}

	/**
	* Sets the value of deviceSerialNumber attribue
	**/

	public void setDeviceSerialNumber(String deviceSerialNumber){
		this.deviceSerialNumber = deviceSerialNumber;
	}
	
		/**
	* One or more characters used to identify, name, or characterize the nature, properties, or contents of a thing.	**/
	private Integer id;
	/**
	* Retreives the value of id attribute
	* @return id
	**/

	public Integer getId(){
		return id;
	}

	/**
	* Sets the value of id attribue
	**/

	public void setId(Integer id){
		this.id = id;
	}
	
		/**
	* Address of the institution where the equipment that contributed to the composite instance is located.	**/
	private String institutionAddress;
	/**
	* Retreives the value of institutionAddress attribute
	* @return institutionAddress
	**/

	public String getInstitutionAddress(){
		return institutionAddress;
	}

	/**
	* Sets the value of institutionAddress attribue
	**/

	public void setInstitutionAddress(String institutionAddress){
		this.institutionAddress = institutionAddress;
	}
	
		/**
	* Text to represent the name of the institution where the equipment that produced the composite instances is located.	**/
	private String institutionName;
	/**
	* Retreives the value of institutionName attribute
	* @return institutionName
	**/

	public String getInstitutionName(){
		return institutionName;
	}

	/**
	* Sets the value of institutionName attribue
	**/

	public void setInstitutionName(String institutionName){
		this.institutionName = institutionName;
	}
	
		/**
	* Text to capture the name of the manufacturer of medical imaging equipment.	**/
	private String manufacturer;
	/**
	* Retreives the value of manufacturer attribute
	* @return manufacturer
	**/

	public String getManufacturer(){
		return manufacturer;
	}

	/**
	* Sets the value of manufacturer attribue
	**/

	public void setManufacturer(String manufacturer){
		this.manufacturer = manufacturer;
	}
	
		/**
	* Text to capture the manufacturer's model names of the (imaging) equipment that produced the composite instances.	**/
	private String manufacturerModelName;
	/**
	* Retreives the value of manufacturerModelName attribute
	* @return manufacturerModelName
	**/

	public String getManufacturerModelName(){
		return manufacturerModelName;
	}

	/**
	* Sets the value of manufacturerModelName attribue
	**/

	public void setManufacturerModelName(String manufacturerModelName){
		this.manufacturerModelName = manufacturerModelName;
	}
	
		/**
	* Numeric value to designate manufacturer's software version of the equipment that produced the (imaging) composite instances.	**/
	private String softwareVersions;
	/**
	* Retreives the value of softwareVersions attribute
	* @return softwareVersions
	**/

	public String getSoftwareVersions(){
		return softwareVersions;
	}

	/**
	* Sets the value of softwareVersions attribue
	**/

	public void setSoftwareVersions(String softwareVersions){
		this.softwareVersions = softwareVersions;
	}
	
		/**
	* Name of the device observer for this document instance.	**/
	private String stationName;
	/**
	* Retreives the value of stationName attribute
	* @return stationName
	**/

	public String getStationName(){
		return stationName;
	}

	/**
	* Sets the value of stationName attribue
	**/

	public void setStationName(String stationName){
		this.stationName = stationName;
	}
	
	/**
	* An associated gov.nih.nci.ncia.domain.Series object's collection 
	**/
			
	private Collection<Series> seriesCollection;
	/**
	* Retreives the value of seriesCollection attribue
	* @return seriesCollection
	**/

	public Collection<Series> getSeriesCollection(){
		return seriesCollection;
	}

	/**
	* Sets the value of seriesCollection attribue
	**/

	public void setSeriesCollection(Collection<Series> seriesCollection){
		this.seriesCollection = seriesCollection;
	}
		
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof Equipment) 
		{
			Equipment c =(Equipment)obj; 			 
			if(getId() != null && getId().equals(c.getId()))
				return true;
		}
		return false;
	}
		
	/**
	* Returns hash code for the primary key of the object
	**/
	public int hashCode()
	{
		if(getId() != null)
			return getId().hashCode();
		return 0;
	}
	
}