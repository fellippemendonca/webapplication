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
import javax.persistence.Lob;
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
@Table(name = "parameter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parameter.findAll", query = "SELECT p FROM Parameter p"),
    @NamedQuery(name = "Parameter.findByIdParameter", query = "SELECT p FROM Parameter p WHERE p.idParameter = :idParameter"),
    @NamedQuery(name = "Parameter.findByParameterName", query = "SELECT p FROM Parameter p WHERE p.parameterName = :parameterName"),
    @NamedQuery(name = "Parameter.findByParameterValue", query = "SELECT p FROM Parameter p WHERE p.parameterValue = :parameterValue"),
    @NamedQuery(name = "Parameter.findByParameterStatic", query = "SELECT p FROM Parameter p WHERE p.parameterStatic = :parameterStatic"),
    @NamedQuery(name = "Parameter.findByParameterNameAndValue", query = "SELECT p FROM Parameter p WHERE p.parameterName = :parameterName and p.parameterValue = :parameterValue")})
public class Parameter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parameter")
    private Integer idParameter;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "parameter_name")
    private String parameterName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "parameter_value")
    private String parameterValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "parameter_static")
    private Integer parameterStatic;

    public Parameter() {
    }

    public Parameter(Integer idParameter) {
        this.idParameter = idParameter;
    }

    public Parameter(Integer idParameter, String parameterName, String parameterValue, Integer parameterStatic) {
        this.idParameter = idParameter;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
        this.parameterStatic = parameterStatic;
    }

    public Integer getIdParameter() {
        return idParameter;
    }

    public void setIdParameter(Integer idParameter) {
        this.idParameter = idParameter;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Integer getParameterStatic() {
        return parameterStatic;
    }

    public void setParameterStatic(Integer parameterStatic) {
        this.parameterStatic = parameterStatic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParameter != null ? idParameter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parameter)) {
            return false;
        }
        Parameter other = (Parameter) object;
        if ((this.idParameter == null && other.idParameter != null) || (this.idParameter != null && !this.idParameter.equals(other.idParameter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Parameter[ idParameter=" + idParameter + " ]";
    }
    
}
