package gov.nih.nci.ncia.domain;

import java.util.Collection;
import java.io.Serializable;
	/**
	* A Clinical Trial Sponsor identifies the agency, group, or institution responsible for conducting the clinical trial and for assigning a Protocol Identifier.	**/
public class ClinicalTrialSponsor  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
		/**
	* Name of the institution that is responsible for coordinating the medical imaging data for a clinical trial.	**/
	private String coordinatingCenter;
	/**
	* Retreives the value of coordinatingCenter attribute
	* @return coordinatingCenter
	**/

	public String getCoordinatingCenter(){
		return coordinatingCenter;
	}

	/**
	* Sets the value of coordinatingCenter attribue
	**/

	public void setCoordinatingCenter(String coordinatingCenter){
		this.coordinatingCenter = coordinatingCenter;
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
	* Text to capture the name of a clinical trial sponsor.	**/
	private String sponsorName;
	/**
	* Retreives the value of sponsorName attribute
	* @return sponsorName
	**/

	public String getSponsorName(){
		return sponsorName;
	}

	/**
	* Sets the value of sponsorName attribue
	**/

	public void setSponsorName(String sponsorName){
		this.sponsorName = sponsorName;
	}
	
	/**
	* An associated gov.nih.nci.ncia.domain.ClinicalTrialProtocol object's collection 
	**/
			
	private Collection<ClinicalTrialProtocol> protocolCollection;
	/**
	* Retreives the value of protocolCollection attribue
	* @return protocolCollection
	**/

	public Collection<ClinicalTrialProtocol> getProtocolCollection(){
		return protocolCollection;
	}

	/**
	* Sets the value of protocolCollection attribue
	**/

	public void setProtocolCollection(Collection<ClinicalTrialProtocol> protocolCollection){
		this.protocolCollection = protocolCollection;
	}
		
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof ClinicalTrialSponsor) 
		{
			ClinicalTrialSponsor c =(ClinicalTrialSponsor)obj; 			 
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