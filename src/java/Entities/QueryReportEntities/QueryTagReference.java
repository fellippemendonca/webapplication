/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.QueryReportEntities;

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
@Table(name = "query_tag_reference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QueryTagReference.findAll", query = "SELECT q FROM QueryTagReference q"),
    @NamedQuery(name = "QueryTagReference.findById", query = "SELECT q FROM QueryTagReference q WHERE q.id = :id"),
    @NamedQuery(name = "QueryTagReference.findByIdQueryReport", query = "SELECT q FROM QueryTagReference q WHERE q.idQueryReport = :idQueryReport"),
    @NamedQuery(name = "QueryTagReference.findByIdQueryTag", query = "SELECT q FROM QueryTagReference q WHERE q.idQueryTag = :idQueryTag")})
public class QueryTagReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_query_report")
    private int idQueryReport;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_query_tag")
    private int idQueryTag;

    public QueryTagReference() {
    }

    public QueryTagReference(Integer id) {
        this.id = id;
    }

    public QueryTagReference(Integer id, int idQueryReport, int idQueryTag) {
        this.id = id;
        this.idQueryReport = idQueryReport;
        this.idQueryTag = idQueryTag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdQueryReport() {
        return idQueryReport;
    }

    public void setIdQueryReport(int idQueryReport) {
        this.idQueryReport = idQueryReport;
    }

    public int getIdQueryTag() {
        return idQueryTag;
    }

    public void setIdQueryTag(int idQueryTag) {
        this.idQueryTag = idQueryTag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QueryTagReference)) {
            return false;
        }
        QueryTagReference other = (QueryTagReference) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.QueryReportEntities.QueryTagReference[ id=" + id + " ]";
    }
    
}
