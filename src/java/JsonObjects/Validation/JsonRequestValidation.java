/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.Validation;

import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonRequestValidation {
    int idRequestReference;
    List<JsonValidationScenario> jsonValidationScenarioList;
    Boolean success;

    public JsonRequestValidation(int idRequestReference, List<JsonValidationScenario> jsonValidationScenarioList, Boolean success) {
        this.idRequestReference = idRequestReference;
        this.jsonValidationScenarioList = jsonValidationScenarioList;
        this.success = success;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public List<JsonValidationScenario> getJsonValidationScenarioList() {
        return jsonValidationScenarioList;
    }

    public void setJsonValidationScenarioList(List<JsonValidationScenario> jsonValidationScenarioList) {
        this.jsonValidationScenarioList = jsonValidationScenarioList;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
