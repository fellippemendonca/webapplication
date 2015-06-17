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
@Table(name = "request_name")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestName.findAll", query = "SELECT r FROM RequestName r"),
    @NamedQuery(name = "RequestName.findByRequestName", query = "SELECT r FROM RequestName r WHERE r.requestName = :requestName"),
    @NamedQuery(name = "RequestName.findByIdRequestName", query = "SELECT r FROM RequestName r WHERE r.idRequestName = :idRequestName")})
public class RequestName implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_request_name")
    private Integer idRequestName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "request_name")
    private String requestName;

    public RequestName() {
    }

    public RequestName(Integer idRequestName) {
        this.idRequestName = idRequestName;
    }

    public RequestName(Integer idRequestName, String requestName) {
        this.idRequestName = idRequestName;
        this.requestName = requestName;
    }

    public Integer getIdRequestName() {
        return idRequestName;
    }

    public void setIdRequestName(Integer idRequestName) {
        this.idRequestName = idRequestName;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestName != null ? idRequestName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestName)) {
            return false;
        }
        RequestName other = (RequestName) object;
        if ((this.idRequestName == null && other.idRequestName != null) || (this.idRequestName != null && !this.idRequestName.equals(other.idRequestName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.RequestName[ idRequestName=" + idRequestName + " ]";
    }
    
}
