/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.ValueEntities;

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
@Table(name = "host_address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HostAddress.findAll", query = "SELECT h FROM HostAddress h"),
    @NamedQuery(name = "HostAddress.findByIdHostAddress", query = "SELECT h FROM HostAddress h WHERE h.idHostAddress = :idHostAddress"),
    @NamedQuery(name = "HostAddress.findByHostAddressValue", query = "SELECT h FROM HostAddress h WHERE h.hostAddressValue = :hostAddressValue")})
public class HostAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//recently added;
    @Basic(optional = false)
    @Column(name = "id_host_address")
    private Integer idHostAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "host_address_value")
    private String hostAddressValue;

    public HostAddress() {
    }

    public HostAddress(Integer idHostAddress) {
        this.idHostAddress = idHostAddress;
    }

    public HostAddress(Integer idHostAddress, String hostAddressValue) {
        this.idHostAddress = idHostAddress;
        this.hostAddressValue = hostAddressValue;
    }

    public Integer getIdHostAddress() {
        return idHostAddress;
    }

    public void setIdHostAddress(Integer idHostAddress) {
        this.idHostAddress = idHostAddress;
    }

    public String getHostAddressValue() {
        return hostAddressValue;
    }

    public void setHostAddressValue(String hostAddressValue) {
        this.hostAddressValue = hostAddressValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHostAddress != null ? idHostAddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HostAddress)) {
            return false;
        }
        HostAddress other = (HostAddress) object;
        if ((this.idHostAddress == null && other.idHostAddress != null) || (this.idHostAddress != null && !this.idHostAddress.equals(other.idHostAddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.HostAddress[ idHostAddress=" + idHostAddress + " ]";
    }
    
}
