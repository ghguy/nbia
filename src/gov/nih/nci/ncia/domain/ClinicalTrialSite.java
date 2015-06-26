package gov.nih.nci.ncia.domain;

import java.util.Collection;
import java.io.Serializable;
	/**
	* A Clinical Trial Site identifies the location or institution at which the Subject is treated or evaluated and which is responsible for submitting clinical trial data. Images and/or clinical trial data may be collected for a given Subject at alternate #NOTES#A Clinical Trial Site identifies the location or institution at which the Subject is treated or evaluated and
which is responsible for submitting clinical trial data. Images and/or clinical trial data may be collected for
a given Subject at alternate institutions, e.g. follow-up scans at a satellite imaging center, but the Clinical Trial Site represents the primary location for Patient management and data submission in the context of a
clinical trial.	**/
public class ClinicalTrialSite  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
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
	* One or more characters used as the identifier of the site responsible for submitting clinical trial data.	**/
	private String siteId;
	/**
	* Retreives the value of siteId attribute
	* @return siteId
	**/

	public String getSiteId(){
		return siteId;
	}

	/**
	* Sets the value of siteId attribue
	**/

	public void setSiteId(String siteId){
		this.siteId = siteId;
	}
	
		/**
	* Text name to capture the name of the site responsible for submitting clinical trial data.	**/
	private String siteName;
	/**
	* Retreives the value of siteName attribute
	* @return siteName
	**/

	public String getSiteName(){
		return siteName;
	}

	/**
	* Sets the value of siteName attribue
	**/

	public void setSiteName(String siteName){
		this.siteName = siteName;
	}
	
	/**
	* An associated gov.nih.nci.ncia.domain.ClinicalTrialSubject object's collection 
	**/
			
	private Collection<ClinicalTrialSubject> subjectCollection;
	/**
	* Retreives the value of subjectCollection attribue
	* @return subjectCollection
	**/

	public Collection<ClinicalTrialSubject> getSubjectCollection(){
		return subjectCollection;
	}

	/**
	* Sets the value of subjectCollection attribue
	**/

	public void setSubjectCollection(Collection<ClinicalTrialSubject> subjectCollection){
		this.subjectCollection = subjectCollection;
	}
		
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof ClinicalTrialSite) 
		{
			ClinicalTrialSite c =(ClinicalTrialSite)obj; 			 
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