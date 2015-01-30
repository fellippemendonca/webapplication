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
public class TemplateReferencePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_template_reference")
    private int idTemplateReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_reference")
    private int idRequestReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_template")
    private int idTemplate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_database_select")
    private int idDatabaseSelect;

    public TemplateReferencePK() {
    }

    public TemplateReferencePK(int idTemplateReference, int idRequestReference, int idTemplate, int idDatabaseSelect) {
        this.idTemplateReference = idTemplateReference;
        this.idRequestReference = idRequestReference;
        this.idTemplate = idTemplate;
        this.idDatabaseSelect = idDatabaseSelect;
    }

    public int getIdTemplateReference() {
        return idTemplateReference;
    }

    public void setIdTemplateReference(int idTemplateReference) {
        this.idTemplateReference = idTemplateReference;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public int getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(int idTemplate) {
        this.idTemplate = idTemplate;
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
        hash += (int) idTemplateReference;
        hash += (int) idRequestReference;
        hash += (int) idTemplate;
        hash += (int) idDatabaseSelect;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemplateReferencePK)) {
            return false;
        }
        TemplateReferencePK other = (TemplateReferencePK) object;
        if (this.idTemplateReference != other.idTemplateReference) {
            return false;
        }
        if (this.idRequestReference != other.idRequestReference) {
            return false;
        }
        if (this.idTemplate != other.idTemplate) {
            return false;
        }
        if (this.idDatabaseSelect != other.idDatabaseSelect) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TemplateReferencePK[ idTemplateReference=" + idTemplateReference + ", idRequestReference=" + idRequestReference + ", idTemplate=" + idTemplate + ", idDatabaseSelect=" + idDatabaseSelect + " ]";
    }
    
}
