/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "RequestReference.findByIdRequestReference", query = "SELECT r FROM RequestReference r WHERE r.requestReferencePK.idRequestReference = :idRequestReference"),
    @NamedQuery(name = "RequestReference.findByIdEnvironment", query = "SELECT r FROM RequestReference r WHERE r.requestReferencePK.idEnvironment = :idEnvironment"),
    @NamedQuery(name = "RequestReference.findByIdMethod", query = "SELECT r FROM RequestReference r WHERE r.requestReferencePK.idMethod = :idMethod"),
    @NamedQuery(name = "RequestReference.findByIdScheme", query = "SELECT r FROM RequestReference r WHERE r.requestReferencePK.idScheme = :idScheme"),
    @NamedQuery(name = "RequestReference.findByIdHostAddress", query = "SELECT r FROM RequestReference r WHERE r.requestReferencePK.idHostAddress = :idHostAddress"),
    @NamedQuery(name = "RequestReference.findByIdPath", query = "SELECT r FROM RequestReference r WHERE r.requestReferencePK.idPath = :idPath")})
public class RequestReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RequestReferencePK requestReferencePK;

    public RequestReference() {
    }

    public RequestReference(RequestReferencePK requestReferencePK) {
        this.requestReferencePK = requestReferencePK;
    }

    public RequestReference(int idRequestReference, int idEnvironment, int idMethod, int idScheme, int idHostAddress, int idPath) {
        this.requestReferencePK = new RequestReferencePK(idRequestReference, idEnvironment, idMethod, idScheme, idHostAddress, idPath);
    }

    public RequestReferencePK getRequestReferencePK() {
        return requestReferencePK;
    }

    public void setRequestReferencePK(RequestReferencePK requestReferencePK) {
        this.requestReferencePK = requestReferencePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestReferencePK != null ? requestReferencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestReference)) {
            return false;
        }
        RequestReference other = (RequestReference) object;
        if ((this.requestReferencePK == null && other.requestReferencePK != null) || (this.requestReferencePK != null && !this.requestReferencePK.equals(other.requestReferencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.RequestReference[ requestReferencePK=" + requestReferencePK + " ]";
    }
    
}
