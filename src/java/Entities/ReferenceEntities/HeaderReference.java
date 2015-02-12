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
@Table(name = "header_reference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HeaderReference.findAll", query = "SELECT h FROM HeaderReference h"),
    @NamedQuery(name = "HeaderReference.findByIdHeaderReference", query = "SELECT h FROM HeaderReference h WHERE h.idHeaderReference = :idHeaderReference"),
    @NamedQuery(name = "HeaderReference.findByIdRequestReference", query = "SELECT h FROM HeaderReference h WHERE h.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "HeaderReference.findByIdHeader", query = "SELECT h FROM HeaderReference h WHERE h.idHeader = :idHeader")})
public class HeaderReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_header_reference")
    private Integer idHeaderReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_reference")
    private int idRequestReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_header")
    private int idHeader;

    public HeaderReference() {
    }

    public HeaderReference(Integer idHeaderReference) {
        this.idHeaderReference = idHeaderReference;
    }

    public HeaderReference(Integer idHeaderReference, int idRequestReference, int idHeader) {
        this.idHeaderReference = idHeaderReference;
        this.idRequestReference = idRequestReference;
        this.idHeader = idHeader;
    }

    public Integer getIdHeaderReference() {
        return idHeaderReference;
    }

    public void setIdHeaderReference(Integer idHeaderReference) {
        this.idHeaderReference = idHeaderReference;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public int getIdHeader() {
        return idHeader;
    }

    public void setIdHeader(int idHeader) {
        this.idHeader = idHeader;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHeaderReference != null ? idHeaderReference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HeaderReference)) {
            return false;
        }
        HeaderReference other = (HeaderReference) object;
        if ((this.idHeaderReference == null && other.idHeaderReference != null) || (this.idHeaderReference != null && !this.idHeaderReference.equals(other.idHeaderReference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.HeaderReference[ idHeaderReference=" + idHeaderReference + " ]";
    }
    
}
