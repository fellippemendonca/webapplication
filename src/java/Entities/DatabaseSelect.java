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
@Table(name = "database_select")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatabaseSelect.findAll", query = "SELECT d FROM DatabaseSelect d"),
    @NamedQuery(name = "DatabaseSelect.findByIdDatabaseSelect", query = "SELECT d FROM DatabaseSelect d WHERE d.idDatabaseSelect = :idDatabaseSelect"),
    @NamedQuery(name = "DatabaseSelect.findByDatabaseName", query = "SELECT d FROM DatabaseSelect d WHERE d.databaseName = :databaseName")})
public class DatabaseSelect implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_database_select")
    private Integer idDatabaseSelect;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "database_name")
    private String databaseName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "value")
    private String value;

    public DatabaseSelect() {
    }

    public DatabaseSelect(Integer idDatabaseSelect) {
        this.idDatabaseSelect = idDatabaseSelect;
    }

    public DatabaseSelect(Integer idDatabaseSelect, String databaseName, String value) {
        this.idDatabaseSelect = idDatabaseSelect;
        this.databaseName = databaseName;
        this.value = value;
    }

    public Integer getIdDatabaseSelect() {
        return idDatabaseSelect;
    }

    public void setIdDatabaseSelect(Integer idDatabaseSelect) {
        this.idDatabaseSelect = idDatabaseSelect;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDatabaseSelect != null ? idDatabaseSelect.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatabaseSelect)) {
            return false;
        }
        DatabaseSelect other = (DatabaseSelect) object;
        if ((this.idDatabaseSelect == null && other.idDatabaseSelect != null) || (this.idDatabaseSelect != null && !this.idDatabaseSelect.equals(other.idDatabaseSelect))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.DatabaseSelect[ idDatabaseSelect=" + idDatabaseSelect + " ]";
    }
    
}
