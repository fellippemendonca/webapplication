/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.ReferenceEntities.HeaderReference;
import Entities.ReferenceEntities.ParameterReference;
import Entities.ReferenceEntities.RequestReference;
import Entities.ReferenceEntities.TemplateReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpa.DatabaseSelectJpaController;
import jpa.EnvironmentJpaController;
import jpa.HeaderJpaController;
import jpa.ReferenceJpaControllers.HeaderReferenceJpaController;
import jpa.HostAddressJpaController;
import jpa.MethodJpaController;
import jpa.ParameterJpaController;
import jpa.ReferenceJpaControllers.ParameterReferenceJpaController;
import jpa.PathJpaController;
import jpa.ReferenceJpaControllers.RequestReferenceJpaController;
import jpa.SchemeJpaController;
import jpa.StoreJpaController;
import jpa.TemplateJpaController;
import jpa.ReferenceJpaControllers.TemplateReferenceJpaController;

/**
 *
 * @author fellippe.mendonca
 */



public class DataAccessObject implements Serializable{

    private final EntityManagerFactory emf;
    RequestReferenceJpaController requestReferenceJpaController;
    MethodJpaController methodJpaController;
    EnvironmentJpaController environmentJpaController;
    StoreJpaController storeJpaController;
    SchemeJpaController schemeJpaController;
    HostAddressJpaController hostAddressJpaController;
    PathJpaController pathJpaController;
    
    TemplateReferenceJpaController templateReferenceJpaController;
    TemplateJpaController templateJpaController;
    
    HeaderReferenceJpaController headerReferenceJpaController;
    HeaderJpaController headerJpaController;
    
    ParameterReferenceJpaController parameterReferenceJpaController;
    ParameterJpaController parameterJpaController;
    
    DatabaseSelectJpaController databaseSelectJpaController = new DatabaseSelectJpaController(this.emf);

    public DataAccessObject()  {
        this.emf = javax.persistence.Persistence.createEntityManagerFactory("ServletStatelessPU");

        this.requestReferenceJpaController = new RequestReferenceJpaController(this.emf);
        
        this.methodJpaController = new MethodJpaController(this.emf);
        this.environmentJpaController = new EnvironmentJpaController(this.emf);
        this.storeJpaController = new StoreJpaController(this.emf);
        this.schemeJpaController = new SchemeJpaController(this.emf);
        this.hostAddressJpaController = new HostAddressJpaController(this.emf);
        this.pathJpaController = new PathJpaController(this.emf);
        
        this.templateReferenceJpaController = new TemplateReferenceJpaController(this.emf);
        this.templateJpaController = new TemplateJpaController(this.emf);
        this.headerReferenceJpaController = new HeaderReferenceJpaController(this.emf);
        this.headerJpaController = new HeaderJpaController(this.emf);
        this.parameterReferenceJpaController = new ParameterReferenceJpaController(this.emf);
        this.parameterJpaController = new ParameterJpaController(this.emf);
        
        this.databaseSelectJpaController = new DatabaseSelectJpaController(this.emf);
    }

    public List<RequestReference> getRequestReferece(){
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
   

/*-------------------------------NEW DAO--------------------------------*/
    
    public RequestReferenceJpaController getRequestReferenceJpaController() {
        return requestReferenceJpaController;
    }

    public MethodJpaController getMethodJpaController() {
        return methodJpaController;
    }

    public EnvironmentJpaController getEnvironmentJpaController() {
        return environmentJpaController;
    }

    public StoreJpaController getStoreJpaController() {
        return storeJpaController;
    }

    public SchemeJpaController getSchemeJpaController() {
        return schemeJpaController;
    }

    public HostAddressJpaController getHostAddressJpaController() {
        return hostAddressJpaController;
    }

    public PathJpaController getPathJpaController() {
        return pathJpaController;
    }

    public TemplateReferenceJpaController getTemplateReferenceJpaController() {
        return templateReferenceJpaController;
    }

    public TemplateJpaController getTemplateJpaController() {
        return templateJpaController;
    }

    public HeaderReferenceJpaController getHeaderReferenceJpaController() {
        return headerReferenceJpaController;
    }

    public HeaderJpaController getHeaderJpaController() {
        return headerJpaController;
    }

    public ParameterReferenceJpaController getParameterReferenceJpaController() {
        return parameterReferenceJpaController;
    }

    public ParameterJpaController getParameterJpaController() {
        return parameterJpaController;
    }

    public DatabaseSelectJpaController getDatabaseSelectJpaController() {
        return databaseSelectJpaController;
    }
}
