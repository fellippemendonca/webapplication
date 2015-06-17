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
@Table(name = "validation_operation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValidationOperation.findAll", query = "SELECT v FROM ValidationOperation v"),
    @NamedQuery(name = "ValidationOperation.findByIdValidationOperation", query = "SELECT v FROM ValidationOperation v WHERE v.idValidationOperation = :idValidationOperation"),
    @NamedQuery(name = "ValidationOperation.findByName", query = "SELECT v FROM ValidationOperation v WHERE v.name = :name")})
public class ValidationOperation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_validation_operation")
    private Integer idValidationOperation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;

    public ValidationOperation() {
    }

    public ValidationOperation(Integer idValidationOperation) {
        this.idValidationOperation = idValidationOperation;
    }

    public ValidationOperation(Integer idValidationOperation, String name) {
        this.idValidationOperation = idValidationOperation;
        this.name = name;
    }

    public Integer getIdValidationOperation() {
        return idValidationOperation;
    }

    public void setIdValidationOperation(Integer idValidationOperation) {
        this.idValidationOperation = idValidationOperation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValidationOperation != null ? idValidationOperation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValidationOperation)) {
            return false;
        }
        ValidationOperation other = (ValidationOperation) object;
        if ((this.idValidationOperation == null && other.idValidationOperation != null) || (this.idValidationOperation != null && !this.idValidationOperation.equals(other.idValidationOperation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ValidationEntities.ValidationOperation[ idValidationOperation=" + idValidationOperation + " ]";
    }
}
