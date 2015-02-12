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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fellippe.mendonca
 */
@Entity
@Table(name = "path")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Path.findAll", query = "SELECT p FROM Path p"),
    @NamedQuery(name = "Path.findByIdPath", query = "SELECT p FROM Path p WHERE p.idPath = :idPath"),
    @NamedQuery(name = "Path.findByPathValue", query = "SELECT p FROM Path p WHERE p.pathValue = :pathValue")})
public class Path implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_path")
    private Integer idPath;
    @Size(max = 45)
    @Column(name = "path_value")
    private String pathValue;

    public Path() {
    }
    
    public Path(Integer idPath, String pathValue) {
        this.idPath = idPath;
        this.pathValue = pathValue;
    }

    public Path(Integer idPath) {
        this.idPath = idPath;
    }

    public Integer getIdPath() {
        return idPath;
    }

    public void setIdPath(Integer idPath) {
        this.idPath = idPath;
    }

    public String getPathValue() {
        return pathValue;
    }

    public void setPathValue(String pathValue) {
        this.pathValue = pathValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPath != null ? idPath.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Path)) {
            return false;
        }
        Path other = (Path) object;
        if ((this.idPath == null && other.idPath != null) || (this.idPath != null && !this.idPath.equals(other.idPath))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Path[ idPath=" + idPath + " ]";
    }
    
}
