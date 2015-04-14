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
import jpa.RequestTagJpaController;
import jpa.SchemeJpaController;
import jpa.StoreJpaController;
import jpa.TemplateJpaController;

/**
 *
 * @author fellippe.mendonca
 */



public class DataAccessObject implements Serializable{

    private final EntityManagerFactory emf;
    
    /*Atributos simples dos requests*/
    RequestReferenceJpaController requestReferenceJpaController;
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
    
    /*Tags Associadas aos requests*/
    RequestTagJpaController requestTagJpaController;
    RequestTagReferenceJpaController requestTagReferenceJpaController;
    
    public DataAccessObject()  {
        this.emf = javax.persistence.Persistence.createEntityManagerFactory("ServletStatelessPU");

        this.requestReferenceJpaController = new RequestReferenceJpaController(this.emf);
        
        this.methodJpaController = new MethodJpaController(this.emf);
        this.environmentJpaController = new EnvironmentJpaController(this.emf);
        this.storeJpaController = new StoreJpaController(this.emf);
        this.schemeJpaController = new SchemeJpaController(this.emf);
        this.hostAddressJpaController = new HostAddressJpaController(this.emf);
        this.pathJpaController = new PathJpaController(this.emf);
        this.payloadJpaController = new PayloadJpaController(this.emf);
        
        this.templateReferenceJpaController = new TemplateReferenceJpaController(this.emf);
        this.templateJpaController = new TemplateJpaController(this.emf);
        
        this.headerReferenceJpaController = new HeaderReferenceJpaController(this.emf);
        this.headerJpaController = new HeaderJpaController(this.emf);
        
        this.parameterReferenceJpaController = new ParameterReferenceJpaController(this.emf);
        this.parameterJpaController = new ParameterJpaController(this.emf);
        
        this.databaseSelectJpaController = new DatabaseSelectJpaController(this.emf);
        
        this.requestTagJpaController = new RequestTagJpaController(this.emf);
        this.requestTagReferenceJpaController = new RequestTagReferenceJpaController(this.emf);
    }

    public List<RequestReference> getRequestReferece(){
        return this.requestReferenceJpaController.findRequestReferenceEntities();
    }
    public List<RequestReference> getRequestRefereceTagFiltered(){
        return this.requestReferenceJpaController.findRequestReferenceEntities();
    }
    
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
   

/*-------------------------------NEW DAO--------------------------------*/
    
    public RequestReferenceJpaController getRequestReferenceJpaController() {
        return requestReferenceJpaController;
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

    public RequestTagJpaController getRequestTagJpaController() {
        return this.requestTagJpaController;
    }

    public RequestTagReferenceJpaController getRequestTagReferenceJpaController() {
        return this.requestTagReferenceJpaController;
    }
}
