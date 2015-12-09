/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoScenarios.openApi.v1.sellerItems;
import DAO.RequestObjectList;
import HttpConnections.ResponseContents;
import HttpConnections.RestRequester;
import JsonObjects.Validation.JsonValidationScenario;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import javax.naming.NamingException;
/**
 *
 * @author fellippe.mendonca
 */
public class AutoScenario implements Serializable {
    
    /*-----VVVVVVVVVV Funções CREATE-RETRIEVE-UPDATE-DELETE VVVVVVVVVV--------*/
    
    /*-------------------------CRUD SIMPLE REQUESTS---------------------------*/
    
    /*Retorna a lista de requests existentes no banco de dados*/
    public String getRequest() throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonRequestList();
    }
    
    /*Função responsável por Instanciar Popular e Executar o request fornecido pelo site*/
    public ResponseContents execRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        RestRequester restRequester = rob.setRequest(json).generateRestRequester();
        if(restRequester == null){
            String errorMessage = "The query used to fill dynamic parameters returned no results and the request was not able to be generated and then executed.";
            ResponseContents rc = new ResponseContents();
            rc.setRequest("Not generated.");
            rc.setStatus("Not retrieved.");
            rc.setExecTime(0);
            rc.setEndDate();
            rc.setContents(errorMessage);
            System.out.println(errorMessage);
            return rc;
        } else {
            /* THREADED EXECUTION */
            if(rob.getRequestRefObj().getThreadNumber()>0){
                return restRequester.ThreadedRequest(rob.getRequestRefObj().getThreadNumber());
            } else {
                return restRequester.Request();
            }
            /* THREADED EXECUTION */
        }
    }
    
    public boolean createRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.createRequest(json);
    }
    
    public boolean updateRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.updateRequest(json);
    }
    
    public boolean removeRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.deleteRequest(json);
    }
    
    
    /*-------------------------CRUD VALIDATION SCENARIOS----------------------*/
    
    /*Função responsável por Instanciar Popular e Executar o request fornecido pelo site juntamente com sua validação*/
    public JsonValidationScenario execValidation(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.setRequestValidation(json).executeScenarioValidation();
    }
    
    public String getRequestValidation(int requestID) throws NamingException, IOException, URISyntaxException, Exception {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonRequestValidations(requestID);
    }
    
    public String getRequestValidationExecuted(int requestID) throws NamingException, IOException, URISyntaxException, Exception {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonRequestValidationsExecuted(requestID);
    }
    
    public boolean createValidation(String json) throws NamingException, IOException, URISyntaxException, Exception {
        RequestObjectList rob = new RequestObjectList();
        return rob.createValidation(json);
    }
    
    public boolean updateValidation(String json) throws NamingException, IOException, URISyntaxException, Exception {
        RequestObjectList rob = new RequestObjectList();
        return rob.updateValidation(json);
    }
    
    public boolean deleteValidation(String json) throws NamingException, IOException, URISyntaxException, Exception {
        RequestObjectList rob = new RequestObjectList();
        return rob.deleteValidation(json);
    }
    
    /*-------------------------CRUD SCHEDULED VALIDATION SCENARIOS----------------------*/
    public boolean createScheduledRequest(int id) throws NamingException, IOException, URISyntaxException, Exception {
        RequestObjectList rob = new RequestObjectList();
        return rob.createScheduledRequest(id);
    }
    
    public boolean removeScheduledRequest(int id) throws NamingException, IOException, URISyntaxException, Exception {
        RequestObjectList rob = new RequestObjectList();
        return rob.deleteScheduledRequest(id);
    }
    
    public String getScheduledRequest() throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonScheduledRequestList();
    }
    
    public boolean executeScheduledValidations() throws NamingException, IOException, URISyntaxException, Exception {
        RequestObjectList rob = new RequestObjectList();
        return rob.executeAllValidations();
    }
    
    public String getDailyRequestValidationLog(int idRequestReference, String executionDate) throws NamingException, ParseException {
        RequestObjectList rob = new RequestObjectList();
        return rob.getDailyRequestValidationLog(idRequestReference, executionDate);
    }
    
    
    /*QUERY REPORTS CRUD*/
    
    public boolean createQueryReport(String json) throws NamingException{
        RequestObjectList rob = new RequestObjectList();
        return rob.createQueryReport(json);
    }
    
    public boolean updateQueryReport(String json) throws NamingException{
        RequestObjectList rob = new RequestObjectList();
        return rob.updateQueryReport(json);
    }
    
    public boolean removeQueryReport(String json) throws NamingException{
        RequestObjectList rob = new RequestObjectList();
        return rob.removeQueryReport(json);
    }
    
    public String getJsonQueryReportList() throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonQueryReportList();
    }
    
    public String getJsonQueryReportChart(int queryId, String since) throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonQueryReportChart(queryId, since);
    }
    
   
}
