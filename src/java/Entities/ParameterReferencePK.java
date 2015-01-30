/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fellippe.mendonca
 */
@Embeddable
public class ParameterReferencePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_parameter_reference")
    private int idParameterReference;
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

    public ParameterReferencePK() {
    }

    public ParameterReferencePK(int idParameterReference, int idRequestReference, int idParameter, int idDatabaseSelect) {
        this.idParameterReference = idParameterReference;
        this.idRequestReference = idRequestReference;
        this.idParameter = idParameter;
        this.idDatabaseSelect = idDatabaseSelect;
    }

    public int getIdParameterReference() {
        return idParameterReference;
    }

    public void setIdParameterReference(int idParameterReference) {
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
        hash += (int) idParameterReference;
        hash += (int) idRequestReference;
        hash += (int) idParameter;
        hash += (int) idDatabaseSelect;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParameterReferencePK)) {
            return false;
        }
        ParameterReferencePK other = (ParameterReferencePK) object;
        if (this.idParameterReference != other.idParameterReference) {
            return false;
        }
        if (this.idRequestReference != other.idRequestReference) {
            return false;
        }
        if (this.idParameter != other.idParameter) {
            return false;
        }
        if (this.idDatabaseSelect != other.idDatabaseSelect) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ParameterReferencePK[ idParameterReference=" + idParameterReference + ", idRequestReference=" + idRequestReference + ", idParameter=" + idParameter + ", idDatabaseSelect=" + idDatabaseSelect + " ]";
    }
    
}
