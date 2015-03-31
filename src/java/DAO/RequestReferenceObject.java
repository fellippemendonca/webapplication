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
import HttpConnections.RestRequester;
import JsonObjects.JHeader;
import JsonObjects.JParameter;
import JsonObjects.JsonRequestObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import jpa.exceptions.RollbackFailureException;
import com.google.gson.Gson;


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
    JsonRequestObject jsonRequestObject;


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
        this.jsonRequestObject = new JsonRequestObject();
    }
    
    public RequestReferenceObject(DataAccessObject dao, String json) {
        this.dao = dao;
        Gson gson = new Gson();
        JsonRequestObject obj = gson.fromJson(json, JsonRequestObject.class);
        this.jsonRequestObject = obj;
        this.environment = new Environment(0, obj.getEnvironment().toUpperCase());
        this.method = new Method(0, obj.getMethod().toUpperCase());
        this.scheme = new Scheme(0, obj.getScheme());
        this.host = new HostAddress(0, obj.getHost());
        this.path = new Path(0, obj.getPath());
        this.templateReferenceObjectList = new ArrayList();
        this.parameterReferenceObjectList = new ArrayList();
        this.headerReferenceObjectList = new ArrayList();
        int i = 1;
        for (String template : obj.getTemplates()) {
            if (template.isEmpty() || template.isEmpty()) {
                System.out.println("Empty value not inserted.");
            } else {
                this.templateReferenceObjectList.add(new TemplateReferenceObject(this.dao, template, i, 1));
            }
            i++;
        }

        for (JHeader header : obj.getHeaders()) {
            if (header.getName().isEmpty() || header.getValue().isEmpty()) {
                System.out.println("Empty value not inserted.");
            } else {
                this.headerReferenceObjectList.add(new HeaderReferenceObject(this.dao, header.getName(), header.getValue()));
            }
        }

        for (JParameter parameter : obj.getParameters()) {
            if (parameter.getName().isEmpty() || parameter.getValue().isEmpty()) {
                System.out.println("Empty value not inserted.");
            } else {
                this.parameterReferenceObjectList.add(new ParameterReferenceObject(this.dao, parameter.getName(), parameter.getValue(), 1));
            }
        }
    }

    public RequestReferenceObject(DataAccessObject dao, RequestReference requestReference) throws NamingException {
        this.jsonRequestObject = new JsonRequestObject();
        this.dao = dao;
        
        this.requestReference = requestReference;
        this.jsonRequestObject.setRequestReference(this.requestReference.getIdRequestReference());
        
        this.environment = this.dao.getEnvironmentJpaController().findEnvironment(this.requestReference.getIdEnvironment());
        this.jsonRequestObject.setEnvironment(this.environment.getEnvironmentName());
        
        this.method = this.dao.getMethodJpaController().findMethod(this.requestReference.getIdMethod());
        this.jsonRequestObject.setMethod(this.method.getMethodValue());
        
        this.scheme = this.dao.getSchemeJpaController().findScheme(this.requestReference.getIdScheme());
        this.jsonRequestObject.setScheme(this.scheme.getSchemeValue());
        
        this.host = this.dao.getHostAddressJpaController().findHostAddress(this.requestReference.getIdHostAddress());
        this.jsonRequestObject.setHost(this.host.getHostAddressValue());
        
        this.path = this.dao.getPathJpaController().findPath(this.requestReference.getIdPath());
        this.jsonRequestObject.setPath(this.path.getPathValue());

        this.templateReferenceObjectList = new ArrayList<>();
        for (TemplateReference tr : this.dao.getTemplateReferece(requestReference)) {
            TemplateReferenceObject templateObject = new TemplateReferenceObject(tr, dao);
            this.templateReferenceObjectList.add(templateObject);
            this.jsonRequestObject.getTemplates().add(templateObject.getTemplate().getTemplateValue());
        }

        this.headerReferenceObjectList = new ArrayList<>();
        for (HeaderReference hr : this.dao.getHeaderReference(requestReference)) {
            HeaderReferenceObject headerObject = new HeaderReferenceObject(hr, dao);
            this.headerReferenceObjectList.add(headerObject);
            JHeader jheader = new JHeader(headerObject.getHeader().getHeaderName(), headerObject.getHeader().getHeaderValue());
            this.jsonRequestObject.getHeaders().add(jheader);
        }
        
        this.parameterReferenceObjectList = new ArrayList<>();
        for (ParameterReference pr : this.dao.getParameterReference(requestReference)) {
            ParameterReferenceObject parameterObject = new ParameterReferenceObject(pr, dao);
            this.parameterReferenceObjectList.add(parameterObject);
            JParameter jparameter = new JParameter(parameterObject.getParameter().getParameterName(), parameterObject.getParameter().getParameterValue());
            this.jsonRequestObject.getParameters().add(jparameter);
        }
    }

    public RequestReference getRequestReference() {
        return this.requestReference;
    }

    public void setRequestReference(RequestReference requestReference) {
        this.requestReference = requestReference;
    }

    public boolean updateRequestReference() {
        try {
            RequestReference rr = this.dao.getRequestReferenceJpaController().findRequestReference(this.jsonRequestObject.getRequestReference());
            clearReferences(rr);
            this.dao.getRequestReferenceJpaController().destroy(this.jsonRequestObject.getRequestReference());
            persistRequestReference();
            return true;
        } catch (DAO.exceptions.RollbackFailureException ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return false;
    }
    
    public boolean persistRequestReference() {
        persistEnvironment();
        persistMethod();
        persistScheme();
        persistHost();
        persistPath();

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
        this.environment = new Environment(0, environment);
    }

    public void persistEnvironment() {
        try {
            this.environment = this.dao.getEnvironmentJpaController().findOrAdd(this.environment);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = new Method(0, method);
    }

    public void persistMethod() {
        try {
            this.method = this.dao.getMethodJpaController().findOrAdd(this.method);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Scheme getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = new Scheme(0, scheme);
    }

    public void persistScheme() {
        try {
            this.scheme = this.dao.getSchemeJpaController().findOrAdd(this.scheme);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HostAddress getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = new HostAddress(0, host);
    }

    public void persistHost() {
        try {
            this.host = this.dao.getHostAddressJpaController().findOrAdd(this.host);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Path getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = new Path(0, path);
    }

    public void persistPath() {
        try {
            this.path = this.dao.getPathJpaController().findOrAdd(this.path);
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

    public String getRequestObjectJson() {
        Gson gson = new Gson();
        return gson.toJson(this.jsonRequestObject);
    }
    
    public JsonRequestObject getRequestObject() {
        return this.jsonRequestObject;
    }
    
    
    
    
    public boolean clearReferences(RequestReference requestReference) throws NamingException{
        for (TemplateReference tr : this.dao.getTemplateReferece(requestReference)) {
            TemplateReferenceObject templateObject = new TemplateReferenceObject(tr, dao);
            templateObject.deleteTemplateReference();
        }

        for (HeaderReference hr : this.dao.getHeaderReference(requestReference)) {
            HeaderReferenceObject headerObject = new HeaderReferenceObject(hr, dao);
            headerObject.deleteHeaderReference();
        }
        
        for (ParameterReference pr : this.dao.getParameterReference(requestReference)) {
            ParameterReferenceObject parameterObject = new ParameterReferenceObject(pr, dao);
            parameterObject.deleteParameterReference();
        }
        return true;
    }
    
    
    
    public RestRequester generateRestRequester(){
        RestRequester restRequester = new RestRequester();
            restRequester.setMethod(getMethod().getMethodValue());
            restRequester.setScheme(getScheme().getSchemeValue());
            restRequester.setHost(getHost().getHostAddressValue());
            restRequester.setPath(getPath().getPathValue());
            if (getHeaderReferenceObjectList().isEmpty() == false) {
                for (HeaderReferenceObject hro : getHeaderReferenceObjectList()) {
                    restRequester.addHeader(hro.getHeader().getHeaderName(), hro.getHeader().getHeaderValue());
                }
            }
            if (getTemplateReferenceObjectList().isEmpty() == false) {
                for (TemplateReferenceObject tro : getTemplateReferenceObjectList()) {
                    restRequester.addTemplate(tro.getTemplate().getTemplateValue());
                }
            }
            if (getParameterReferenceObjectList().isEmpty() == false) {
                for (ParameterReferenceObject pro : getParameterReferenceObjectList()) {
                    restRequester.addParameter(pro.getParameter().getParameterName(), pro.getParameter().getParameterValue());
                }
            }
            return restRequester;
    }
}
