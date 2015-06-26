/**
 * ViewDicomHeaderResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class ViewDicomHeaderResponse  implements java.io.Serializable {
    private gov.nih.nci.ncia.dto.DicomTagDTO[] dicomTagDTO;

    public ViewDicomHeaderResponse() {
    }

    public ViewDicomHeaderResponse(
           gov.nih.nci.ncia.dto.DicomTagDTO[] dicomTagDTO) {
           this.dicomTagDTO = dicomTagDTO;
    }


    /**
     * Gets the dicomTagDTO value for this ViewDicomHeaderResponse.
     * 
     * @return dicomTagDTO
     */
    public gov.nih.nci.ncia.dto.DicomTagDTO[] getDicomTagDTO() {
        return dicomTagDTO;
    }


    /**
     * Sets the dicomTagDTO value for this ViewDicomHeaderResponse.
     * 
     * @param dicomTagDTO
     */
    public void setDicomTagDTO(gov.nih.nci.ncia.dto.DicomTagDTO[] dicomTagDTO) {
        this.dicomTagDTO = dicomTagDTO;
    }

    public gov.nih.nci.ncia.dto.DicomTagDTO getDicomTagDTO(int i) {
        return this.dicomTagDTO[i];
    }

    public void setDicomTagDTO(int i, gov.nih.nci.ncia.dto.DicomTagDTO _value) {
        this.dicomTagDTO[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ViewDicomHeaderResponse)) return false;
        ViewDicomHeaderResponse other = (ViewDicomHeaderResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dicomTagDTO==null && other.getDicomTagDTO()==null) || 
             (this.dicomTagDTO!=null &&
              java.util.Arrays.equals(this.dicomTagDTO, other.getDicomTagDTO())));
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
        if (getDicomTagDTO() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDicomTagDTO());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDicomTagDTO(), i);
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
        new org.apache.axis.description.TypeDesc(ViewDicomHeaderResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">ViewDicomHeaderResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dicomTagDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.dicomtags", "DicomTagDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("gme://NBIA.caBIG/4.4/gov.nih.nci.nbia.dicomtags", "DicomTagDTO"));
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
