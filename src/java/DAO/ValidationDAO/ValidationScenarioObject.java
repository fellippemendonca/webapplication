/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.ValidationDAO;

import DAO.DataAccessObject;
import DAO.RequestReferenceObject;
import Entities.ValidationEntities.ValidationElement;
import Entities.ValidationEntities.ValidationOperation;
import Entities.ValidationEntities.ValidationScenario;
import HttpConnections.ResponseContents;
import HttpConnections.RestRequester;
import JsonObjects.Validation.JsonValidationElement;
import JsonObjects.Validation.JsonValidationOperation;
import JsonObjects.Validation.JsonValidationScenario;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.SOAPException;
import jpa.ValidationJPA.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class ValidationScenarioObject {

    DataAccessObject dao;
    JsonValidationScenario jsonValidationScenario;
    ValidationScenario validationScenario;
    List<ValidationElement> validationElementList;

    //Inicializa Scenario de validação existente na base
    public ValidationScenarioObject(DataAccessObject dao, ValidationScenario validationScenario) {
        //inicializa o data access object necessario para operaçoes CRUD
        this.dao = dao;

        //seta referencia para entidade ValidationScenario
        this.validationScenario = validationScenario;

        //Inicializa array JsonValidationElement
        List<JsonValidationElement> JsonValidationElementList = new ArrayList();

        //Busca todas as entidades ValidationElement relacionadas ao ValidationScenario em questão
        for (ValidationElement validationElement : this.dao.getValidationElementJpaController().findByIdValidationScenario(this.validationScenario.getIdValidationScenario())) {

            //Inicializa entidade ValidationOperation
            ValidationOperation validationOperation = this.dao.getValidationOperationJpaController().findValidationOperation(validationElement.getIdValidationOperation());

            //Inicializa JsonValidationOperation que será inserido no JsonValidationElement
            JsonValidationOperation jsonValidationOperation = new JsonValidationOperation(validationOperation.getIdValidationOperation(), validationOperation.getName(), validationOperation.getDescription());

            //Inicializa JsonValidationElement
            JsonValidationElement jsonValidationElement = new JsonValidationElement(validationElement.getIdResponseValidationElement(), validationElement.getIdValidationScenario(), jsonValidationOperation, validationElement.getExpectedObject(), false);

            //Insere JsonValidationElement no array  
            JsonValidationElementList.add(jsonValidationElement);
        }
        //Inicializa o JsonValidationScenario com todos os elementos Json gerados com base nas Entities
        this.jsonValidationScenario = new JsonValidationScenario(this.validationScenario.getIdValidationScenario(), this.validationScenario.getIdRequestReference(), this.validationScenario.getRequestObject(), this.validationScenario.getValidationScenarioDescription(), JsonValidationElementList, false, null);
    }

    //Inicializa Scenario de validação
    public ValidationScenarioObject(DataAccessObject dao, String json) {
        //inicializa o data access object necessario para operaçoes CRUD
        this.dao = dao;

        //Converte o Json Scenario recebido em Objeto JsonValidationScenario;
        this.jsonValidationScenario = new Gson().fromJson(json, JsonValidationScenario.class);
    }

    public boolean deleteScenario() {
        boolean success = false;
        try {
            for (ValidationElement validationElement : this.dao.getValidationElementJpaController().findByIdValidationScenario(getJsonValidationScenario().getIdValidationScenario())) {
                this.dao.getValidationElementJpaController().destroy(validationElement.getIdResponseValidationElement());
            }
            this.dao.getValidationScenarioJpaController().destroy(getJsonValidationScenario().getIdValidationScenario());
            success = true;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ValidationScenarioObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ValidationScenarioObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean updateScenario() {
        boolean success = false;
        try {
            for (ValidationElement validationElement : this.dao.getValidationElementJpaController().findByIdValidationScenario(getJsonValidationScenario().getIdValidationScenario())) {
                this.dao.getValidationElementJpaController().destroy(validationElement.getIdResponseValidationElement());
            }
            this.dao.getValidationScenarioJpaController().destroy(getJsonValidationScenario().getIdValidationScenario());
            success = persistScenario();
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ValidationScenarioObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ValidationScenarioObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public JsonValidationScenario getJsonValidationScenario() {
        return jsonValidationScenario;
    }

    //Persiste cenario Inicializado pelo jsonValidationScenario
    public boolean persistScenario() throws RollbackFailureException, Exception {
        //inicializa a entity ValidationScenario
        this.validationScenario = new ValidationScenario(0, this.jsonValidationScenario.getIdRequestReference(), this.jsonValidationScenario.getRequestObject());
        this.validationScenario.setValidationScenarioDescription(this.jsonValidationScenario.getValidationScenarioDescription());

        //Persiste a entity ValidationScenario em DB
        this.dao.getValidationScenarioJpaController().create(this.validationScenario);

        //Atualiza Json Atual com o ID recebido do banco.
        this.jsonValidationScenario.setIdValidationScenario(this.validationScenario.getIdValidationScenario());

        //Inicializa array de ValidationElements
        this.validationElementList = new ArrayList();
        for (JsonValidationElement element : this.jsonValidationScenario.getJsonValidationElementList()) {

            //Busca em banco qual foi a Operação efetuada e instancia a entidade.      
            ValidationOperation validationOperation = dao.getValidationOperationJpaController().find(new ValidationOperation(0, element.getJsonValidationOperation().getName()));

            //Instancia nova entidade ValidationElement
            ValidationElement validationElement = new ValidationElement(0, this.validationScenario.getIdValidationScenario(), validationOperation.getIdValidationOperation(), element.getExpectedObject());

            //Persiste nova entidade no Banco
            this.dao.getValidationElementJpaController().create(validationElement);

            //Atualiza o id de seu Element correspondente no Json 
            element.setIdResponseValidationElement(validationElement.getIdResponseValidationElement());

            //Adiciona ValidationElement ao Array
            this.validationElementList.add(validationElement);
        }

        //se Ids foram gerados com sucesso retorna jsonValidationScenario atualizado com os IDs recém gerados no Banco.
        return this.jsonValidationScenario.getIdValidationScenario() != 0;
    }

    //Executa request, valida e retorna cenarios com status de cada elemento testado.
    public JsonValidationScenario executeScenarioValidation() {
        boolean success;
        this.jsonValidationScenario.setSuccess(true);
        RequestReferenceObject requestRefObj = new RequestReferenceObject(this.dao, this.jsonValidationScenario.getRequestObject());

        RestRequester restRequester = requestRefObj.generateRestRequester();
        if (restRequester == null) {
            String errorMessage = "The query used to fill dynamic parameters returned no results and the request was not able to be generated and then executed.";
            ResponseContents rc = new ResponseContents();
            rc.setRequest("Not generated.");
            rc.setStatus("Not retrieved.");
            rc.setEndDate();
            rc.setContents(errorMessage);
            System.out.println(errorMessage);
            this.jsonValidationScenario.setResponseContents(rc);
            this.jsonValidationScenario.setSuccess(false);
            return this.jsonValidationScenario;
        } else {
            ResponseContents rc;
            System.out.println("\nExecuting Validation of RequestId:" + this.jsonValidationScenario.getIdRequestReference());
            try {
                rc = restRequester.Request();
                this.jsonValidationScenario.setResponseContents(rc);
                for (JsonValidationElement element : this.jsonValidationScenario.getJsonValidationElementList()) {

                    ValidationOperationSource operation = new ValidationOperationSource();
                    String operationName = element.getJsonValidationOperation().getName();
                    String expected = element.getExpectedObject();
                    success = operation.validateElement(operationName, expected, rc);
                    element.setSuccess(success);
                    if (success == false) {
                        this.jsonValidationScenario.setSuccess(success);
                    }
                }
                System.out.println("Status:" + this.jsonValidationScenario.isSuccess());
                return this.jsonValidationScenario;
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(ValidationScenarioObject.class.getName()).log(Level.SEVERE, null, ex);
                this.jsonValidationScenario.setSuccess(false);
            }
            return null;
        }
    }
}
