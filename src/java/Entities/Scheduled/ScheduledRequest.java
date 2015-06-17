/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.Scheduled;

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
@Table(name = "scheduled_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduledRequest.findAll", query = "SELECT s FROM ScheduledRequest s"),
    @NamedQuery(name = "ScheduledRequest.findByIdScheduledRequest", query = "SELECT s FROM ScheduledRequest s WHERE s.idScheduledRequest = :idScheduledRequest"),
    @NamedQuery(name = "ScheduledRequest.findByIdRequestReference", query = "SELECT s FROM ScheduledRequest s WHERE s.idRequestReference = :idRequestReference")})
public class ScheduledRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_scheduled_request")
    private Integer idScheduledRequest;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_reference")
    private int idRequestReference;

    public ScheduledRequest() {
    }

    public ScheduledRequest(Integer idScheduledRequest) {
        this.idScheduledRequest = idScheduledRequest;
    }

    public ScheduledRequest(Integer idScheduledRequest, int idRequestReference) {
        this.idScheduledRequest = idScheduledRequest;
        this.idRequestReference = idRequestReference;
    }

    public Integer getIdScheduledRequest() {
        return idScheduledRequest;
    }

    public void setIdScheduledRequest(Integer idScheduledRequest) {
        this.idScheduledRequest = idScheduledRequest;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idScheduledRequest != null ? idScheduledRequest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScheduledRequest)) {
            return false;
        }
        ScheduledRequest other = (ScheduledRequest) object;
        if ((this.idScheduledRequest == null && other.idScheduledRequest != null) || (this.idScheduledRequest != null && !this.idScheduledRequest.equals(other.idScheduledRequest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Scheduled.ScheduledRequest[ idScheduledRequest=" + idScheduledRequest + " ]";
    }
    
}
