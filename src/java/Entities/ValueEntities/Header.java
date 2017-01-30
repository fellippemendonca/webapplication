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
@Table(name = "header")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Header.findAll", query = "SELECT h FROM Header h"),
    @NamedQuery(name = "Header.findByIdHeader", query = "SELECT h FROM Header h WHERE h.idHeader = :idHeader"),
    @NamedQuery(name = "Header.findByHeaderName", query = "SELECT h FROM Header h WHERE h.headerName = :headerName"),
    @NamedQuery(name = "Header.findByHeaderValue", query = "SELECT h FROM Header h WHERE h.headerValue = :headerValue"),
    @NamedQuery(name = "Header.findByHeaderNameAndValue", query = "SELECT h FROM Header h WHERE h.headerName = :headerName and h.headerValue = :headerValue")})
public class Header implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_header")
    private Integer idHeader;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "header_name")
    private String headerName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "header_value")
    private String headerValue;

    public Header() {
    }

    public Header(Integer idHeader) {
        this.idHeader = idHeader;
    }

    public Header(Integer idHeader, String headerName, String headerValue) {
        this.idHeader = idHeader;
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    public Integer getIdHeader() {
        return idHeader;
    }

    public void setIdHeader(Integer idHeader) {
        this.idHeader = idHeader;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHeader != null ? idHeader.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Header)) {
            return false;
        }
        Header other = (Header) object;
        if ((this.idHeader == null && other.idHeader != null) || (this.idHeader != null && !this.idHeader.equals(other.idHeader))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Header[ idHeader=" + idHeader + " ]";
    }
    
}
