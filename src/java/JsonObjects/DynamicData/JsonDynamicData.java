/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.DynamicData;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonDynamicData {
    int idDynamicInputData;
    int idRequestReference;
    String requestType;
    String jsonRequest;

    public JsonDynamicData(int idDynamicInputData, int idRequestReference, String requestType, String jsonRequest) {
        this.idDynamicInputData = idDynamicInputData;
        this.idRequestReference = idRequestReference;
        this.requestType = requestType;
        this.jsonRequest = jsonRequest;
    }

    public int getIdDynamicInputData() {
        return idDynamicInputData;
    }

    public void setIdDynamicInputData(int idDynamicInputData) {
        this.idDynamicInputData = idDynamicInputData;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getJsonRequest() {
        return jsonRequest;
    }

    public void setJsonRequest(String jsonRequest) {
        this.jsonRequest = jsonRequest;
    }

    

}
