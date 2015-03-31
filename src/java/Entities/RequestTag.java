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
@Table(name = "request_tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestTag.findAll", query = "SELECT r FROM RequestTag r"),
    @NamedQuery(name = "RequestTag.findByIdRequestTag", query = "SELECT r FROM RequestTag r WHERE r.idRequestTag = :idRequestTag"),
    @NamedQuery(name = "RequestTag.findByTagValue", query = "SELECT r FROM RequestTag r WHERE r.tagValue = :tagValue")})
public class RequestTag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_request_tag")
    private Integer idRequestTag;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tag_value")
    private String tagValue;

    public RequestTag() {
    }

    public RequestTag(Integer idRequestTag) {
        this.idRequestTag = idRequestTag;
    }

    public RequestTag(Integer idRequestTag, String tagValue) {
        this.idRequestTag = idRequestTag;
        this.tagValue = tagValue;
    }

    public Integer getIdRequestTag() {
        return idRequestTag;
    }

    public void setIdRequestTag(Integer idRequestTag) {
        this.idRequestTag = idRequestTag;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestTag != null ? idRequestTag.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestTag)) {
            return false;
        }
        RequestTag other = (RequestTag) object;
        if ((this.idRequestTag == null && other.idRequestTag != null) || (this.idRequestTag != null && !this.idRequestTag.equals(other.idRequestTag))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.RequestTag[ idRequestTag=" + idRequestTag + " ]";
    }
    
}
