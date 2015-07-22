/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fellippe.mendonca
 */
@Entity
@Table(name = "dynamic_input_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DynamicInputData.findAll", query = "SELECT d FROM DynamicInputData d"),
    @NamedQuery(name = "DynamicInputData.findByIdDynamicInputData", query = "SELECT d FROM DynamicInputData d WHERE d.idDynamicInputData = :idDynamicInputData"),
    @NamedQuery(name = "DynamicInputData.findByIdRequestReference", query = "SELECT d FROM DynamicInputData d WHERE d.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "DynamicInputData.findByRequestType", query = "SELECT d FROM DynamicInputData d WHERE d.requestType = :requestType")})
public class DynamicInputData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dynamic_input_data")
    private Integer idDynamicInputData;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_reference")
    private int idRequestReference;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "request_type")
    private String requestType;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "json_request")
    private String jsonRequest;

    public DynamicInputData() {
    }

    public DynamicInputData(Integer idDynamicInputData) {
        this.idDynamicInputData = idDynamicInputData;
    }

    public DynamicInputData(Integer idDynamicInputData, int idRequestReference, String requestType, String jsonRequest) {
        this.idDynamicInputData = idDynamicInputData;
        this.idRequestReference = idRequestReference;
        this.requestType = requestType;
        this.jsonRequest = jsonRequest;
    }

    public Integer getIdDynamicInputData() {
        return idDynamicInputData;
    }

    public void setIdDynamicInputData(Integer idDynamicInputData) {
        this.idDynamicInputData = idDynamicInputData;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getJsonRequest() {
        return jsonRequest;
    }

    public void setJsonRequest(String jsonRequest) {
        this.jsonRequest = jsonRequest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDynamicInputData != null ? idDynamicInputData.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DynamicInputData)) {
            return false;
        }
        DynamicInputData other = (DynamicInputData) object;
        if ((this.idDynamicInputData == null && other.idDynamicInputData != null) || (this.idDynamicInputData != null && !this.idDynamicInputData.equals(other.idDynamicInputData))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.DynamicInputData[ idDynamicInputData=" + idDynamicInputData + " ]";
    }
    
}
