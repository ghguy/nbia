/**
 * GetAvailableSearchTermsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class GetAvailableSearchTermsResponse  implements java.io.Serializable {
    private gov.nih.nci.ncia.search.AvailableSearchTerms availableSearchTerms;

    public GetAvailableSearchTermsResponse() {
    }

    public GetAvailableSearchTermsResponse(
           gov.nih.nci.ncia.search.AvailableSearchTerms availableSearchTerms) {
           this.availableSearchTerms = availableSearchTerms;
    }


    /**
     * Gets the availableSearchTerms value for this GetAvailableSearchTermsResponse.
     * 
     * @return availableSearchTerms
     */
    public gov.nih.nci.ncia.search.AvailableSearchTerms getAvailableSearchTerms() {
        return availableSearchTerms;
    }


    /**
     * Sets the availableSearchTerms value for this GetAvailableSearchTermsResponse.
     * 
     * @param availableSearchTerms
     */
    public void setAvailableSearchTerms(gov.nih.nci.ncia.search.AvailableSearchTerms availableSearchTerms) {
        this.availableSearchTerms = availableSearchTerms;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetAvailableSearchTermsResponse)) return false;
        GetAvailableSearchTermsResponse other = (GetAvailableSearchTermsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.availableSearchTerms==null && other.getAvailableSearchTerms()==null) || 
             (this.availableSearchTerms!=null &&
              this.availableSearchTerms.equals(other.getAvailableSearchTerms())));
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
        if (getAvailableSearchTerms() != null) {
            _hashCode += getAvailableSearchTerms().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetAvailableSearchTermsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">GetAvailableSearchTermsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availableSearchTerms");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "AvailableSearchTerms"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "AvailableSearchTerms"));
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
