/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.ValueEntities;

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
@Table(name = "request_description")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestDescription.findAll", query = "SELECT r FROM RequestDescription r"),
    @NamedQuery(name = "RequestDescription.findByRequestDescription", query = "SELECT r FROM RequestDescription r WHERE r.requestDescription = :requestDescription"),
    @NamedQuery(name = "RequestDescription.findByIdRequestDescription", query = "SELECT r FROM RequestDescription r WHERE r.idRequestDescription = :idRequestDescription")})
public class RequestDescription implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_request_description")
    private Integer idRequestDescription;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "request_description")
    private String requestDescription;

    public RequestDescription() {
    }

    public RequestDescription(Integer idRequestDescription) {
        this.idRequestDescription = idRequestDescription;
    }

    public RequestDescription(Integer idRequestDescription, String requestDescription) {
        this.idRequestDescription = idRequestDescription;
        this.requestDescription = requestDescription;
    }

    public Integer getIdRequestDescription() {
        return idRequestDescription;
    }

    public void setIdRequestDescription(Integer idRequestDescription) {
        this.idRequestDescription = idRequestDescription;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestDescription != null ? idRequestDescription.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestDescription)) {
            return false;
        }
        RequestDescription other = (RequestDescription) object;
        if ((this.idRequestDescription == null && other.idRequestDescription != null) || (this.idRequestDescription != null && !this.idRequestDescription.equals(other.idRequestDescription))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ValueEntities.RequestDescription[ idRequestDescription=" + idRequestDescription + " ]";
    }
    
}
