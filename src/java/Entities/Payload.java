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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fellippe.mendonca
 */
@Entity
@Table(name = "payload")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payload.findAll", query = "SELECT p FROM Payload p"),
    @NamedQuery(name = "Payload.findByIdPayload", query = "SELECT p FROM Payload p WHERE p.idPayload = :idPayload"),
    @NamedQuery(name = "Payload.findByPayloadValue", query = "SELECT p FROM Payload p WHERE p.payloadValue = :payloadValue")
})
public class Payload implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_payload")
    private Integer idPayload;
    @Lob
    @Size(max = 65535)
    @Column(name = "payload_value")
    private String payloadValue;

    public Payload() {
    }
    
    public Payload(Integer idPayload, String payloadValue) {
        this.idPayload = idPayload;
        this.payloadValue = payloadValue;
    }

    public Payload(Integer idPayload) {
        this.idPayload = idPayload;
    }

    public Integer getIdPayload() {
        return idPayload;
    }

    public void setIdPayload(Integer idPayload) {
        this.idPayload = idPayload;
    }

    public String getPayloadValue() {
        return payloadValue;
    }

    public void setPayloadValue(String payloadValue) {
        this.payloadValue = payloadValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPayload != null ? idPayload.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payload)) {
            return false;
        }
        Payload other = (Payload) object;
        if ((this.idPayload == null && other.idPayload != null) || (this.idPayload != null && !this.idPayload.equals(other.idPayload))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Payload[ idPayload=" + idPayload + " ]";
    }
    
}
