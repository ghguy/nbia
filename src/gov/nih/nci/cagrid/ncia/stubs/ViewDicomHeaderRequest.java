/**
 * ViewDicomHeaderRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class ViewDicomHeaderRequest  implements java.io.Serializable {
    private gov.nih.nci.cagrid.ncia.stubs.ViewDicomHeaderRequestImageSearchResult imageSearchResult;

    public ViewDicomHeaderRequest() {
    }

    public ViewDicomHeaderRequest(
           gov.nih.nci.cagrid.ncia.stubs.ViewDicomHeaderRequestImageSearchResult imageSearchResult) {
           this.imageSearchResult = imageSearchResult;
    }


    /**
     * Gets the imageSearchResult value for this ViewDicomHeaderRequest.
     * 
     * @return imageSearchResult
     */
    public gov.nih.nci.cagrid.ncia.stubs.ViewDicomHeaderRequestImageSearchResult getImageSearchResult() {
        return imageSearchResult;
    }


    /**
     * Sets the imageSearchResult value for this ViewDicomHeaderRequest.
     * 
     * @param imageSearchResult
     */
    public void setImageSearchResult(gov.nih.nci.cagrid.ncia.stubs.ViewDicomHeaderRequestImageSearchResult imageSearchResult) {
        this.imageSearchResult = imageSearchResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ViewDicomHeaderRequest)) return false;
        ViewDicomHeaderRequest other = (ViewDicomHeaderRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.imageSearchResult==null && other.getImageSearchResult()==null) || 
             (this.imageSearchResult!=null &&
              this.imageSearchResult.equals(other.getImageSearchResult())));
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
        if (getImageSearchResult() != null) {
            _hashCode += getImageSearchResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ViewDicomHeaderRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">ViewDicomHeaderRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageSearchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "imageSearchResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">>ViewDicomHeaderRequest>imageSearchResult"));
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
