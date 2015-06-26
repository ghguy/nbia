/**
 * RetrieveDicomDataBySeriesUIDsRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveDicomDataBySeriesUIDsRequest  implements java.io.Serializable {
    private java.lang.String[] seriesInstanceUids;

    public RetrieveDicomDataBySeriesUIDsRequest() {
    }

    public RetrieveDicomDataBySeriesUIDsRequest(
           java.lang.String[] seriesInstanceUids) {
           this.seriesInstanceUids = seriesInstanceUids;
    }


    /**
     * Gets the seriesInstanceUids value for this RetrieveDicomDataBySeriesUIDsRequest.
     * 
     * @return seriesInstanceUids
     */
    public java.lang.String[] getSeriesInstanceUids() {
        return seriesInstanceUids;
    }


    /**
     * Sets the seriesInstanceUids value for this RetrieveDicomDataBySeriesUIDsRequest.
     * 
     * @param seriesInstanceUids
     */
    public void setSeriesInstanceUids(java.lang.String[] seriesInstanceUids) {
        this.seriesInstanceUids = seriesInstanceUids;
    }

    public java.lang.String getSeriesInstanceUids(int i) {
        return this.seriesInstanceUids[i];
    }

    public void setSeriesInstanceUids(int i, java.lang.String _value) {
        this.seriesInstanceUids[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveDicomDataBySeriesUIDsRequest)) return false;
        RetrieveDicomDataBySeriesUIDsRequest other = (RetrieveDicomDataBySeriesUIDsRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.seriesInstanceUids==null && other.getSeriesInstanceUids()==null) || 
             (this.seriesInstanceUids!=null &&
              java.util.Arrays.equals(this.seriesInstanceUids, other.getSeriesInstanceUids())));
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
        if (getSeriesInstanceUids() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSeriesInstanceUids());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSeriesInstanceUids(), i);
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
        new org.apache.axis.description.TypeDesc(RetrieveDicomDataBySeriesUIDsRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveDicomDataBySeriesUIDsRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seriesInstanceUids");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "seriesInstanceUids"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
