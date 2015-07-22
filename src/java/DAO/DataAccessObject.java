/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.ReferenceEntities.HeaderReference;
import Entities.ReferenceEntities.ParameterReference;
import Entities.ReferenceEntities.RequestReference;
import Entities.ReferenceEntities.RequestTagReference;
import Entities.ReferenceEntities.TemplateReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpa.DatabaseSelectJpaController;
import jpa.DynamicInputDataJpaController;
import jpa.EnvironmentJpaController;
import jpa.HeaderJpaController;
import jpa.HostAddressJpaController;
import jpa.MethodJpaController;
import jpa.ParameterJpaController;
import jpa.PathJpaController;
import jpa.PayloadJpaController;
import jpa.ReferenceJpaControllers.HeaderReferenceJpaController;
import jpa.ReferenceJpaControllers.ParameterReferenceJpaController;
import jpa.ReferenceJpaControllers.RequestReferenceJpaController;
import jpa.ReferenceJpaControllers.RequestTagReferenceJpaController;
import jpa.ReferenceJpaControllers.TemplateReferenceJpaController;
import jpa.RequestNameJpaController;
import jpa.RequestTagJpaController;
import jpa.Scheduled.ScheduledRequestExecutionLogJpaController;
import jpa.Scheduled.ScheduledRequestJpaController;
import jpa.SchemeJpaController;
import jpa.StoreJpaController;
import jpa.TemplateJpaController;
import jpa.ValidationJPA.ValidationElementJpaController;
import jpa.ValidationJPA.ValidationOperationJpaController;
import jpa.ValidationJPA.ValidationScenarioJpaController;

/**
 *
 * @author fellippe.mendonca
 */



public class DataAccessObject implements Serializable{

    private final EntityManagerFactory emf;
    
    /*Atributos simples dos requests*/
    RequestReferenceJpaController requestReferenceJpaController;
    RequestNameJpaController requestNameJpaController;
    MethodJpaController methodJpaController;
    EnvironmentJpaController environmentJpaController;
    StoreJpaController storeJpaController;
    SchemeJpaController schemeJpaController;
    HostAddressJpaController hostAddressJpaController;
    PathJpaController pathJpaController;
    PayloadJpaController payloadJpaController;
    
    /*Atributos Referenciados dos requests*/
    TemplateReferenceJpaController templateReferenceJpaController;
    TemplateJpaController templateJpaController;
    
    HeaderReferenceJpaController headerReferenceJpaController;
    HeaderJpaController headerJpaController;
    
    ParameterReferenceJpaController parameterReferenceJpaController;
    ParameterJpaController parameterJpaController;
    
    DatabaseSelectJpaController databaseSelectJpaController;
    
    DynamicInputDataJpaController dynamicInputDataJpaController;
    
    /*Tags Associadas aos requests*/
    RequestTagJpaController requestTagJpaController;
    RequestTagReferenceJpaController requestTagReferenceJpaController;
    
    /* ---- SCENARIOS DE VALIDAÇÃO ---- */
    ValidationScenarioJpaController validationScenarioJpaController;
    ValidationElementJpaController validationElementJpaController;
    ValidationOperationJpaController validationOperationJpaController;
    
    /* ---- SCHEDULED SCENARIOS ---- */
    ScheduledRequestJpaController scheduledRequestJpaController;
    ScheduledRequestExecutionLogJpaController scheduledRequestExecutionLogJpaController;
    
    
    public DataAccessObject()  {
        /*Inicializa o Entity Manager Factory para a conexao com o banco de dados configurada no Glassfish*/
        this.emf = javax.persistence.Persistence.createEntityManagerFactory("ServletStatelessPU");

        /*Inicia Controladores JPA para consulta dos dados de banco dos objetos do REQUEST*/
        this.requestReferenceJpaController = new RequestReferenceJpaController(this.emf);
        
        /*Atributos simples dos requests*/
        this.requestNameJpaController = new RequestNameJpaController(this.emf);
        this.methodJpaController = new MethodJpaController(this.emf);
        this.environmentJpaController = new EnvironmentJpaController(this.emf);
        this.storeJpaController = new StoreJpaController(this.emf);
        this.schemeJpaController = new SchemeJpaController(this.emf);
        this.hostAddressJpaController = new HostAddressJpaController(this.emf);
        this.pathJpaController = new PathJpaController(this.emf);
        this.payloadJpaController = new PayloadJpaController(this.emf);
        
        /*Atributos Referenciados dos requests*/
        this.templateReferenceJpaController = new TemplateReferenceJpaController(this.emf);
        this.templateJpaController = new TemplateJpaController(this.emf);
        
        this.headerReferenceJpaController = new HeaderReferenceJpaController(this.emf);
        this.headerJpaController = new HeaderJpaController(this.emf);
        
        this.parameterReferenceJpaController = new ParameterReferenceJpaController(this.emf);
        this.parameterJpaController = new ParameterJpaController(this.emf);
        
        /*Selects utilizados para busca na base durante montagem do request*/
        this.databaseSelectJpaController = new DatabaseSelectJpaController(this.emf);
        
        /*Objeto que armazena requests dinamicos*/
        this.dynamicInputDataJpaController = new DynamicInputDataJpaController(this.emf);
        
        
        /*Tags Associadas aos requests*/
        this.requestTagJpaController = new RequestTagJpaController(this.emf);
        this.requestTagReferenceJpaController = new RequestTagReferenceJpaController(this.emf);
        /*--------------------------------------------------------------------*/
        
        /* ---- SCENARIOS DE VALIDAÇÃO ---- */
        this.validationScenarioJpaController = new ValidationScenarioJpaController(this.emf);        
        this.validationElementJpaController = new ValidationElementJpaController(this.emf);
        this.validationOperationJpaController = new ValidationOperationJpaController(this.emf);
        /*--------------------------------------------------------------------*/
        
        /* ---- SCHEDULED SCENARIOS ---- */
        this.scheduledRequestJpaController = new ScheduledRequestJpaController(this.emf);        
        this.scheduledRequestExecutionLogJpaController = new ScheduledRequestExecutionLogJpaController(this.emf);
    }
    
    
    /*Funções simplificadoras de acesso*/
    public List<TemplateReference> getTemplateReferece(RequestReference requestReference){
        return this.templateReferenceJpaController.findByIdRequestReference(requestReference.getIdRequestReference());
    }
    public List<ParameterReference> getParameterReference(RequestReference requestReference){
        return this.parameterReferenceJpaController.findByIdRequestReference(requestReference.getIdRequestReference());
    }
    public List<HeaderReference> getHeaderReference(RequestReference requestReference){
        return this.headerReferenceJpaController.findByIdRequestReference(requestReference.getIdRequestReference());
    }
    public List<RequestTagReference> getRequestTagReference(RequestReference requestReference){
        return this.requestTagReferenceJpaController.findByIdRequestReference(requestReference.getIdRequestReference());
    }
   

/*------- Acesso Geral aos Controladores JPA dos objetos de banco ------------*/
    
    
    /* ---- GET de Controladores de request ---- */
    public RequestReferenceJpaController getRequestReferenceJpaController() {
        return requestReferenceJpaController;
    }

    public RequestNameJpaController getRequestNameJpaController() {
        return this.requestNameJpaController;
    }
    
    public MethodJpaController getMethodJpaController() {
        return this.methodJpaController;
    }

    public EnvironmentJpaController getEnvironmentJpaController() {
        return this.environmentJpaController;
    }

    public StoreJpaController getStoreJpaController() {
        return this.storeJpaController;
    }

    public SchemeJpaController getSchemeJpaController() {
        return this.schemeJpaController;
    }

    public HostAddressJpaController getHostAddressJpaController() {
        return this.hostAddressJpaController;
    }

    public PathJpaController getPathJpaController() {
        return this.pathJpaController;
    }
    
    public PayloadJpaController getPayloadJpaController() {
        return this.payloadJpaController;
    }

    public TemplateReferenceJpaController getTemplateReferenceJpaController() {
        return templateReferenceJpaController;
    }

    public TemplateJpaController getTemplateJpaController() {
        return this.templateJpaController;
    }

    public HeaderReferenceJpaController getHeaderReferenceJpaController() {
        return this.headerReferenceJpaController;
    }

    public HeaderJpaController getHeaderJpaController() {
        return this.headerJpaController;
    }

    public ParameterReferenceJpaController getParameterReferenceJpaController() {
        return this.parameterReferenceJpaController;
    }

    public ParameterJpaController getParameterJpaController() {
        return this.parameterJpaController;
    }

    public DatabaseSelectJpaController getDatabaseSelectJpaController() {
        return this.databaseSelectJpaController;
    }
    
    public DynamicInputDataJpaController getDynamicInputDataJpaController() {
        return this.dynamicInputDataJpaController;
    }

    public RequestTagJpaController getRequestTagJpaController() {
        return this.requestTagJpaController;
    }

    public RequestTagReferenceJpaController getRequestTagReferenceJpaController() {
        return this.requestTagReferenceJpaController;
    }
    
    /* ---- GET de Controladores de SCENARIOS DE VALIDAÇÃO ---- */
    public ValidationScenarioJpaController getValidationScenarioJpaController(){
        return this.validationScenarioJpaController;
    }
    public ValidationElementJpaController getValidationElementJpaController(){
        return this.validationElementJpaController;
    }
    public ValidationOperationJpaController getValidationOperationJpaController(){
        return this.validationOperationJpaController;
    }
    
    /* ---- GET de Controladores de SCHEDULED SCENARIOS ---- */
    public ScheduledRequestJpaController getScheduledRequestJpaController() {
        return this.scheduledRequestJpaController;
    }

    public ScheduledRequestExecutionLogJpaController getScheduledRequestExecutionLogJpaController() {
        return this.scheduledRequestExecutionLogJpaController;
    }
    
    
    
}
