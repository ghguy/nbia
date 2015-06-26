/**
 * GetRepresentativeImageBySeriesRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class GetRepresentativeImageBySeriesRequest  implements java.io.Serializable {
    private java.lang.String seriesInstanceUID;

    public GetRepresentativeImageBySeriesRequest() {
    }

    public GetRepresentativeImageBySeriesRequest(
           java.lang.String seriesInstanceUID) {
           this.seriesInstanceUID = seriesInstanceUID;
    }


    /**
     * Gets the seriesInstanceUID value for this GetRepresentativeImageBySeriesRequest.
     * 
     * @return seriesInstanceUID
     */
    public java.lang.String getSeriesInstanceUID() {
        return seriesInstanceUID;
    }


    /**
     * Sets the seriesInstanceUID value for this GetRepresentativeImageBySeriesRequest.
     * 
     * @param seriesInstanceUID
     */
    public void setSeriesInstanceUID(java.lang.String seriesInstanceUID) {
        this.seriesInstanceUID = seriesInstanceUID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetRepresentativeImageBySeriesRequest)) return false;
        GetRepresentativeImageBySeriesRequest other = (GetRepresentativeImageBySeriesRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.seriesInstanceUID==null && other.getSeriesInstanceUID()==null) || 
             (this.seriesInstanceUID!=null &&
              this.seriesInstanceUID.equals(other.getSeriesInstanceUID())));
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
        if (getSeriesInstanceUID() != null) {
            _hashCode += getSeriesInstanceUID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetRepresentativeImageBySeriesRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">GetRepresentativeImageBySeriesRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seriesInstanceUID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "seriesInstanceUID"));
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
