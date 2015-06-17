/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.Scheduled;

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
@Table(name = "scheduled_request_execution_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduledRequestExecutionLog.findAll", query = "SELECT s FROM ScheduledRequestExecutionLog s"),
    @NamedQuery(name = "ScheduledRequestExecutionLog.findByIdScheduledRequestExecutionLog", query = "SELECT s FROM ScheduledRequestExecutionLog s WHERE s.idScheduledRequestExecutionLog = :idScheduledRequestExecutionLog"),
    @NamedQuery(name = "ScheduledRequestExecutionLog.findByIdRequestReference", query = "SELECT s FROM ScheduledRequestExecutionLog s WHERE s.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "ScheduledRequestExecutionLog.findByExecutionDate", query = "SELECT s FROM ScheduledRequestExecutionLog s WHERE s.executionDate = :executionDate"),
    @NamedQuery(name = "ScheduledRequestExecutionLog.findByExecutionDateAndId", query = "SELECT s FROM ScheduledRequestExecutionLog s WHERE s.executionDate >= :executionDate and s.executionDate < :dayAfter and s.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "ScheduledRequestExecutionLog.findBySuccess", query = "SELECT s FROM ScheduledRequestExecutionLog s WHERE s.success = :success")})
public class ScheduledRequestExecutionLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_scheduled_request_execution_log")
    private Integer idScheduledRequestExecutionLog;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_reference")
    private int idRequestReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "execution_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date executionDate;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "json_request_validation")
    private String jsonRequestValidation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "success")
    private int success;

    public ScheduledRequestExecutionLog() {
    }

    public ScheduledRequestExecutionLog(Integer idScheduledRequestExecutionLog) {
        this.idScheduledRequestExecutionLog = idScheduledRequestExecutionLog;
    }

    public ScheduledRequestExecutionLog(Integer idScheduledRequestExecutionLog, int idRequestReference, Date executionDate, String jsonRequestValidation, int success) {
        this.idScheduledRequestExecutionLog = idScheduledRequestExecutionLog;
        this.idRequestReference = idRequestReference;
        this.executionDate = executionDate;
        this.jsonRequestValidation = jsonRequestValidation;
        this.success = success;
    }

    public Integer getIdScheduledRequestExecutionLog() {
        return idScheduledRequestExecutionLog;
    }

    public void setIdScheduledRequestExecutionLog(Integer idScheduledRequestExecutionLog) {
        this.idScheduledRequestExecutionLog = idScheduledRequestExecutionLog;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public String getJsonRequestValidation() {
        return jsonRequestValidation;
    }

    public void setJsonRequestValidation(String jsonRequestValidation) {
        this.jsonRequestValidation = jsonRequestValidation;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idScheduledRequestExecutionLog != null ? idScheduledRequestExecutionLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScheduledRequestExecutionLog)) {
            return false;
        }
        ScheduledRequestExecutionLog other = (ScheduledRequestExecutionLog) object;
        if ((this.idScheduledRequestExecutionLog == null && other.idScheduledRequestExecutionLog != null) || (this.idScheduledRequestExecutionLog != null && !this.idScheduledRequestExecutionLog.equals(other.idScheduledRequestExecutionLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Scheduled.ScheduledRequestExecutionLog[ idScheduledRequestExecutionLog=" + idScheduledRequestExecutionLog + " ]";
    }
    
}
