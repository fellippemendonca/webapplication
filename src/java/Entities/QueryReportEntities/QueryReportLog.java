/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.QueryReportEntities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fellippe.mendonca
 */
@Entity
@Table(name = "query_report_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QueryReportLog.findAll", query = "SELECT q FROM QueryReportLog q"),
    @NamedQuery(name = "QueryReportLog.findById", query = "SELECT q FROM QueryReportLog q WHERE q.id = :id"),
    @NamedQuery(name = "QueryReportLog.findByQueryReportId", query = "SELECT q FROM QueryReportLog q WHERE q.executionDateTime >= :executionDateTime and q.queryReportId = :queryReportId order by q.executionDateTime"),
    @NamedQuery(name = "QueryReportLog.findByExecutionDateTime", query = "SELECT q FROM QueryReportLog q WHERE q.executionDateTime = :executionDateTime")})
public class QueryReportLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "query_report_id")
    private int queryReportId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "execution_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executionDateTime;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "query_result_json_string")
    private String queryResultJsonString;

    public QueryReportLog() {
    }

    public QueryReportLog(Integer id) {
        this.id = id;
    }

    public QueryReportLog(Integer id, int queryReportId, Date executionDateTime, String queryResultJsonString) {
        this.id = id;
        this.queryReportId = queryReportId;
        this.executionDateTime = executionDateTime;
        this.queryResultJsonString = queryResultJsonString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQueryReportId() {
        return queryReportId;
    }

    public void setQueryReportId(int queryReportId) {
        this.queryReportId = queryReportId;
    }

    public Date getExecutionDateTime() {
        return executionDateTime;
    }

    public void setExecutionDateTime(Date executionDateTime) {
        this.executionDateTime = executionDateTime;
    }

    public String getQueryResultJsonString() {
        return queryResultJsonString;
    }

    public void setQueryResultJsonString(String queryResultJsonString) {
        this.queryResultJsonString = queryResultJsonString;
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
        if (!(object instanceof QueryReportLog)) {
            return false;
        }
        QueryReportLog other = (QueryReportLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.QueryReportEntities.QueryReportLog[ id=" + id + " ]";
    }
    
}
