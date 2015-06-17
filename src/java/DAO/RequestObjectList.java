/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.ValidationDAO.RequestValidationObject;
import DAO.ValidationDAO.Schedule.ScheduledRequestExecutionLogObject;
import DAO.ValidationDAO.Schedule.ScheduledRequestObject;
import DAO.ValidationDAO.ValidationScenarioObject;
import Entities.ReferenceEntities.RequestReference;
import Entities.Scheduled.ScheduledRequest;
import JsonObjects.JsonRequestObject;
import JsonObjects.Tags.JsonTag;
import com.google.gson.Gson;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author fellippe.mendonca
 */
public class RequestObjectList {

    DataAccessObject dao;
    RequestReferenceObject requestRefObj;
    ValidationScenarioObject validationScenarioObject;
    ScheduledRequestExecutionLogObject scheduledRequestExecutionLogObject;

    public RequestObjectList() throws NamingException {
        this.dao = new DataAccessObject();
        this.requestRefObj = null;
    }

    public RequestReferenceObject setRequest(String json) {
        this.requestRefObj = new RequestReferenceObject(this.dao, json);
        return this.requestRefObj;
    }

    public ValidationScenarioObject setRequestValidation(String json) {
        this.validationScenarioObject = new ValidationScenarioObject(this.dao, json);
        return this.validationScenarioObject;
    }

    /*-########################-CRUD REQUESTS-###############################-*/
    /*--------------RETORNA LISTA COM REQUESTS DA BASE DE DADOS---------------*/
    public String getJsonRequestList() throws NamingException {
        List<JsonRequestObject> requestList = new ArrayList();
        for (RequestReferenceObject rro : getObjectRequestList()) {
            requestList.add(rro.getRequestObject());
        }
        return "{\"requestList\":" + (new Gson().toJson(requestList)) + "}";
    }

    public List<RequestReferenceObject> getObjectRequestList() throws NamingException {
        List<RequestReferenceObject> requestObjectListDB = new ArrayList<>();
        for (RequestReference rr : this.dao.getRequestReferenceJpaController().findRequestReferenceEntities()) {
            RequestReferenceObject requestReferenceObject = null;
            try {
                requestReferenceObject = new RequestReferenceObject(this.dao, rr);
            } catch (NamingException name) {
                System.out.println("Houve um problema ao adquirir dados em um dos requests cadastrados. RequestID:" + rr.getIdRequestReference() + " , " + name);
            }
            if (requestReferenceObject != null) {
                requestObjectListDB.add(new RequestReferenceObject(this.dao, rr));
            }
        }
        return requestObjectListDB;
    }
    /*----------RETORNA LISTA COM SCHEDULED REQUESTS DA BASE DE DADOS---------*/

    public String getJsonScheduledRequestList() throws NamingException {
        List<JsonRequestObject> requestList = new ArrayList();
        for (RequestReferenceObject rro : getScheduledObjectRequestList()) {
            requestList.add(rro.getRequestObject());
        }
        return "{\"requestList\":" + (new Gson().toJson(requestList)) + "}";
    }

    public List<RequestReferenceObject> getScheduledObjectRequestList() throws NamingException {
        List<RequestReferenceObject> requestObjectListDB = new ArrayList<>();
        for (ScheduledRequest rr : this.dao.getScheduledRequestJpaController().findScheduledRequestEntities()) {
            requestObjectListDB.add(new RequestReferenceObject(this.dao, this.dao.getRequestReferenceJpaController().findRequestReference(rr.getIdRequestReference())));
        }
        return requestObjectListDB;
    }

    /*------------------------------------------------------------------------*/
    public boolean createRequest(String json) {
        this.requestRefObj = new RequestReferenceObject(this.dao, json);
        return this.requestRefObj.persistRequestReference();
    }

    public boolean updateRequest(String json) {
        this.requestRefObj = new RequestReferenceObject(this.dao, json);
        return this.requestRefObj.updateRequestReference();
    }

    public boolean deleteRequest(String json) {
        this.requestRefObj = new RequestReferenceObject(this.dao, json);
        return this.requestRefObj.deleteRequestReference();
    }
    /*------------------------------------------------------------------------*/

    /*-########################-CRUD VALIDATION-#############################-*/
    /*--------------RETORNA LISTA COM REQUESTS DA BASE DE DADOS---------------*/
    public String getJsonRequestValidations(int requestId) throws NamingException {
        RequestValidationObject requestValidationObject = new RequestValidationObject(this.dao, requestId);
        return new Gson().toJson(requestValidationObject.getJsonRequestValidation());
    }

    public String getJsonRequestValidationsExecuted(int requestId) throws NamingException {
        RequestValidationObject requestValidationObject = new RequestValidationObject(this.dao, requestId);
        return new Gson().toJson(requestValidationObject.getJsonRequestValidationExecuted());
    }

    public boolean createValidation(String json) throws Exception {
        this.validationScenarioObject = new ValidationScenarioObject(this.dao, json);
        return this.validationScenarioObject.persistScenario();
    }

    public boolean updateValidation(String json) throws Exception {
        this.validationScenarioObject = new ValidationScenarioObject(this.dao, json);
        return this.validationScenarioObject.updateScenario();
    }

    public boolean deleteValidation(String json) throws Exception {
        this.validationScenarioObject = new ValidationScenarioObject(this.dao, json);
        return this.validationScenarioObject.deleteScenario();
    }
    /*-######################################################################-*/

    /*-########################-CRUD SCHEDULE VALIDATION-####################-*/
    /*------------------------------------------------------------------------*/
    public boolean createScheduledRequest(int idRequestReference) throws Exception {
        if (this.dao.getRequestReferenceJpaController().findRequestReference(idRequestReference) != null) {
            ScheduledRequestObject scheduledRequestObject = new ScheduledRequestObject(this.dao, idRequestReference);
            return scheduledRequestObject.persistSchedule();
        } else {
            System.out.println("Request não encontrado na base");
            return false;
        }
    }

    public boolean deleteScheduledRequest(int idRequestReference) throws Exception {
        if (this.dao.getRequestReferenceJpaController().findRequestReference(idRequestReference) != null) {
            ScheduledRequestObject scheduledRequestObject = new ScheduledRequestObject(this.dao, idRequestReference);
            return scheduledRequestObject.deleteSchedule();
        } else {
            System.out.println("Request não encontrado na base");
            return false;
        }
    }
    /*------------------------------------------------------------------------*/

    public boolean executeAllValidations() {
        this.scheduledRequestExecutionLogObject = new ScheduledRequestExecutionLogObject(this.dao);
        return this.scheduledRequestExecutionLogObject.executeRequestsValidationAndGenerateLog();
    }

    public String getDailyRequestValidationLog(int idRequestReference, String executionDate) throws ParseException {
        this.scheduledRequestExecutionLogObject = new ScheduledRequestExecutionLogObject(this.dao);
        return new Gson().toJson(this.scheduledRequestExecutionLogObject.getDailyRequestValidationLog(idRequestReference, executionDate));
    }

    /*-######################################################################-*/
    /*-------------------METODOS DE LISTAGEM DE PARAMETROS--------------------*/
    //Methods
    public List<String> listMethodsFromDB() {
        return this.dao.getMethodJpaController().listMethodEntities();
    }

    //Environments
    public List<String> listEnvironmentsFromDB() {
        return this.dao.getEnvironmentJpaController().listEnvironmentEntities();
    }

    //Schemes
    public List<String> listSchemesFromDB() {
        return this.dao.getSchemeJpaController().listSchemeEntities();
    }

    //Hosts
    public List<String> listHostsFromDB() {
        return this.dao.getHostAddressJpaController().listHostAddressEntities();
    }

    //Paths
    public List<String> listPathsFromDB() {
        return this.dao.getPathJpaController().listPathEntities();
    }

    //Payloads
    public List<String> listPayloadsFromDB() {
        return this.dao.getPayloadJpaController().listPayloadEntities();
    }

    //Templates
    public List<String> listTemplatesFromDB() {
        return this.dao.getTemplateJpaController().listTemplateEntities();
    }

    //Parameters
    public List<String> listParameterNamesFromDB() {
        return this.dao.getParameterJpaController().listParameterNameEntities();
    }

    public List<String> listParameterValuesFromDB() {
        return this.dao.getParameterJpaController().listParameterValueEntities();
    }

    //Headers
    public List<String> listHeaderNamesFromDB() {
        return this.dao.getHeaderJpaController().listHeaderNameEntities();
    }

    public List<String> listHeaderValuesFromDB() {
        return this.dao.getHeaderJpaController().listHeaderValueEntities();
    }

    //Tags
    public List<JsonTag> listJsonTagValuesFromDB() {
        return this.dao.getRequestTagJpaController().listJsonTagEntities();
    }

    public List<String> listTagValuesFromDB() {
        return this.dao.getRequestTagJpaController().listRequestTagEntities();
    }
    /*------------------------------------------------------------------------*/
}
