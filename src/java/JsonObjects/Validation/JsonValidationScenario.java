/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JsonObjects.Validation;

import HttpConnections.ResponseContents;
import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonValidationScenario {

    int idValidationScenario;
    int idRequestReference;
    String requestObject;
    String validationScenarioDescription;
    List<JsonValidationElement> jsonValidationElementList;
    Boolean success;
    ResponseContents responseContents;

    public JsonValidationScenario(int idValidationScenario, int idRequestReference, String requestObject, String validationScenarioDescription, List<JsonValidationElement> jsonValidationElementList, Boolean success, ResponseContents responseContents) {
        this.idValidationScenario = idValidationScenario;
        this.idRequestReference = idRequestReference;
        this.requestObject = requestObject;
        this.validationScenarioDescription = validationScenarioDescription;
        this.jsonValidationElementList = jsonValidationElementList;
        this.success = success;
        this.responseContents = responseContents;
    }

    public int getIdValidationScenario() {
        return idValidationScenario;
    }

    public void setIdValidationScenario(int idValidationScenario) {
        this.idValidationScenario = idValidationScenario;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public String getRequestObject() {
        return requestObject;
    }

    public void setRequestObject(String requestObject) {
        this.requestObject = requestObject;
    }

    public String getValidationScenarioDescription() {
        return validationScenarioDescription;
    }

    public void setValidationScenarioDescription(String validationScenarioDescription) {
        this.validationScenarioDescription = validationScenarioDescription;
    }

    public List<JsonValidationElement> getJsonValidationElementList() {
        return jsonValidationElementList;
    }

    public void setJsonValidationElementList(List<JsonValidationElement> jsonValidationElementList) {
        this.jsonValidationElementList = jsonValidationElementList;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ResponseContents getResponseContents() {
        return responseContents;
    }

    public void setResponseContents(ResponseContents responseContents) {
        this.responseContents = responseContents;
    }
}
