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
@Table(name = "environment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Environment.findAll", query = "SELECT e FROM Environment e"),
    @NamedQuery(name = "Environment.findByIdEnvironment", query = "SELECT e FROM Environment e WHERE e.idEnvironment = :idEnvironment"),
    @NamedQuery(name = "Environment.findByEnvironmentName", query = "SELECT e FROM Environment e WHERE e.environmentName = :environmentName")})
public class Environment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_environment")
    private Integer idEnvironment;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "environment_name")
    private String environmentName;

    public Environment() {
    }

    public Environment(Integer idEnvironment) {
        this.idEnvironment = idEnvironment;
    }

    public Environment(Integer idEnvironment, String environmentName) {
        this.idEnvironment = idEnvironment;
        this.environmentName = environmentName;
    }

    public Integer getIdEnvironment() {
        return idEnvironment;
    }

    public void setIdEnvironment(Integer idEnvironment) {
        this.idEnvironment = idEnvironment;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEnvironment != null ? idEnvironment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Environment)) {
            return false;
        }
        Environment other = (Environment) object;
        if ((this.idEnvironment == null && other.idEnvironment != null) || (this.idEnvironment != null && !this.idEnvironment.equals(other.idEnvironment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Environment[ idEnvironment=" + idEnvironment + " ]";
    }
    
}
