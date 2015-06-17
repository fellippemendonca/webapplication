package DAO;

import DAO.ValidationDAO.Schedule.ScheduledRequestObject;
import Entities.Environment;
import Entities.HostAddress;
import Entities.Method;
import Entities.Path;
import Entities.Payload;
import Entities.ReferenceEntities.HeaderReference;
import Entities.ReferenceEntities.ParameterReference;
import Entities.ReferenceEntities.RequestReference;
import Entities.ReferenceEntities.RequestTagReference;
import Entities.ReferenceEntities.TemplateReference;
import Entities.RequestName;
import Entities.Scheduled.ScheduledRequest;
import Entities.Scheme;
import Entities.ValidationEntities.ValidationScenario;
import HttpConnections.RestRequester;
import JsonObjects.JHeader;
import JsonObjects.JParameter;
import JsonObjects.JsonRequestObject;
import JsonObjects.Tags.JsonTag;
import com.google.gson.Gson;
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
    RequestName requestName;
    Environment environment;
    Method method;
    Scheme scheme;
    HostAddress host;
    Path path;
    Payload payload;
    DataAccessObject dao;
    List<TemplateReferenceObject> templateReferenceObjectList;
    List<ParameterReferenceObject> parameterReferenceObjectList;
    List<HeaderReferenceObject> headerReferenceObjectList;
    JsonRequestObject jsonRequestObject;

    List<TagReferenceObject> tagReferenceObjectList;

//-----------------------JSON CONSTRUCTOR---------------------------------------
    public RequestReferenceObject(DataAccessObject dao, String json) {
        this.dao = dao;
        this.jsonRequestObject = new Gson().fromJson(json, JsonRequestObject.class);
        this.requestName = new RequestName(0, this.jsonRequestObject.getRequestName());
        this.environment = new Environment(0, this.jsonRequestObject.getEnvironment().toUpperCase());
        this.method = new Method(0, this.jsonRequestObject.getMethod().toUpperCase());
        this.scheme = new Scheme(0, this.jsonRequestObject.getScheme());
        this.host = new HostAddress(0, this.jsonRequestObject.getHost());
        this.path = new Path(0, this.jsonRequestObject.getPath());
        this.payload = new Payload(0, this.jsonRequestObject.getPayload());

        this.templateReferenceObjectList = new ArrayList();
        int i = 1;
        for (String template : this.jsonRequestObject.getTemplates()) {
            if (template.isEmpty() || template.isEmpty()) {
                System.out.println("Empty template value not inserted.");
            } else {
                this.templateReferenceObjectList.add(new TemplateReferenceObject(this.dao, template, i, 1));
            }
            i++;
        }

        this.headerReferenceObjectList = new ArrayList();
        for (JHeader header : this.jsonRequestObject.getHeaders()) {
            if (header.getName().isEmpty() || header.getValue().isEmpty()) {
                System.out.println("Empty header value not inserted.");
            } else {
                this.headerReferenceObjectList.add(new HeaderReferenceObject(this.dao, header.getName(), header.getValue()));
            }
        }

        this.parameterReferenceObjectList = new ArrayList();
        for (JParameter parameter : this.jsonRequestObject.getParameters()) {
            if (parameter.getName().isEmpty() || parameter.getValue().isEmpty()) {
                System.out.println("Empty parameter value not inserted.");
            } else {
                this.parameterReferenceObjectList.add(new ParameterReferenceObject(this.dao, parameter.getName(), parameter.getValue(), 1));
            }
        }

        this.tagReferenceObjectList = new ArrayList<>();
        for (JsonTag tag : this.jsonRequestObject.getJsonTags()) {
            if (tag.getName().isEmpty()) {
                System.out.println("Empty tag value not inserted.");
            } else {
                this.tagReferenceObjectList.add(new TagReferenceObject(this.dao, tag.getName().toUpperCase()));
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////

//-----------------------REFERENCE CONSTRUCTOR----------------------------------
    public RequestReferenceObject(DataAccessObject dao, RequestReference requestReference) throws NamingException {
        this.jsonRequestObject = new JsonRequestObject();
        this.dao = dao;

        this.requestReference = requestReference;
        if (this.requestReference != null) {
            this.jsonRequestObject.setRequestReference(this.requestReference.getIdRequestReference());
        }

        this.requestName = this.dao.getRequestNameJpaController().findRequestName(this.requestReference.getIdRequestName());
        if (this.requestName != null) {
            this.jsonRequestObject.setRequestName(this.requestName.getRequestName());
        }
        this.environment = this.dao.getEnvironmentJpaController().findEnvironment(this.requestReference.getIdEnvironment());
        if (this.environment != null) {
            this.jsonRequestObject.setEnvironment(this.environment.getEnvironmentName());
        }
        this.method = this.dao.getMethodJpaController().findMethod(this.requestReference.getIdMethod());
        if (this.method != null) {
            this.jsonRequestObject.setMethod(this.method.getMethodValue());
        }
        this.scheme = this.dao.getSchemeJpaController().findScheme(this.requestReference.getIdScheme());
        if (this.scheme != null) {
            this.jsonRequestObject.setScheme(this.scheme.getSchemeValue());
        }
        this.host = this.dao.getHostAddressJpaController().findHostAddress(this.requestReference.getIdHostAddress());
        if (this.host != null) {
            this.jsonRequestObject.setHost(this.host.getHostAddressValue());
        }
        this.path = this.dao.getPathJpaController().findPath(this.requestReference.getIdPath());
        if (this.path != null) {
            this.jsonRequestObject.setPath(this.path.getPathValue());
        }
        this.payload = this.dao.getPayloadJpaController().findPayload(this.requestReference.getIdPayload());
        if (this.payload != null) {
            this.jsonRequestObject.setPayload(this.payload.getPayloadValue());
        }

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

        this.tagReferenceObjectList = new ArrayList<>();
        for (RequestTagReference tg : this.dao.getRequestTagReference(requestReference)) {
            TagReferenceObject tagObject = new TagReferenceObject(tg, dao);
            this.tagReferenceObjectList.add(tagObject);
            JsonTag jsonTag = new JsonTag(tagObject.getRequestTag().getIdRequestTag(), tagObject.getRequestTag().getTagValue());
            this.jsonRequestObject.getJsonTags().add(jsonTag);
        }
    }
        ////////////////////////////////////////////////////////////////////////////////

    //-----------------REMOVE AND PERSIST NEW REQUEST-------------------------------
    public boolean updateRequestReference() {
        int oldRequestReferenceId = this.jsonRequestObject.getRequestReference();
        try {
            System.out.println("Updating request number: " + this.jsonRequestObject.getRequestReference());
            RequestReference rr = this.dao.getRequestReferenceJpaController().findRequestReference(this.jsonRequestObject.getRequestReference());
            if (rr != null && clearReferences(rr)) {
                this.dao.getRequestReferenceJpaController().destroy(rr.getIdRequestReference());
                persistRequestReference();

                /*Busca na base ValidationScenarios que estejam relacionados ao antigo request q foi substituido*/
                for (ValidationScenario validationScenario : this.dao.getValidationScenarioJpaController().findByIdRequestReference(oldRequestReferenceId)) {
                    validationScenario.setIdRequestReference(this.requestReference.getIdRequestReference());
                    this.dao.getValidationScenarioJpaController().edit(validationScenario);
                }

                /*Busca se request possuía agendamento e agenda sua nova execução*/
                if (this.dao.getScheduledRequestJpaController().find(new ScheduledRequest(0, oldRequestReferenceId)) != null) {
                    ScheduledRequest scheduledRequest = this.dao.getScheduledRequestJpaController().find(new ScheduledRequest(0, oldRequestReferenceId));
                    scheduledRequest.setIdRequestReference(this.requestReference.getIdRequestReference());
                    this.dao.getScheduledRequestJpaController().edit(scheduledRequest);
                }

                System.out.println("Success.");
                return true;
            } else {
                System.out.println("Fail.");
                return false;
            }
        } catch (DAO.exceptions.RollbackFailureException ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

//--------------------------------REMOVE REQUEST--------------------------------
    public boolean deleteRequestReference() {
        int oldRequestReferenceId = this.jsonRequestObject.getRequestReference();
        try {
            System.out.println("Removing request number: " + this.jsonRequestObject.getRequestReference());
            RequestReference rr = this.dao.getRequestReferenceJpaController().findRequestReference(this.jsonRequestObject.getRequestReference());
            if (rr != null && clearReferences(rr)) {
                this.dao.getRequestReferenceJpaController().destroy(rr.getIdRequestReference());

                /*Busca na base ValidationScenarios que estejam relacionados ao antigo request q foi removido*/
                for (ValidationScenario validationScenario : this.dao.getValidationScenarioJpaController().findByIdRequestReference(oldRequestReferenceId)) {
                    this.dao.getValidationScenarioJpaController().destroy(validationScenario.getIdValidationScenario());
                }

                /*Busca se request possuía agendamento e remove sua execução*/
                if (this.dao.getScheduledRequestJpaController().find(new ScheduledRequest(0, oldRequestReferenceId)) != null) {
                    ScheduledRequestObject scheduledRequestObject = new ScheduledRequestObject(this.dao, this.requestReference.getIdRequestReference());
                    scheduledRequestObject.deleteSchedule();
                }

                System.out.println("Success.");
                return true;
            } else {
                System.out.println("Fail.");
                return false;
            }
        } catch (DAO.exceptions.RollbackFailureException ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean clearReferences(RequestReference requestReference) throws NamingException {
        if (requestReference != null) {
            for (TemplateReference tr : this.dao.getTemplateReferece(requestReference)) {
                new TemplateReferenceObject(tr, this.dao).deleteTemplateReference();
            }
            for (HeaderReference hr : this.dao.getHeaderReference(requestReference)) {
                new HeaderReferenceObject(hr, this.dao).deleteHeaderReference();
            }
            for (ParameterReference pr : this.dao.getParameterReference(requestReference)) {
                new ParameterReferenceObject(pr, this.dao).deleteParameterReference();
            }
            for (RequestTagReference tg : this.dao.getRequestTagReference(requestReference)) {
                new TagReferenceObject(tg, this.dao).deleteRequestTagReference();
            }
            return true;
        } else {
            return false;
        }

    }
////////////////////////////////////////////////////////////////////////////////

//-------------------------PERSIST REQUEST--------------------------------------
    public boolean persistRequestReference() {
        persistRequestName();
        persistEnvironment();
        persistMethod();
        persistScheme();
        persistHost();
        persistPath();
        persistPayload();

        RequestReference tmp = new RequestReference(0, this.requestName.getIdRequestName(), this.environment.getIdEnvironment(), this.method.getIdMethod(), this.scheme.getIdScheme(), this.host.getIdHostAddress(), this.path.getIdPath(), this.payload.getIdPayload());
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
            if (this.tagReferenceObjectList.isEmpty() == false) {
                for (TagReferenceObject t : this.tagReferenceObjectList) {
                    t.persistTagReference(this.requestReference);
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
////////////////////////////////////////////////////////////////////////////////

//-------------------------GERADOR DO REQUESTER---------------------------------
    public RestRequester generateRestRequester() {
        RestRequester restRequester = new RestRequester();
        restRequester.setMethod(getMethod().getMethodValue());
        restRequester.setScheme(getScheme().getSchemeValue());
        restRequester.setHost(getHost().getHostAddressValue());
        restRequester.setPath(getPath().getPathValue());
        if (getPayload().getPayloadValue().equals("") == false) {
            restRequester.setEntity(getPayload().getPayloadValue());
        }
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

//----------------------------GETTERS SETTERS-----------------------------------
    public RequestReference getRequestReference() {
        return this.requestReference;
    }
//------------------------------------------------------------------------------

    public RequestName getRequestName() {
        return this.requestName;
    }

    public void persistRequestName() {
        try {
            this.requestName = this.dao.getRequestNameJpaController().findOrAdd(this.requestName);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public Environment getEnvironment() {
        return this.environment;
    }

    public void persistEnvironment() {
        try {
            this.environment = this.dao.getEnvironmentJpaController().findOrAdd(this.environment);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public Method getMethod() {
        return this.method;
    }

    public void persistMethod() {
        try {
            this.method = this.dao.getMethodJpaController().findOrAdd(this.method);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public Scheme getScheme() {
        return this.scheme;
    }

    public void persistScheme() {
        try {
            this.scheme = this.dao.getSchemeJpaController().findOrAdd(this.scheme);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public HostAddress getHost() {
        return this.host;
    }

    public void persistHost() {
        try {
            this.host = this.dao.getHostAddressJpaController().findOrAdd(this.host);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public Path getPath() {
        return this.path;
    }

    public void persistPath() {
        try {
            this.path = this.dao.getPathJpaController().findOrAdd(this.path);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*------------------------------------------------------------------------*/
    public Payload getPayload() {
        return this.payload;
    }

    public void persistPayload() {
        try {
            this.payload = this.dao.getPayloadJpaController().create(this.payload);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*------------------------------------------------------------------------*/
    public List<TemplateReferenceObject> getTemplateReferenceObjectList() {
        return templateReferenceObjectList;
    }

    public void setTemplateReferenceObjectList(List<TemplateReferenceObject> templateReferenceObjectList) {
        this.templateReferenceObjectList = templateReferenceObjectList;
    }

    public void addTemplateReferenceObject(TemplateReferenceObject templateReferenceObject) {
        this.templateReferenceObjectList.add(templateReferenceObject);
    }

    /*------------------------------------------------------------------------*/
    public List<ParameterReferenceObject> getParameterReferenceObjectList() {
        return parameterReferenceObjectList;
    }

    public void setParameterReferenceObjectList(List<ParameterReferenceObject> parameterReferenceObjectList) {
        this.parameterReferenceObjectList = parameterReferenceObjectList;
    }

    public void addParameterReferenceObject(ParameterReferenceObject parameterReferenceObject) {
        this.parameterReferenceObjectList.add(parameterReferenceObject);
    }

    /*------------------------------------------------------------------------*/
    public List<HeaderReferenceObject> getHeaderReferenceObjectList() {
        return headerReferenceObjectList;
    }

    public void setHeaderReferenceObjectList(List<HeaderReferenceObject> headerReferenceObjectList) {
        this.headerReferenceObjectList = headerReferenceObjectList;
    }

    public void addHeaderReferenceObject(HeaderReferenceObject headerReferenceObject) {
        this.headerReferenceObjectList.add(headerReferenceObject);
    }

    /*------------------------------------------------------------------------*/
    public String getRequestObjectJson() {
        Gson gson = new Gson();
        return gson.toJson(this.jsonRequestObject);
    }

    public JsonRequestObject getRequestObject() {
        return this.jsonRequestObject;
    }
}
