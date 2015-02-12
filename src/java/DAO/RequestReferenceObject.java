/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.Environment;
import Entities.ReferenceEntities.HeaderReference;
import Entities.HostAddress;
import Entities.Method;
import Entities.ReferenceEntities.ParameterReference;
import Entities.Path;
import Entities.ReferenceEntities.RequestReference;
import Entities.Scheme;
import Entities.ReferenceEntities.TemplateReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import jpa.exceptions.RollbackFailureException;

/*
 * @author fellippe.mendonca
 */
public class RequestReferenceObject {

    RequestReference requestReference;
    Environment environment;
    Method method;
    Scheme scheme;
    HostAddress host;
    Path path;
    DataAccessObject dao;
    List<TemplateReferenceObject> templateReferenceObjectList;
    List<ParameterReferenceObject> parameterReferenceObjectList;
    List<HeaderReferenceObject> headerReferenceObjectList;

    public RequestReferenceObject(DataAccessObject dao) {
        this.requestReference = new RequestReference();
        this.environment = new Environment();
        this.method = new Method();
        this.scheme = new Scheme();
        this.host = new HostAddress();
        this.path = new Path();
        this.dao = dao;
        this.templateReferenceObjectList = new ArrayList();
        this.parameterReferenceObjectList = new ArrayList();
        this.headerReferenceObjectList = new ArrayList();
    }

    public RequestReferenceObject(RequestReference requestReference, DataAccessObject dao) throws NamingException {
        this.dao = dao;
        this.requestReference = requestReference;
        this.environment = this.dao.getEnvironmentJpaController().findEnvironment(this.requestReference.getIdEnvironment());
        this.method = this.dao.getMethodJpaController().findMethod(this.requestReference.getIdMethod());
        this.scheme = this.dao.getSchemeJpaController().findScheme(this.requestReference.getIdScheme());
        this.host = this.dao.getHostAddressJpaController().findHostAddress(this.requestReference.getIdHostAddress());
        this.path = this.dao.getPathJpaController().findPath(this.requestReference.getIdPath());

        templateReferenceObjectList = new ArrayList<>();
        for (TemplateReference tr : this.dao.getTemplateReferece(requestReference)) {
            TemplateReferenceObject templateObject = new TemplateReferenceObject(tr, dao);
            templateReferenceObjectList.add(templateObject);
        }

        parameterReferenceObjectList = new ArrayList<>();
        for (ParameterReference pr : this.dao.getParameterReference(requestReference)) {
            ParameterReferenceObject parameterObject = new ParameterReferenceObject(pr, dao);
            parameterReferenceObjectList.add(parameterObject);
        }

        headerReferenceObjectList = new ArrayList<>();
        for (HeaderReference hr : this.dao.getHeaderReference(requestReference)) {
            HeaderReferenceObject headerObject = new HeaderReferenceObject(hr, dao);
            headerReferenceObjectList.add(headerObject);
        }
    }

    public RequestReference getRequestReference() {
        return this.requestReference;
    }

    public void setRequestReference(RequestReference requestReference) {
        this.requestReference = requestReference;
    }

    public boolean persistRequestReference() {
        RequestReference tmp = new RequestReference(0, this.environment.getIdEnvironment(), this.method.getIdMethod(), this.scheme.getIdScheme(), this.host.getIdHostAddress(), this.path.getIdPath());
        try {
            this.requestReference = this.dao.getRequestReferenceJpaController().create(tmp);

            if (this.templateReferenceObjectList.isEmpty() == false) {
                for (TemplateReferenceObject t : this.templateReferenceObjectList) {
                    t.persistTemplateReference(this.requestReference);
                }
            }
            if (this.parameterReferenceObjectList.isEmpty() == false) {
                for (ParameterReferenceObject t : this.parameterReferenceObjectList) {
                    t.persistParameterReference(this.requestReference);
                }
            }
            if (this.headerReferenceObjectList.isEmpty() == false) {
                for (HeaderReferenceObject t : this.headerReferenceObjectList) {
                    t.persistHeaderReference(this.requestReference);
                }
            }
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String environment) {
        Environment tmp = new Environment(0, environment);
        try {
            this.environment = this.dao.getEnvironmentJpaController().findOrAdd(tmp);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        Method tmp = new Method(0, method);
        try {
            this.method = this.dao.getMethodJpaController().findOrAdd(tmp);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Scheme getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        Scheme tmp = new Scheme(0, scheme);
        try {
            this.scheme = this.dao.getSchemeJpaController().findOrAdd(tmp);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HostAddress getHost() {
        return this.host;
    }

    public void setHost(String host) {
        HostAddress tmp = new HostAddress(0, host);
        try {
            this.host = this.dao.getHostAddressJpaController().findOrAdd(tmp);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(String path) {
        Path tmp = new Path(0, path);
        try {
            this.path = this.dao.getPathJpaController().findOrAdd(tmp);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<TemplateReferenceObject> getTemplateReferenceObjectList() {
        return templateReferenceObjectList;
    }

    public void setTemplateReferenceObjectList(List<TemplateReferenceObject> templateReferenceObjectList) {
        this.templateReferenceObjectList = templateReferenceObjectList;
    }

    public void addTemplateReferenceObject(TemplateReferenceObject templateReferenceObject) {
        this.templateReferenceObjectList.add(templateReferenceObject);
    }

    public List<ParameterReferenceObject> getParameterReferenceObjectList() {
        return parameterReferenceObjectList;
    }

    public void setParameterReferenceObjectList(List<ParameterReferenceObject> parameterReferenceObjectList) {
        this.parameterReferenceObjectList = parameterReferenceObjectList;
    }

    public void addParameterReferenceObject(ParameterReferenceObject parameterReferenceObject) {
        this.parameterReferenceObjectList.add(parameterReferenceObject);
    }

    public List<HeaderReferenceObject> getHeaderReferenceObjectList() {
        return headerReferenceObjectList;
    }

    public void setHeaderReferenceObjectList(List<HeaderReferenceObject> headerReferenceObjectList) {
        this.headerReferenceObjectList = headerReferenceObjectList;
    }

    public void addHeaderReferenceObject(HeaderReferenceObject headerReferenceObject) {
        this.headerReferenceObjectList.add(headerReferenceObject);
    }

}
