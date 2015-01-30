/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "ParameterReference.findByIdParameterReference", query = "SELECT p FROM ParameterReference p WHERE p.parameterReferencePK.idParameterReference = :idParameterReference"),
    @NamedQuery(name = "ParameterReference.findByIdRequestReference", query = "SELECT p FROM ParameterReference p WHERE p.parameterReferencePK.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "ParameterReference.findByIdParameter", query = "SELECT p FROM ParameterReference p WHERE p.parameterReferencePK.idParameter = :idParameter"),
    @NamedQuery(name = "ParameterReference.findByIdDatabaseSelect", query = "SELECT p FROM ParameterReference p WHERE p.parameterReferencePK.idDatabaseSelect = :idDatabaseSelect")})
public class ParameterReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParameterReferencePK parameterReferencePK;

    public ParameterReference() {
    }

    public ParameterReference(ParameterReferencePK parameterReferencePK) {
        this.parameterReferencePK = parameterReferencePK;
    }

    public ParameterReference(int idParameterReference, int idRequestReference, int idParameter, int idDatabaseSelect) {
        this.parameterReferencePK = new ParameterReferencePK(idParameterReference, idRequestReference, idParameter, idDatabaseSelect);
    }

    public ParameterReferencePK getParameterReferencePK() {
        return parameterReferencePK;
    }

    public void setParameterReferencePK(ParameterReferencePK parameterReferencePK) {
        this.parameterReferencePK = parameterReferencePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parameterReferencePK != null ? parameterReferencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParameterReference)) {
            return false;
        }
        ParameterReference other = (ParameterReference) object;
        if ((this.parameterReferencePK == null && other.parameterReferencePK != null) || (this.parameterReferencePK != null && !this.parameterReferencePK.equals(other.parameterReferencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ParameterReference[ parameterReferencePK=" + parameterReferencePK + " ]";
    }
    
}
