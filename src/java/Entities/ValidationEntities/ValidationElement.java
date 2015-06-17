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
@Table(name = "validation_element")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValidationElement.findAll", query = "SELECT v FROM ValidationElement v"),
    @NamedQuery(name = "ValidationElement.findByIdResponseValidationElement", query = "SELECT v FROM ValidationElement v WHERE v.idResponseValidationElement = :idResponseValidationElement"),
    @NamedQuery(name = "ValidationElement.findByIdValidationScenario", query = "SELECT v FROM ValidationElement v WHERE v.idValidationScenario = :idValidationScenario"),
    @NamedQuery(name = "ValidationElement.findByIdValidationOperation", query = "SELECT v FROM ValidationElement v WHERE v.idValidationOperation = :idValidationOperation")})
public class ValidationElement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_response_validation_element")
    private Integer idResponseValidationElement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_validation_scenario")
    private int idValidationScenario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_validation_operation")
    private int idValidationOperation;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "expected_object")
    private String expectedObject;

    public ValidationElement() {
    }

    public ValidationElement(Integer idResponseValidationElement) {
        this.idResponseValidationElement = idResponseValidationElement;
    }

    public ValidationElement(Integer idResponseValidationElement, int idValidationScenario, int idValidationOperation, String expectedObject) {
        this.idResponseValidationElement = idResponseValidationElement;
        this.idValidationScenario = idValidationScenario;
        this.idValidationOperation = idValidationOperation;
        this.expectedObject = expectedObject;
    }

    public Integer getIdResponseValidationElement() {
        return idResponseValidationElement;
    }

    public void setIdResponseValidationElement(Integer idResponseValidationElement) {
        this.idResponseValidationElement = idResponseValidationElement;
    }

    public int getIdValidationScenario() {
        return idValidationScenario;
    }

    public void setIdValidationScenario(int idValidationScenario) {
        this.idValidationScenario = idValidationScenario;
    }

    public int getIdValidationOperation() {
        return idValidationOperation;
    }

    public void setIdValidationOperation(int idValidationOperation) {
        this.idValidationOperation = idValidationOperation;
    }

    public String getExpectedObject() {
        return expectedObject;
    }

    public void setExpectedObject(String expectedObject) {
        this.expectedObject = expectedObject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResponseValidationElement != null ? idResponseValidationElement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValidationElement)) {
            return false;
        }
        ValidationElement other = (ValidationElement) object;
        if ((this.idResponseValidationElement == null && other.idResponseValidationElement != null) || (this.idResponseValidationElement != null && !this.idResponseValidationElement.equals(other.idResponseValidationElement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ValidationEntities.ValidationElement[ idResponseValidationElement=" + idResponseValidationElement + " ]";
    }
    
}
