package gov.nih.nci.ncia.internaldomain;


import java.io.Serializable;
	/**
	* 	**/
public class GridNode  implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;

	
		/**
	* 	**/
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
	* 	**/
	private String nodeDescription;
	/**
	* Retreives the value of nodeDescription attribute
	* @return nodeDescription
	**/

	public String getNodeDescription(){
		return nodeDescription;
	}

	/**
	* Sets the value of nodeDescription attribue
	**/

	public void setNodeDescription(String nodeDescription){
		this.nodeDescription = nodeDescription;
	}
	
		/**
	* 	**/
	private String nodeName;
	/**
	* Retreives the value of nodeName attribute
	* @return nodeName
	**/

	public String getNodeName(){
		return nodeName;
	}

	/**
	* Sets the value of nodeName attribue
	**/

	public void setNodeName(String nodeName){
		this.nodeName = nodeName;
	}
	
		/**
	* 	**/
	private String url;
	/**
	* Retreives the value of url attribute
	* @return url
	**/

	public String getUrl(){
		return url;
	}

	/**
	* Sets the value of url attribue
	**/

	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof GridNode) 
		{
			GridNode c =(GridNode)obj; 			 
			if(getId() != null && getId().equals(c.getId())) {
				return true;
			}
		}
		return false;
	}
		
	/**
	* Returns hash code for the primary key of the object
	**/
	public int hashCode()
	{
		if(getId() != null) {
			return getId().hashCode();
		}
		return 0;
	}
	
}