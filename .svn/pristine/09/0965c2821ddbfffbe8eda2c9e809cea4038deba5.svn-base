/**
 * RetrieveDicomDataByStudyUIDsRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveDicomDataByStudyUIDsRequest  implements java.io.Serializable {
    private java.lang.String[] studyInstanceUids;

    public RetrieveDicomDataByStudyUIDsRequest() {
    }

    public RetrieveDicomDataByStudyUIDsRequest(
           java.lang.String[] studyInstanceUids) {
           this.studyInstanceUids = studyInstanceUids;
    }


    /**
     * Gets the studyInstanceUids value for this RetrieveDicomDataByStudyUIDsRequest.
     * 
     * @return studyInstanceUids
     */
    public java.lang.String[] getStudyInstanceUids() {
        return studyInstanceUids;
    }


    /**
     * Sets the studyInstanceUids value for this RetrieveDicomDataByStudyUIDsRequest.
     * 
     * @param studyInstanceUids
     */
    public void setStudyInstanceUids(java.lang.String[] studyInstanceUids) {
        this.studyInstanceUids = studyInstanceUids;
    }

    public java.lang.String getStudyInstanceUids(int i) {
        return this.studyInstanceUids[i];
    }

    public void setStudyInstanceUids(int i, java.lang.String _value) {
        this.studyInstanceUids[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveDicomDataByStudyUIDsRequest)) return false;
        RetrieveDicomDataByStudyUIDsRequest other = (RetrieveDicomDataByStudyUIDsRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.studyInstanceUids==null && other.getStudyInstanceUids()==null) || 
             (this.studyInstanceUids!=null &&
              java.util.Arrays.equals(this.studyInstanceUids, other.getStudyInstanceUids())));
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
        if (getStudyInstanceUids() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStudyInstanceUids());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStudyInstanceUids(), i);
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
        new org.apache.axis.description.TypeDesc(RetrieveDicomDataByStudyUIDsRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveDicomDataByStudyUIDsRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("studyInstanceUids");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", "studyInstanceUids"));
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
