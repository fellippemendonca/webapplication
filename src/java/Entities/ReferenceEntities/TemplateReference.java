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
@Table(name = "template_reference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TemplateReference.findAll", query = "SELECT t FROM TemplateReference t"),
    @NamedQuery(name = "TemplateReference.findByIdTemplateReference", query = "SELECT t FROM TemplateReference t WHERE t.idTemplateReference = :idTemplateReference"),
    @NamedQuery(name = "TemplateReference.findByIdRequestReference", query = "SELECT t FROM TemplateReference t WHERE t.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "TemplateReference.findByIdTemplate", query = "SELECT t FROM TemplateReference t WHERE t.idTemplate = :idTemplate"),
    @NamedQuery(name = "TemplateReference.findBySequenceNumber", query = "SELECT t FROM TemplateReference t WHERE t.sequenceNumber = :sequenceNumber"),
    @NamedQuery(name = "TemplateReference.findByIdDatabaseSelect", query = "SELECT t FROM TemplateReference t WHERE t.idDatabaseSelect = :idDatabaseSelect")})
public class TemplateReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_template_reference")
    private Integer idTemplateReference;
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
    @Column(name = "sequence_number")
    private int sequenceNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_database_select")
    private int idDatabaseSelect;

    public TemplateReference() {
    }

    public TemplateReference(Integer idTemplateReference) {
        this.idTemplateReference = idTemplateReference;
    }

    public TemplateReference(Integer idTemplateReference, int idRequestReference, int idTemplate, int sequenceNumber, int idDatabaseSelect) {
        this.idTemplateReference = idTemplateReference;
        this.idRequestReference = idRequestReference;
        this.idTemplate = idTemplate;
        this.sequenceNumber = sequenceNumber;
        this.idDatabaseSelect = idDatabaseSelect;
    }

    public Integer getIdTemplateReference() {
        return idTemplateReference;
    }

    public void setIdTemplateReference(Integer idTemplateReference) {
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

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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
        hash += (idTemplateReference != null ? idTemplateReference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TemplateReference)) {
            return false;
        }
        TemplateReference other = (TemplateReference) object;
        if ((this.idTemplateReference == null && other.idTemplateReference != null) || (this.idTemplateReference != null && !this.idTemplateReference.equals(other.idTemplateReference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TemplateReference[ idTemplateReference=" + idTemplateReference + " ]";
    }
    
}
