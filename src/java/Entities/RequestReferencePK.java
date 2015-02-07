/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fellippe.mendonca
 */
@Embeddable
public class RequestReferencePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_request_reference")
    private int idRequestReference;
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

    public RequestReferencePK() {
    }

    public RequestReferencePK(int idRequestReference, int idEnvironment, int idMethod, int idScheme, int idHostAddress, int idPath) {
        this.idRequestReference = idRequestReference;
        this.idEnvironment = idEnvironment;
        this.idMethod = idMethod;
        this.idScheme = idScheme;
        this.idHostAddress = idHostAddress;
        this.idPath = idPath;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRequestReference;
        hash += (int) idEnvironment;
        hash += (int) idMethod;
        hash += (int) idScheme;
        hash += (int) idHostAddress;
        hash += (int) idPath;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestReferencePK)) {
            return false;
        }
        RequestReferencePK other = (RequestReferencePK) object;
        if (this.idRequestReference != other.idRequestReference) {
            return false;
        }
        if (this.idEnvironment != other.idEnvironment) {
            return false;
        }
        if (this.idMethod != other.idMethod) {
            return false;
        }
        if (this.idScheme != other.idScheme) {
            return false;
        }
        if (this.idHostAddress != other.idHostAddress) {
            return false;
        }
        if (this.idPath != other.idPath) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.RequestReferencePK[ idRequestReference=" + idRequestReference + ", idEnvironment=" + idEnvironment + ", idMethod=" + idMethod + ", idScheme=" + idScheme + ", idHostAddress=" + idHostAddress + ", idPath=" + idPath + " ]";
    }
    
}
