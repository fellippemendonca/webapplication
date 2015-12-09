/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.ValidationDAO;

import DAO.DataAccessObject;
import Entities.ValidationEntities.ValidationScenario;
import JsonObjects.Validation.JsonRequestValidation;
import JsonObjects.Validation.JsonValidationScenario;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.SOAPException;

/**
 *
 * @author fellippe.mendonca
 */
public class RequestValidationObject {

    int idRequestReference;
    List<ValidationScenarioObject> validationScenarioObjectList;
    DataAccessObject dao;
    JsonRequestValidation jsonRequestValidation;

    public RequestValidationObject(DataAccessObject dao, int idRequestReference) {
        this.dao = dao;
        this.idRequestReference = idRequestReference;

        this.validationScenarioObjectList = new ArrayList();
        List<JsonValidationScenario> jsonValidationScenarioList = new ArrayList();
        for (ValidationScenario validationScenario : this.dao.getValidationScenarioJpaController().findByIdRequestReference(this.idRequestReference)) {
            ValidationScenarioObject validationScenarioObject = new ValidationScenarioObject(this.dao, validationScenario);
            this.validationScenarioObjectList.add(validationScenarioObject);
            jsonValidationScenarioList.add(validationScenarioObject.getJsonValidationScenario());
        }
        this.jsonRequestValidation = new JsonRequestValidation(this.idRequestReference, jsonValidationScenarioList, false);
    }

    public JsonRequestValidation getJsonRequestValidation() {
        return jsonRequestValidation;
    }

    public JsonRequestValidation getJsonRequestValidationExecuted() {
        boolean success = true;
        List<JsonValidationScenario> jsonValidationScenarioList = new ArrayList();
        for (ValidationScenarioObject validationScenarioObject : this.validationScenarioObjectList) {
            if (validationScenarioObject.executeScenarioValidation().isSuccess()) {
            } else {
                success = false;
            }
            jsonValidationScenarioList.add(validationScenarioObject.getJsonValidationScenario());
        }
        this.jsonRequestValidation = new JsonRequestValidation(this.idRequestReference, jsonValidationScenarioList, success);
        return this.jsonRequestValidation;
    }

}
