/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.QueryReportObject.QueryReportObject;
import DAO.ValidationDAO.RequestValidationObject;
import DAO.ValidationDAO.Schedule.ScheduledRequestExecutionLogObject;
import DAO.ValidationDAO.Schedule.ScheduledRequestObject;
import DAO.ValidationDAO.ValidationScenarioObject;
import Entities.QueryReportEntities.QueryReport;
import Entities.ReferenceEntities.RequestReference;
import Entities.Scheduled.ScheduledRequest;
import Entities.ValueEntities.Environment;
import Entities.ValueEntities.HostAddress;
import Entities.ValueEntities.Path;
import JsonObjects.JsonRequestObject;
import JsonObjects.QueryReport.JsonQueryReport;
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

    DataAccessObject dao;  //Unique Data Access Object by Request
    RequestReferenceObject requestRefObj; // Request Reference Object
    ValidationScenarioObject validationScenarioObject; // Validation Scenario Object
    ScheduledRequestExecutionLogObject scheduledRequestExecutionLogObject; // Scheduled Request Execution Log 

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
        return new Gson().toJson(requestList);
    }

    public List<RequestReferenceObject> getObjectRequestList() throws NamingException {
        List<RequestReferenceObject> requestObjectListDB = new ArrayList<>();
        for (RequestReference rr : this.dao.getRequestReferenceJpaController().findRequestReferenceEntities()) {
            requestObjectListDB.add(new RequestReferenceObject(this.dao, rr.getIdRequestReference()));
        }
        return requestObjectListDB;
    }
    /*----------RETORNA LISTA COM SCHEDULED REQUESTS DA BASE DE DADOS---------*/

    public String getJsonScheduledRequestList() throws NamingException {
        List<JsonRequestObject> requestList = new ArrayList();
        for (RequestReferenceObject rro : getScheduledObjectRequestList()) {
            requestList.add(rro.getRequestObject());
        }
        return new Gson().toJson(requestList);
    }

    public List<RequestReferenceObject> getScheduledObjectRequestList() throws NamingException {
        List<RequestReferenceObject> requestObjectListDB = new ArrayList<>();
        for (ScheduledRequest rr : this.dao.getScheduledRequestJpaController().findScheduledRequestEntities()) {
            requestObjectListDB.add(new RequestReferenceObject(this.dao, rr.getIdRequestReference()));
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
    
    
    /*------------------------------------------------------------------------*/
    
    public String getJsonQueryReportList(){
        List<JsonQueryReport> JsonQueryReportList = new ArrayList();
        for(QueryReport queryReport : this.dao.getQueryReportJpaController().findQueryReportEntities()){
            QueryReportObject queryReportObject = new QueryReportObject(this.dao, queryReport.getId());
            JsonQueryReportList.add(queryReportObject.getJsonQueryReport());
        }
        return new Gson().toJson(JsonQueryReportList);
    }
    
    public String getJsonQueryReportChart(int queryId, String since){
        QueryReportObject queryReportObject = new QueryReportObject(this.dao);
        return queryReportObject.retrieveGoogleChartMatrixFromLog(queryId,since);
    }
    
    
    public boolean createQueryReport(String json) {
        QueryReportObject queryReportObject = new QueryReportObject(this.dao, json);
        return queryReportObject.createQueryReport();
    }

    public boolean updateQueryReport(String json) {
        QueryReportObject queryReportObject = new QueryReportObject(this.dao, json);
        return queryReportObject.updateQueryReport();
    }

    public boolean removeQueryReport(String json) {
        QueryReportObject queryReportObject = new QueryReportObject(this.dao, json);
        return queryReportObject.removeQueryReport();
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
            System.out.println("Request ID " + idRequestReference + " não encontrado na base");
            return false;
        }
    }

    public boolean deleteScheduledRequest(int idRequestReference) throws Exception {
        if (this.dao.getRequestReferenceJpaController().findRequestReference(idRequestReference) != null) {
            ScheduledRequestObject scheduledRequestObject = new ScheduledRequestObject(this.dao, idRequestReference);
            return scheduledRequestObject.deleteSchedule();
        } else {
            System.out.println("Request ID " + idRequestReference + " não encontrado na base");
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
    //------------------------Methods-------------------------------------------
    public List<String> listMethodsFromDB() {
        return this.dao.getMethodJpaController().listMethodEntities();
    }

    //------------------------Environments--------------------------------------
    public List<String> listEnvironmentsFromDB() {
        return this.dao.getEnvironmentJpaController().listEnvironmentEntities();
    }

    //------------------------Schemes-------------------------------------------
    public List<String> listSchemesFromDB() {
        return this.dao.getSchemeJpaController().listSchemeEntities();
    }

    //------------------------Hosts---------------------------------------------
    public List<String> listHostsFromDB() {
        return this.dao.getHostAddressJpaController().listHostAddressEntities();
    }

    //------------------------Paths---------------------------------------------
    public List<String> listPathsFromDB() {
        return this.dao.getPathJpaController().listPathEntities();
    }

    //------------------------Templates-----------------------------------------
    public List<String> listTemplatesFromDB() {
        return this.dao.getTemplateJpaController().listTemplateEntities();
    }

    //------------------------Parameters----------------------------------------
    public List<String> listParameterNamesFromDB() {
        return this.dao.getParameterJpaController().listParameterNameEntities();
    }
    public List<String> listParameterValuesFromDB() {
        return this.dao.getParameterJpaController().listParameterValueEntities();
    }
    //--------------------------------------------------------------------------

    //------------------------Headers-------------------------------------------
    public List<String> listHeaderNamesFromDB() {
        return this.dao.getHeaderJpaController().listHeaderNameEntities();
    }
    public List<String> listHeaderValuesFromDB() {
        return this.dao.getHeaderJpaController().listHeaderValueEntities();
    }

    //------------------------Tags----------------------------------------------
    public List<JsonTag> listJsonTagValuesFromDB() {
        return this.dao.getRequestTagJpaController().listJsonTagEntities();
    }
    public List<String> listTagValuesFromDB() {
        return this.dao.getRequestTagJpaController().listRequestTagEntities();
    }
    public List<String> listQueryTagValuesFromDB() {
        return this.dao.getQueryTagJpaController().listQueryTagEntities();
    }

    /*-----------------------------NEW-Version--------------------------------*/
    //------------------------Hosts---------------------------------------------
    public List<String> listHostsBasedOnEnvironment(String env) {
        Environment environment = null;
        try {
            environment = this.dao.getEnvironmentJpaController().find(new Environment(0, env));
        } catch (Exception e) {
            System.out.println("Problem while retrieving environment " + env + " from database. Message: " + e);
        }
        if (environment != null) {
            return this.dao.getHostAddressJpaController().listEntitiesBasedOnCriteria(environment.getEnvironmentName());
        } else {
            return this.dao.getHostAddressJpaController().listHostAddressEntities();
        }

    }

    //------------------------Paths---------------------------------------------
    public List<String> listPathsBasedOnHost(String host) {
        HostAddress hostAddress = null;
        try {
            hostAddress = this.dao.getHostAddressJpaController().find(new HostAddress(0, host));
        } catch (Exception e) {
            System.out.println("Problem while retrieving HostAddress " + host + " from database. Message: " + e);
        }
        if (hostAddress != null) {
            /*If host exists on database then the filter will retrieve all related paths*/
            return this.dao.getPathJpaController().listEntitiesBasedOnCriteria(hostAddress.getHostAddressValue());
        } else {
            /*If host not exists on database then the filter will retrieve all paths*/
            return this.dao.getPathJpaController().listPathEntities();
        }
    }

    //------------------------Templates-----------------------------------------
    public List<String> listTemplatesBasedOnPath(String host) {
        Path path = null;
        try {
            path = this.dao.getPathJpaController().find(new Path(0, host));
        } catch (Exception e) {
            System.out.println("Problem while retrieving Hosts " + host + " from database. Message: " + e);
        }
        if (path != null) {
            /*If host exists on database then the filter will retrieve all related templates*/
            return this.dao.getTemplateJpaController().listEntitiesBasedOnCriteria(path.getPathValue());
        } else {
            /*If host not exists on database then the filter will retrieve all templates*/
            return this.dao.getTemplateJpaController().listTemplateEntities();
        }

    }

    //------------------------Headers-------------------------------------------
    public List<String> listHeaderNamesBasedOnHost(String host) {
        HostAddress hostAddress = null;
        try {
            hostAddress = this.dao.getHostAddressJpaController().find(new HostAddress(0, host));
        } catch (Exception e) {
            System.out.println("Problem while retrieving Hosts " + host + " from database. Message: " + e);
        }
        if (hostAddress != null) {
            /*If host exists on database then the filter will retrieve all related Headers*/
            return this.dao.getHeaderJpaController().listEntitiesBasedOnCriteria(hostAddress.getHostAddressValue());
        } else {
            /*If host not exists on database then the filter will retrieve all Headers*/
            return this.dao.getHeaderJpaController().listHeaderNameEntities();
        }
    }

    public List<String> listHeaderValuesBasedOnHeaderName(String headerName) {
        List<String> list = this.dao.getHeaderJpaController().listValuesBasedOnName(headerName);
        if (list.size() > 0) {
            return list;
        } else {
            return this.dao.getHeaderJpaController().listHeaderValueEntities();
        }
    }

    //------------------------Parameters----------------------------------------
    public List<String> listParameterNamesBasedOnHost(String host) {
        HostAddress hostAddress = null;
        try {
            hostAddress = this.dao.getHostAddressJpaController().find(new HostAddress(0, host));
        } catch (Exception e) {
            System.out.println("Problem while retrieving Hosts " + host + " from database. Message: " + e);
        }
        if (hostAddress != null) {
            /*If host exists on database then the filter will retrieve all related Parameters*/
            return this.dao.getParameterJpaController().listEntitiesBasedOnCriteria(hostAddress.getHostAddressValue());
        } else {
            /*If host not exists on database then the filter will retrieve all Parameters*/
            return this.dao.getParameterJpaController().listParameterNameEntities();
        }
    }

    public List<String> listParameterValuesBasedOnParameterName(String parameterName) {
        List<String> list = this.dao.getParameterJpaController().listValuesBasedOnName(parameterName);
        if (list.size() > 0) {
            return list;
        } else {
            return this.dao.getParameterJpaController().listParameterValueEntities();
        }
    }

    /* GET RequestReferenceObject */
    public RequestReferenceObject getRequestRefObj() {
        return this.requestRefObj;
    }

}
