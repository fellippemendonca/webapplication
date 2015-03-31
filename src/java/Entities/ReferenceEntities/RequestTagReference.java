/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.ReferenceEntities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fellippe.mendonca
 */
@Entity
@Table(name = "request_tag_reference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestTagReference.findAll", query = "SELECT r FROM RequestTagReference r"),
    @NamedQuery(name = "RequestTagReference.findByIdrequestTagReference", query = "SELECT r FROM RequestTagReference r WHERE r.idrequestTagReference = :idrequestTagReference"),
    @NamedQuery(name = "RequestTagReference.findByIdRequestReference", query = "SELECT r FROM RequestTagReference r WHERE r.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "RequestTagReference.findByIdRequestTag", query = "SELECT r FROM RequestTagReference r WHERE r.idRequestTag = :idRequestTag")})
public class RequestTagReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrequest_tag_reference")
    private Integer idrequestTagReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_reference")
    private int idRequestReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_tag")
    private int idRequestTag;

    public RequestTagReference() {
    }

    public RequestTagReference(Integer idrequestTagReference) {
        this.idrequestTagReference = idrequestTagReference;
    }

    public RequestTagReference(Integer idrequestTagReference, int idRequestReference, int idRequestTag) {
        this.idrequestTagReference = idrequestTagReference;
        this.idRequestReference = idRequestReference;
        this.idRequestTag = idRequestTag;
    }

    public Integer getIdrequestTagReference() {
        return idrequestTagReference;
    }

    public void setIdrequestTagReference(Integer idrequestTagReference) {
        this.idrequestTagReference = idrequestTagReference;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public int getIdRequestTag() {
        return idRequestTag;
    }

    public void setIdRequestTag(int idRequestTag) {
        this.idRequestTag = idRequestTag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrequestTagReference != null ? idrequestTagReference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestTagReference)) {
            return false;
        }
        RequestTagReference other = (RequestTagReference) object;
        if ((this.idrequestTagReference == null && other.idrequestTagReference != null) || (this.idrequestTagReference != null && !this.idrequestTagReference.equals(other.idrequestTagReference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ReferenceEntities.RequestTagReference[ idrequestTagReference=" + idrequestTagReference + " ]";
    }
    
}
