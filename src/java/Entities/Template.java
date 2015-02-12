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
@Table(name = "template")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Template.findAll", query = "SELECT t FROM Template t"),
    @NamedQuery(name = "Template.findByIdTemplate", query = "SELECT t FROM Template t WHERE t.idTemplate = :idTemplate"),
    @NamedQuery(name = "Template.findByTemplateValue", query = "SELECT t FROM Template t WHERE t.templateValue = :templateValue"),
    @NamedQuery(name = "Template.findByTemplateStatic", query = "SELECT t FROM Template t WHERE t.templateStatic = :templateStatic")})
public class Template implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_template")
    private Integer idTemplate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "template_value")
    private String templateValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "template_static")
    private int templateStatic;

    public Template() {
    }

    public Template(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public Template(Integer idTemplate, String templateValue, int templateStatic) {
        this.idTemplate = idTemplate;
        this.templateValue = templateValue;
        this.templateStatic = templateStatic;
    }

    public Integer getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    public String getTemplateValue() {
        return templateValue;
    }

    public void setTemplateValue(String templateValue) {
        this.templateValue = templateValue;
    }

    public int getTemplateStatic() {
        return templateStatic;
    }

    public void setTemplateStatic(int templateStatic) {
        this.templateStatic = templateStatic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTemplate != null ? idTemplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Template)) {
            return false;
        }
        Template other = (Template) object;
        if ((this.idTemplate == null && other.idTemplate != null) || (this.idTemplate != null && !this.idTemplate.equals(other.idTemplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Template[ idTemplate=" + idTemplate + " ]";
    }
    
}
