/**
 * RetrieveImagesForSeriesRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveImagesForSeriesRequest  implements java.io.Serializable {
    private gov.nih.nci.cagrid.ncia.stubs.RetrieveImagesForSeriesRequestSeriesSearchResult seriesSearchResult;

    public RetrieveImagesForSeriesRequest() {
    }

    public RetrieveImagesForSeriesRequest(
           gov.nih.nci.cagrid.ncia.stubs.RetrieveImagesForSeriesRequestSeriesSearchResult seriesSearchResult) {
           this.seriesSearchResult = seriesSearchResult;
    }


    /**
     * Gets the seriesSearchResult value for this RetrieveImagesForSeriesRequest.
     * 
     * @return seriesSearchResult
     */
    public gov.nih.nci.cagrid.ncia.stubs.RetrieveImagesForSeriesRequestSeriesSearchResult getSeriesSearchResult() {
        return seriesSearchResult;
    }


    /**
     * Sets the seriesSearchResult value for this RetrieveImagesForSeriesRequest.
     * 
     * @param seriesSearchResult
     */
    public void setSeriesSearchResult(gov.nih.nci.cagrid.ncia.stubs.RetrieveImagesForSeriesRequestSeriesSearchResult seriesSearchResult) {
        this.seriesSearchResult = seriesSearchResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveImagesForSeriesRequest)) return false;
        RetrieveImagesForSeriesRequest other = (RetrieveImagesForSeriesRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.seriesSearchResult==null && other.getSeriesSearchResult()==null) || 
             (this.seriesSearchResult!=null &&
              this.seriesSearchResult.equals(other.getSeriesSearchResult())));
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
        if (getSeriesSearchResult() != null) {
            _hashCode += getSeriesSearchResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetrieveImagesForSeriesRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveImagesForSeriesRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seriesSearchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "seriesSearchResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">>RetrieveImagesForSeriesRequest>seriesSearchResult"));
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
