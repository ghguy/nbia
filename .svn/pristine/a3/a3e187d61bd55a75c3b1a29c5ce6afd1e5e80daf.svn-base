/**
 * RetrieveImagesForSeriesResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveImagesForSeriesResponse  implements java.io.Serializable {
    private gov.nih.nci.ncia.search.ImageSearchResult[] imageSearchResult;

    public RetrieveImagesForSeriesResponse() {
    }

    public RetrieveImagesForSeriesResponse(
           gov.nih.nci.ncia.search.ImageSearchResult[] imageSearchResult) {
           this.imageSearchResult = imageSearchResult;
    }


    /**
     * Gets the imageSearchResult value for this RetrieveImagesForSeriesResponse.
     * 
     * @return imageSearchResult
     */
    public gov.nih.nci.ncia.search.ImageSearchResult[] getImageSearchResult() {
        return imageSearchResult;
    }


    /**
     * Sets the imageSearchResult value for this RetrieveImagesForSeriesResponse.
     * 
     * @param imageSearchResult
     */
    public void setImageSearchResult(gov.nih.nci.ncia.search.ImageSearchResult[] imageSearchResult) {
        this.imageSearchResult = imageSearchResult;
    }

    public gov.nih.nci.ncia.search.ImageSearchResult getImageSearchResult(int i) {
        return this.imageSearchResult[i];
    }

    public void setImageSearchResult(int i, gov.nih.nci.ncia.search.ImageSearchResult _value) {
        this.imageSearchResult[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveImagesForSeriesResponse)) return false;
        RetrieveImagesForSeriesResponse other = (RetrieveImagesForSeriesResponse) obj;
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
              java.util.Arrays.equals(this.imageSearchResult, other.getImageSearchResult())));
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
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImageSearchResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImageSearchResult(), i);
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
        new org.apache.axis.description.TypeDesc(RetrieveImagesForSeriesResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveImagesForSeriesResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageSearchResult");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "ImageSearchResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.remotesearch", "ImageSearchResult"));
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
