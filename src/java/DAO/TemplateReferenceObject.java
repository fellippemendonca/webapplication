/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entities.DatabaseSelect;
import Entities.ReferenceEntities.RequestReference;
import Entities.Template;
import Entities.ReferenceEntities.TemplateReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class TemplateReferenceObject {
    int sequence;
    TemplateReference templateReference;
    Template template;
    DataAccessObject dao;
    DatabaseSelect databaseSelect;

    public TemplateReferenceObject(DataAccessObject dao, String name, int seq, int fix) {
        this.sequence = seq;
        this.templateReference = null;
        this.dao = dao;
        this.databaseSelect = new DatabaseSelect(0, "", "");
        
        try {
            this.template = dao.getTemplateJpaController().findOrAdd(new Template(0, name, fix));
        } catch (Exception ex) {
            Logger.getLogger(TemplateReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public TemplateReferenceObject(TemplateReference templateReference, DataAccessObject dao){
        this.templateReference = templateReference;
        this.template = dao.getTemplateJpaController().findTemplate(this.templateReference.getIdTemplate());
        if(this.template.getTemplateStatic()==0){
            this.databaseSelect = dao.getDatabaseSelectJpaController().findDatabaseSelect(this.templateReference.getIdDatabaseSelect());
        }
    }

    public TemplateReference getTemplateReference() {
        return templateReference;
    }

    public void setTemplateReference(TemplateReference templateReference) {
        this.templateReference = templateReference;
    }
    
    public boolean persistTemplateReference(RequestReference requestReference){
        TemplateReference tmp;
        if(this.template.getTemplateStatic()==1){
            tmp = new TemplateReference(0, requestReference.getIdRequestReference(), this.template.getIdTemplate(), this.sequence, 0);
        }else{
            tmp = new TemplateReference(0, requestReference.getIdRequestReference(), this.template.getIdTemplate(), this.sequence, this.databaseSelect.getIdDatabaseSelect());
        }
        
        try {
            this.templateReference = this.dao.getTemplateReferenceJpaController().create(tmp);
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(TemplateReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(TemplateReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TemplateReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(String name, int fix) {
        Template tmp = new Template(0, name, fix);
        try {
            this.template = dao.getTemplateJpaController().findOrAdd(tmp);
        } catch (Exception ex) {
            Logger.getLogger(TemplateReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DatabaseSelect getDatabaseSelect() {
        return databaseSelect;
    }

    public void setDatabaseSelect(String databaseName, String value) {
        DatabaseSelect tmp = new DatabaseSelect(0, databaseName, value);
        try {
            this.databaseSelect = this.dao.getDatabaseSelectJpaController().findOrAdd(tmp);
        } catch (Exception ex) {
            Logger.getLogger(ParameterReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataAccessObject getDao() {
        return dao;
    }

    public void setDao(DataAccessObject dao) {
        this.dao = dao;
    }
    
    
}
