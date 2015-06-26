/**
 * SearchForPatientsRequestSearchCriteriaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class SearchForPatientsRequestSearchCriteriaDTO  implements java.io.Serializable {
    private gov.nih.nci.ncia.search.SearchCriteriaDTO[] searchCriteriaDTO;

    public SearchForPatientsRequestSearchCriteriaDTO() {
    }

    public SearchForPatientsRequestSearchCriteriaDTO(
           gov.nih.nci.ncia.search.SearchCriteriaDTO[] searchCriteriaDTO) {
           this.searchCriteriaDTO = searchCriteriaDTO;
    }


    /**
     * Gets the searchCriteriaDTO value for this SearchForPatientsRequestSearchCriteriaDTO.
     * 
     * @return searchCriteriaDTO
     */
    public gov.nih.nci.ncia.search.SearchCriteriaDTO[] getSearchCriteriaDTO() {
        return searchCriteriaDTO;
    }


    /**
     * Sets the searchCriteriaDTO value for this SearchForPatientsRequestSearchCriteriaDTO.
     * 
     * @param searchCriteriaDTO
     */
    public void setSearchCriteriaDTO(gov.nih.nci.ncia.search.SearchCriteriaDTO[] searchCriteriaDTO) {
        this.searchCriteriaDTO = searchCriteriaDTO;
    }

    public gov.nih.nci.ncia.search.SearchCriteriaDTO getSearchCriteriaDTO(int i) {
        return this.searchCriteriaDTO[i];
    }

    public void setSearchCriteriaDTO(int i, gov.nih.nci.ncia.search.SearchCriteriaDTO _value) {
        this.searchCriteriaDTO[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SearchForPatientsRequestSearchCriteriaDTO)) return false;
        SearchForPatientsRequestSearchCriteriaDTO other = (SearchForPatientsRequestSearchCriteriaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.searchCriteriaDTO==null && other.getSearchCriteriaDTO()==null) || 
             (this.searchCriteriaDTO!=null &&
              java.util.Arrays.equals(this.searchCriteriaDTO, other.getSearchCriteriaDTO())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSearchCriteriaDTO() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSearchCriteriaDTO());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSearchCriteriaDTO(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SearchForPatientsRequestSearchCriteriaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">>SearchForPatientsRequest>searchCriteriaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchCriteriaDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "SearchCriteriaDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "SearchCriteriaDTO"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
