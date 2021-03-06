/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities.ReferenceEntities;

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
@Table(name = "request_reference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestReference.findAll", query = "SELECT r FROM RequestReference r"),
    @NamedQuery(name = "RequestReference.findByIdRequestReference", query = "SELECT r FROM RequestReference r WHERE r.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "RequestReference.findByIdRequestName", query = "SELECT r FROM RequestReference r WHERE r.idRequestName = :idRequestName"),
    @NamedQuery(name = "RequestReference.findByIdRequestDescription", query = "SELECT r FROM RequestReference r WHERE r.idRequestDescription = :idRequestDescription"),
    @NamedQuery(name = "RequestReference.findByIdEnvironment", query = "SELECT r FROM RequestReference r WHERE r.idEnvironment = :idEnvironment"),
    @NamedQuery(name = "RequestReference.findByIdMethod", query = "SELECT r FROM RequestReference r WHERE r.idMethod = :idMethod"),
    @NamedQuery(name = "RequestReference.findByIdScheme", query = "SELECT r FROM RequestReference r WHERE r.idScheme = :idScheme"),
    @NamedQuery(name = "RequestReference.findByIdHostAddress", query = "SELECT r FROM RequestReference r WHERE r.idHostAddress = :idHostAddress"),
    @NamedQuery(name = "RequestReference.findByIdPath", query = "SELECT r FROM RequestReference r WHERE r.idPath = :idPath"),
    @NamedQuery(name = "RequestReference.findByIdPayload", query = "SELECT r FROM RequestReference r WHERE r.idPayload = :idPayload")})
public class RequestReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_request_reference")
    private Integer idRequestReference;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_name")
    private int idRequestName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_request_description")
    private int idRequestDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_environment")
    private int idEnvironment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_method")
    private int idMethod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_scheme")
    private int idScheme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_host_address")
    private int idHostAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_path")
    private int idPath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_payload")
    private int idPayload;

    public RequestReference() {
    }

    public RequestReference(Integer idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public RequestReference(Integer idRequestReference, int idRequestName, int idRequestDescription, int idEnvironment, int idMethod, int idScheme, int idHostAddress, int idPath, int idPayload) {
        this.idRequestReference = idRequestReference;
        this.idRequestName = idRequestName;
        this.idRequestDescription = idRequestDescription;
        this.idEnvironment = idEnvironment;
        this.idMethod = idMethod;
        this.idScheme = idScheme;
        this.idHostAddress = idHostAddress;
        this.idPath = idPath;
        this.idPayload = idPayload;
    }

    public Integer getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(Integer idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public int getIdRequestName() {
        return idRequestName;
    }

    public void setIdRequestName(int idRequestName) {
        this.idRequestName = idRequestName;
    }
    
    public int getIdRequestDescription() {
        return idRequestDescription;
    }
    
    public void setIdRequestDescription(int idRequestDescription) {
        this.idRequestDescription = idRequestDescription;
    }

    public int getIdEnvironment() {
        return idEnvironment;
    }

    public void setIdEnvironment(int idEnvironment) {
        this.idEnvironment = idEnvironment;
    }

    public int getIdMethod() {
        return idMethod;
    }

    public void setIdMethod(int idMethod) {
        this.idMethod = idMethod;
    }

    public int getIdScheme() {
        return idScheme;
    }

    public void setIdScheme(int idScheme) {
        this.idScheme = idScheme;
    }

    public int getIdHostAddress() {
        return idHostAddress;
    }

    public void setIdHostAddress(int idHostAddress) {
        this.idHostAddress = idHostAddress;
    }

    public int getIdPath() {
        return idPath;
    }

    public void setIdPath(int idPath) {
        this.idPath = idPath;
    }

    public int getIdPayload() {
        return idPayload;
    }

    public void setIdPayload(int idPayload) {
        this.idPayload = idPayload;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRequestReference != null ? idRequestReference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestReference)) {
            return false;
        }
        RequestReference other = (RequestReference) object;
        if ((this.idRequestReference == null && other.idRequestReference != null) || (this.idRequestReference != null && !this.idRequestReference.equals(other.idRequestReference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ReferenceEntities.RequestReference[ idRequestReference=" + idRequestReference + " ]";
    }
    
}
