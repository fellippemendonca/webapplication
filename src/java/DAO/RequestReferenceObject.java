package DAO;

import DAO.ValidationDAO.Schedule.ScheduledRequestObject;
import Entities.DynamicInputData;
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
import JsonObjects.DynamicData.JsonDynamicData;
import JsonObjects.JHeader;
import JsonObjects.JParameter;
import JsonObjects.JsonRequestObject;
import JsonObjects.Tags.JsonTag;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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

    DataAccessObject dao;
    RequestReference requestReference;
    RequestName requestName;
    Environment environment;
    Method method;
    Scheme scheme;
    HostAddress host;
    Path path;
    Payload payload;
    DynamicInputData dynamicInputData;
    List<TemplateReferenceObject> templateReferenceObjectList;
    List<ParameterReferenceObject> parameterReferenceObjectList;
    List<HeaderReferenceObject> headerReferenceObjectList;
    JsonRequestObject jsonRequestObject;
    List<TagReferenceObject> tagReferenceObjectList;

    public RequestReferenceObject(DataAccessObject dao) {
        this.dao = dao;
    }

//-----------------------JSON CONSTRUCTOR---------------------------------------
    public RequestReferenceObject(DataAccessObject dao, String json) {
        this.dao = dao;
        setAllEntitiesFromJsonString(json);
    }

    private boolean setAllEntitiesFromJsonString(String json) {
        boolean success;
        JsonRequestObject jro;
        jro = setJsonRequestObjectFromString(json);
        success = setEntitiesFromJsonRequestObject(jro);
        return success;
    }

    public JsonRequestObject setJsonRequestObjectFromString(String json) {
        JsonRequestObject jsonObj = null;
        try {
            jsonObj = new Gson().fromJson(json, JsonRequestObject.class);
        } catch (JsonSyntaxException ex) {
            System.out.println(ex);
        }
        return jsonObj;
    }
////////////////////////////////////////////////////////////////////////////////

//-----------------------REFERENCE CONSTRUCTOR--------------------------------//
    public RequestReferenceObject(DataAccessObject dao, int id) {
        this.dao = dao;
        setAllEntitiesFromRequestID(id);
    }

    private boolean setAllEntitiesFromRequestID(int id) {
        boolean success;
        JsonRequestObject jro;
        jro = setJsonRequestObjectFromDataBank(this.dao, id);
        success = setEntitiesFromJsonRequestObject(jro);
        return success;
    }

    public JsonRequestObject setJsonRequestObjectFromDataBank(DataAccessObject dao, int id) {
        JsonRequestObject jReqObj = new JsonRequestObject();
        RequestReference reqRef = null;
        try {
            reqRef = dao.getRequestReferenceJpaController().findRequestReference(id);
        } catch (Exception ex) {
            System.out.println("failed to retrieve request id from DataBank, " + ex);
        }

        if (reqRef != null) {
            jReqObj.setRequestReference(reqRef.getIdRequestReference());
            jReqObj.setRequestName(dao.getRequestNameJpaController().findRequestName(reqRef.getIdRequestName()).getRequestName());
            jReqObj.setEnvironment(dao.getEnvironmentJpaController().findEnvironment(reqRef.getIdEnvironment()).getEnvironmentName());
            jReqObj.setMethod(dao.getMethodJpaController().findMethod(reqRef.getIdMethod()).getMethodValue());
            jReqObj.setScheme(dao.getSchemeJpaController().findScheme(reqRef.getIdScheme()).getSchemeValue());
            jReqObj.setHost(dao.getHostAddressJpaController().findHostAddress(reqRef.getIdHostAddress()).getHostAddressValue());
            jReqObj.setPath(dao.getPathJpaController().findPath(reqRef.getIdPath()).getPathValue());
            jReqObj.setPayload(dao.getPayloadJpaController().findPayload(reqRef.getIdPayload()).getPayloadValue());

            /*---------------Dynamic generated fields Feature-----------------*/
            DynamicInputData dynaData = dao.getDynamicInputDataJpaController().findByIdRequestReference(reqRef.getIdRequestReference());
            if (dynaData != null) {
                JsonDynamicData jsonDynamicData = new JsonDynamicData(dynaData.getIdDynamicInputData(), dynaData.getIdRequestReference(), dynaData.getRequestType(), dynaData.getJsonRequest());
                jReqObj.setJsonDynamicData(jsonDynamicData);
            } else {
                jReqObj.setJsonDynamicData(null);
            }
            /*----------------------------------------------------------------*/

            for (TemplateReference tr : dao.getTemplateReferece(reqRef)) {
                TemplateReferenceObject templateObject = new TemplateReferenceObject(tr, dao);
                jReqObj.getTemplates().add(templateObject.getTemplate().getTemplateValue());
            }

            for (HeaderReference hr : dao.getHeaderReference(reqRef)) {
                HeaderReferenceObject headerObject = new HeaderReferenceObject(hr, dao);
                JHeader jheader = new JHeader(headerObject.getHeader().getHeaderName(), headerObject.getHeader().getHeaderValue());
                jReqObj.getHeaders().add(jheader);
            }

            for (ParameterReference pr : dao.getParameterReference(reqRef)) {
                ParameterReferenceObject parameterObject;
                JParameter jparameter = null;
                try {
                    parameterObject = new ParameterReferenceObject(pr, dao);
                    jparameter = new JParameter(parameterObject.getParameter().getParameterName(), parameterObject.getParameter().getParameterValue());
                } catch (NamingException ex) {
                    System.out.println("Exception occured while setting parameterObject" + ex);
                    Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
                }
                jReqObj.getParameters().add(jparameter);
            }

            for (RequestTagReference tg : dao.getRequestTagReference(reqRef)) {
                TagReferenceObject tagObject = new TagReferenceObject(tg, dao);
                JsonTag jsonTag = new JsonTag(tagObject.getRequestTag().getIdRequestTag(), tagObject.getRequestTag().getTagValue());
                jReqObj.getJsonTags().add(jsonTag);
            }
        }
        return jReqObj;
    }

////////////////////////////////////////////////////////////////////////////////
//-----------------------REQUEST FILLER--------------------------------//
    public boolean setEntitiesFromJsonRequestObject(JsonRequestObject jsonObj) {
        this.jsonRequestObject = jsonObj;
        this.requestName = new RequestName(0, jsonObj.getRequestName());
        this.environment = new Environment(0, jsonObj.getEnvironment().toUpperCase());
        this.method = new Method(0, jsonObj.getMethod().toUpperCase());
        this.scheme = new Scheme(0, jsonObj.getScheme());
        this.host = new HostAddress(0, jsonObj.getHost());
        this.path = new Path(0, jsonObj.getPath());
        this.payload = new Payload(0, jsonObj.getPayload());

        /*---------------Dynamic generated fields Feature-----------------*/
        if (jsonObj.getJsonDynamicData() != null) {
            JsonDynamicData jdd = jsonObj.getJsonDynamicData();
            this.dynamicInputData = new DynamicInputData(0, jdd.getIdRequestReference(), jdd.getRequestType(), jdd.getJsonRequest());
        }

        this.templateReferenceObjectList = new ArrayList();
        int i = 1;
        for (String template : jsonObj.getTemplates()) {
            if (template.isEmpty() || template.isEmpty()) {
                System.out.println("Empty template value not inserted.");
            } else {
                this.templateReferenceObjectList.add(new TemplateReferenceObject(this.dao, template, i, 1));
            }
            i++;
        }

        this.headerReferenceObjectList = new ArrayList();
        for (JHeader header : jsonObj.getHeaders()) {
            if (header.getName().isEmpty() || header.getValue().isEmpty()) {
                System.out.println("Empty header value not inserted.");
            } else {
                this.headerReferenceObjectList.add(new HeaderReferenceObject(this.dao, header.getName(), header.getValue()));
            }
        }

        this.parameterReferenceObjectList = new ArrayList();
        for (JParameter parameter : jsonObj.getParameters()) {
            if (parameter.getName().isEmpty() || parameter.getValue().isEmpty()) {
                System.out.println("Empty parameter value not inserted.");
            } else {
                this.parameterReferenceObjectList.add(new ParameterReferenceObject(this.dao, parameter.getName(), parameter.getValue(), 1));
            }
        }

        this.tagReferenceObjectList = new ArrayList<>();
        for (JsonTag tag : jsonObj.getJsonTags()) {
            if (tag.getName().isEmpty()) {
                System.out.println("Empty tag value not inserted.");
            } else {
                this.tagReferenceObjectList.add(new TagReferenceObject(this.dao, tag.getName().toUpperCase()));
            }
        }
        return true;
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
                    ScheduledRequestObject scheduledRequestObject = new ScheduledRequestObject(this.dao, oldRequestReferenceId);
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

    public boolean clearReferences(RequestReference requestReference) {
        if (requestReference != null) {
            for (TemplateReference tr : this.dao.getTemplateReferece(requestReference)) {
                new TemplateReferenceObject(tr, this.dao).deleteTemplateReference();
            }
            for (HeaderReference hr : this.dao.getHeaderReference(requestReference)) {
                new HeaderReferenceObject(hr, this.dao).deleteHeaderReference();
            }
            for (ParameterReference pr : this.dao.getParameterReference(requestReference)) {
                try {
                    new ParameterReferenceObject(pr, this.dao).deleteParameterReference();
                } catch (NamingException ex) {
                    Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (RequestTagReference tg : this.dao.getRequestTagReference(requestReference)) {
                new TagReferenceObject(tg, this.dao).deleteRequestTagReference();
            }
            if (this.dao.getDynamicInputDataJpaController().findByIdRequestReference(requestReference.getIdRequestReference()) != null) {
                int idDynamic = this.dao.getDynamicInputDataJpaController().findByIdRequestReference(requestReference.getIdRequestReference()).getIdDynamicInputData();
                try {
                    this.dao.getDynamicInputDataJpaController().destroy(idDynamic);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            
            /*NEW DYNAMIC INPUT DATA*/
            if(this.dynamicInputData!=null){
                persistDynamicInputData(this.requestReference.getIdRequestReference());
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
        JsonRequestObject jro = this.jsonRequestObject;

        if (jro.getJsonDynamicData() != null) {
            DynamicDataObject ddo = new DynamicDataObject(jro);
            jro = ddo.setDynamicallyGeneratedValues();
        }
        if (jro == null) {
            return null;
        } else {
            RestRequester restRequester = new RestRequester();
            restRequester.setMethod(jro.getMethod());
            restRequester.setScheme(jro.getScheme());
            restRequester.setHost(jro.getHost());
            restRequester.setPath(jro.getPath());
            if (jro.getPayload().equals("") == false) {
                restRequester.setEntity(jro.getPayload());
            }
            if (jro.getHeaders().isEmpty() == false) {
                for (JHeader jh : jro.getHeaders()) {
                    restRequester.addHeader(jh.getName(), jh.getValue());
                }
            }
            if (jro.getTemplates().isEmpty() == false) {
                for (String tm : jro.getTemplates()) {
                    restRequester.addTemplate(tm);
                }
            }
            if (jro.getParameters().isEmpty() == false) {
                for (JParameter jp : jro.getParameters()) {
                    restRequester.addParameter(jp.getName(), jp.getValue());
                }
            }
            return restRequester;
        }
    }

//----------------------------GETTERS SETTERS-----------------------------------
    public RequestReference getRequestReference() {
        return this.requestReference;
    }
//------------------------------------------------------------------------------

    public void persistRequestName() {
        try {
            this.requestName = this.dao.getRequestNameJpaController().findOrAdd(this.requestName);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public void persistEnvironment() {
        try {
            this.environment = this.dao.getEnvironmentJpaController().findOrAdd(this.environment);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public void persistMethod() {
        try {
            this.method = this.dao.getMethodJpaController().findOrAdd(this.method);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public void persistScheme() {
        try {
            this.scheme = this.dao.getSchemeJpaController().findOrAdd(this.scheme);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public void persistHost() {
        try {
            this.host = this.dao.getHostAddressJpaController().findOrAdd(this.host);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//------------------------------------------------------------------------------

    public void persistPath() {
        try {
            this.path = this.dao.getPathJpaController().findOrAdd(this.path);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*------------------------------------------------------------------------*/

    public void persistPayload() {
        try {
            this.payload = this.dao.getPayloadJpaController().create(this.payload);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*------------------------------------------------------------------------*/

    public void persistDynamicInputData(int idRequestReference) {
        try {
            this.dynamicInputData.setIdRequestReference(idRequestReference);
            this.dynamicInputData = this.dao.getDynamicInputDataJpaController().create(this.dynamicInputData);
        } catch (Exception ex) {
            Logger.getLogger(RequestReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*------------------------------------------------------------------------*/

    public JsonRequestObject getRequestObject() {
        return this.jsonRequestObject;
    }
}
