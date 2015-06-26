/**
 * RetrieveDicomDataResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package gov.nih.nci.cagrid.ncia.stubs;

public class RetrieveDicomDataResponse  implements java.io.Serializable {
    private org.cagrid.transfer.context.stubs.types.TransferServiceContextReference transferServiceContextReference;

    public RetrieveDicomDataResponse() {
    }

    public RetrieveDicomDataResponse(
           org.cagrid.transfer.context.stubs.types.TransferServiceContextReference transferServiceContextReference) {
           this.transferServiceContextReference = transferServiceContextReference;
    }


    /**
     * Gets the transferServiceContextReference value for this RetrieveDicomDataResponse.
     * 
     * @return transferServiceContextReference
     */
    public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference getTransferServiceContextReference() {
        return transferServiceContextReference;
    }


    /**
     * Sets the transferServiceContextReference value for this RetrieveDicomDataResponse.
     * 
     * @param transferServiceContextReference
     */
    public void setTransferServiceContextReference(org.cagrid.transfer.context.stubs.types.TransferServiceContextReference transferServiceContextReference) {
        this.transferServiceContextReference = transferServiceContextReference;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetrieveDicomDataResponse)) return false;
        RetrieveDicomDataResponse other = (RetrieveDicomDataResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.transferServiceContextReference==null && other.getTransferServiceContextReference()==null) || 
             (this.transferServiceContextReference!=null &&
              this.transferServiceContextReference.equals(other.getTransferServiceContextReference())));
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
        if (getTransferServiceContextReference() != null) {
            _hashCode += getTransferServiceContextReference().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetrieveDicomDataResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ncia.cagrid.nci.nih.gov/NCIACoreService", ">RetrieveDicomDataResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transferServiceContextReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService/Context/types", "TransferServiceContextReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService/Context/types", ">TransferServiceContextReference"));
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
