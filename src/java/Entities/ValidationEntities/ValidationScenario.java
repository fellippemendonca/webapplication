/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.ValidationEntities;

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
@Table(name = "validation_scenario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValidationScenario.findAll", query = "SELECT v FROM ValidationScenario v"),
    @NamedQuery(name = "ValidationScenario.findByIdValidationScenario", query = "SELECT v FROM ValidationScenario v WHERE v.idValidationScenario = :idValidationScenario"),
    @NamedQuery(name = "ValidationScenario.findByIdRequestReference", query = "SELECT v FROM ValidationScenario v WHERE v.idRequestReference = :idRequestReference")})
public class ValidationScenario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_validation_scenario")
    private Integer idValidationScenario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_reference")
    private int idRequestReference;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "request_object")
    private String requestObject;
    @Lob
    @Size(max = 65535)
    @Column(name = "validation_scenario_description")
    private String validationScenarioDescription;

    public ValidationScenario() {
    }

    public ValidationScenario(Integer idValidationScenario) {
        this.idValidationScenario = idValidationScenario;
    }

    public ValidationScenario(Integer idValidationScenario, int idRequestReference, String requestObject) {
        this.idValidationScenario = idValidationScenario;
        this.idRequestReference = idRequestReference;
        this.requestObject = requestObject;
    }

    public Integer getIdValidationScenario() {
        return idValidationScenario;
    }

    public void setIdValidationScenario(Integer idValidationScenario) {
        this.idValidationScenario = idValidationScenario;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public String getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(String requestObject) {
        this.requestObject = requestObject;
    }

    public String getValidationScenarioDescription() {
        return validationScenarioDescription;
    }

    public void setValidationScenarioDescription(String validationScenarioDescription) {
        this.validationScenarioDescription = validationScenarioDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValidationScenario != null ? idValidationScenario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValidationScenario)) {
            return false;
        }
        ValidationScenario other = (ValidationScenario) object;
        if ((this.idValidationScenario == null && other.idValidationScenario != null) || (this.idValidationScenario != null && !this.idValidationScenario.equals(other.idValidationScenario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ValidationEntities.ValidationScenario[ idValidationScenario=" + idValidationScenario + " ]";
    }
    
}
