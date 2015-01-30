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
@Table(name = "scheme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scheme.findAll", query = "SELECT s FROM Scheme s"),
    @NamedQuery(name = "Scheme.findByIdScheme", query = "SELECT s FROM Scheme s WHERE s.idScheme = :idScheme"),
    @NamedQuery(name = "Scheme.findBySchemeValue", query = "SELECT s FROM Scheme s WHERE s.schemeValue = :schemeValue")})
public class Scheme implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_scheme")
    private Integer idScheme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "scheme_value")
    private String schemeValue;

    public Scheme() {
    }

    public Scheme(Integer idScheme) {
        this.idScheme = idScheme;
    }

    public Scheme(Integer idScheme, String schemeValue) {
        this.idScheme = idScheme;
        this.schemeValue = schemeValue;
    }

    public Integer getIdScheme() {
        return idScheme;
    }

    public void setIdScheme(Integer idScheme) {
        this.idScheme = idScheme;
    }

    public String getSchemeValue() {
        return schemeValue;
    }

    public void setSchemeValue(String schemeValue) {
        this.schemeValue = schemeValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idScheme != null ? idScheme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scheme)) {
            return false;
        }
        Scheme other = (Scheme) object;
        if ((this.idScheme == null && other.idScheme != null) || (this.idScheme != null && !this.idScheme.equals(other.idScheme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Scheme[ idScheme=" + idScheme + " ]";
    }
    
}
