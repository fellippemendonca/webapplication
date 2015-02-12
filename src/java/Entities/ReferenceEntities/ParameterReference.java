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
@Table(name = "parameter_reference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParameterReference.findAll", query = "SELECT p FROM ParameterReference p"),
    @NamedQuery(name = "ParameterReference.findByIdParameterReference", query = "SELECT p FROM ParameterReference p WHERE p.idParameterReference = :idParameterReference"),
    @NamedQuery(name = "ParameterReference.findByIdRequestReference", query = "SELECT p FROM ParameterReference p WHERE p.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "ParameterReference.findByIdParameter", query = "SELECT p FROM ParameterReference p WHERE p.idParameter = :idParameter"),
    @NamedQuery(name = "ParameterReference.findByIdDatabaseSelect", query = "SELECT p FROM ParameterReference p WHERE p.idDatabaseSelect = :idDatabaseSelect")})
public class ParameterReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parameter_reference")
    private Integer idParameterReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_reference")
    private int idRequestReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_parameter")
    private int idParameter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_database_select")
    private int idDatabaseSelect;

    public ParameterReference() {
    }

    public ParameterReference(Integer idParameterReference) {
        this.idParameterReference = idParameterReference;
    }

    public ParameterReference(Integer idParameterReference, int idRequestReference, int idParameter, int idDatabaseSelect) {
        this.idParameterReference = idParameterReference;
        this.idRequestReference = idRequestReference;
        this.idParameter = idParameter;
        this.idDatabaseSelect = idDatabaseSelect;
    }

    public Integer getIdParameterReference() {
        return idParameterReference;
    }

    public void setIdParameterReference(Integer idParameterReference) {
        this.idParameterReference = idParameterReference;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public int getIdParameter() {
        return idParameter;
    }

    public void setIdParameter(int idParameter) {
        this.idParameter = idParameter;
    }

    public int getIdDatabaseSelect() {
        return idDatabaseSelect;
    }

    public void setIdDatabaseSelect(int idDatabaseSelect) {
        this.idDatabaseSelect = idDatabaseSelect;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParameterReference != null ? idParameterReference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParameterReference)) {
            return false;
        }
        ParameterReference other = (ParameterReference) object;
        if ((this.idParameterReference == null && other.idParameterReference != null) || (this.idParameterReference != null && !this.idParameterReference.equals(other.idParameterReference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ParameterReference[ idParameterReference=" + idParameterReference + " ]";
    }
    
}
