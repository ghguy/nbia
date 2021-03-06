/**
 * RetrieveDicomDataRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveDicomDataRequest  implements java.io.Serializable {
    private gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataRequestCQLQuery cQLQuery;

    public RetrieveDicomDataRequest() {
    }

    public RetrieveDicomDataRequest(
           gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataRequestCQLQuery cQLQuery) {
           this.cQLQuery = cQLQuery;
    }


    /**
     * Gets the cQLQuery value for this RetrieveDicomDataRequest.
     * 
     * @return cQLQuery
     */
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataRequestCQLQuery getCQLQuery() {
        return cQLQuery;
    }


    /**
     * Sets the cQLQuery value for this RetrieveDicomDataRequest.
     * 
     * @param cQLQuery
     */
    public void setCQLQuery(gov.nih.nci.cagrid.ncia.stubs.RetrieveDicomDataRequestCQLQuery cQLQuery) {
        this.cQLQuery = cQLQuery;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveDicomDataRequest)) return false;
        RetrieveDicomDataRequest other = (RetrieveDicomDataRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cQLQuery==null && other.getCQLQuery()==null) || 
             (this.cQLQuery!=null &&
              this.cQLQuery.equals(other.getCQLQuery())));
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
        if (getCQLQuery() != null) {
            _hashCode += getCQLQuery().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetrieveDicomDataRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveDicomDataRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CQLQuery");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "cQLQuery"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">>RetrieveDicomDataRequest>cQLQuery"));
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
