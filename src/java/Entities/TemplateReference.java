/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "template_reference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TemplateReference.findAll", query = "SELECT t FROM TemplateReference t"),
    @NamedQuery(name = "TemplateReference.findByIdTemplateReference", query = "SELECT t FROM TemplateReference t WHERE t.templateReferencePK.idTemplateReference = :idTemplateReference"),
    @NamedQuery(name = "TemplateReference.findByIdRequestReference", query = "SELECT t FROM TemplateReference t WHERE t.templateReferencePK.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "TemplateReference.findByIdTemplate", query = "SELECT t FROM TemplateReference t WHERE t.templateReferencePK.idTemplate = :idTemplate"),
    @NamedQuery(name = "TemplateReference.findBySequenceNumber", query = "SELECT t FROM TemplateReference t WHERE t.sequenceNumber = :sequenceNumber"),
    @NamedQuery(name = "TemplateReference.findByIdDatabaseSelect", query = "SELECT t FROM TemplateReference t WHERE t.templateReferencePK.idDatabaseSelect = :idDatabaseSelect")})
public class TemplateReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TemplateReferencePK templateReferencePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sequence_number")
    private int sequenceNumber;

    public TemplateReference() {
    }

    public TemplateReference(TemplateReferencePK templateReferencePK) {
        this.templateReferencePK = templateReferencePK;
    }

    public TemplateReference(TemplateReferencePK templateReferencePK, int sequenceNumber) {
        this.templateReferencePK = templateReferencePK;
        this.sequenceNumber = sequenceNumber;
    }

    public TemplateReference(int idTemplateReference, int idRequestReference, int idTemplate, int idDatabaseSelect) {
        this.templateReferencePK = new TemplateReferencePK(idTemplateReference, idRequestReference, idTemplate, idDatabaseSelect);
    }

    public TemplateReferencePK getTemplateReferencePK() {
        return templateReferencePK;
    }

    public void setTemplateReferencePK(TemplateReferencePK templateReferencePK) {
        this.templateReferencePK = templateReferencePK;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (templateReferencePK != null ? templateReferencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemplateReference)) {
            return false;
        }
        TemplateReference other = (TemplateReference) object;
        if ((this.templateReferencePK == null && other.templateReferencePK != null) || (this.templateReferencePK != null && !this.templateReferencePK.equals(other.templateReferencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TemplateReference[ templateReferencePK=" + templateReferencePK + " ]";
    }
    
}
