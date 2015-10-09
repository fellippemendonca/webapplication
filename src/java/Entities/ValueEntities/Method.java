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
@Table(name = "method")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Method.findAll", query = "SELECT m FROM Method m"),
    @NamedQuery(name = "Method.findByIdMethod", query = "SELECT m FROM Method m WHERE m.idMethod = :idMethod"),
    @NamedQuery(name = "Method.findByMethodValue", query = "SELECT m FROM Method m WHERE m.methodValue = :methodValue")})
public class Method implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_method")
    private Integer idMethod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "method_value")
    private String methodValue;

    public Method() {
    }

    public Method(Integer idMethod) {
        this.idMethod = idMethod;
    }

    public Method(Integer idMethod, String methodValue) {
        this.idMethod = idMethod;
        this.methodValue = methodValue;
    }

    public Integer getIdMethod() {
        return idMethod;
    }

    public void setIdMethod(Integer idMethod) {
        this.idMethod = idMethod;
    }

    public String getMethodValue() {
        return methodValue;
    }

    public void setMethodValue(String methodValue) {
        this.methodValue = methodValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMethod != null ? idMethod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Method)) {
            return false;
        }
        Method other = (Method) object;
        if ((this.idMethod == null && other.idMethod != null) || (this.idMethod != null && !this.idMethod.equals(other.idMethod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Method[ idMethod=" + idMethod + " ]";
    }
    
}
