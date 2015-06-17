/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.Validation;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonValidationElement {
    int idResponseValidationElement;
    int idValidationScenario;
    JsonValidationOperation jsonValidationOperation;
    String expectedObject;
    Boolean success;

    public JsonValidationElement(int idResponseValidationElement, int idValidationScenario, JsonValidationOperation jsonValidationOperation, String expectedObject, Boolean success) {
        this.idResponseValidationElement = idResponseValidationElement;
        this.idValidationScenario = idValidationScenario;
        this.jsonValidationOperation = jsonValidationOperation;
        this.expectedObject = expectedObject;
        this.success = success;
    }

    public int getIdResponseValidationElement() {
        return idResponseValidationElement;
    }

    public void setIdResponseValidationElement(int idResponseValidationElement) {
        this.idResponseValidationElement = idResponseValidationElement;
    }

    public int getIdValidationScenario() {
        return idValidationScenario;
    }

    public void setIdValidationScenario(int idValidationScenario) {
        this.idValidationScenario = idValidationScenario;
    }

    public JsonValidationOperation getJsonValidationOperation() {
        return jsonValidationOperation;
    }

    public void setJsonValidationOperation(JsonValidationOperation jsonValidationOperation) {
        this.jsonValidationOperation = jsonValidationOperation;
    }

    public String getExpectedObject() {
        return expectedObject;
    }

    public void setExpectedObject(String expectedObject) {
        this.expectedObject = expectedObject;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
