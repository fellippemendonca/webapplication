/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Entities.DatabaseSelect;
import Entities.Parameter;
import Entities.ReferenceEntities.ParameterReference;
import Entities.ReferenceEntities.RequestReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class ParameterReferenceObject {
    ParameterReference parameterReference;
    Parameter parameter;
    DataAccessObject dao;
    DatabaseSelect databaseSelect;
    
    
    public ParameterReferenceObject(DataAccessObject dao, String name, String value, int fix) {
        this.parameter = new Parameter(0, name, value, fix);
        this.parameterReference = null;
        this.dao = dao;
        this.databaseSelect = new DatabaseSelect(0, "", "");
    }

    
    public ParameterReferenceObject(ParameterReference parameterReference, DataAccessObject dao) throws NamingException{
        this.parameterReference = parameterReference;
        this.parameter = dao.getParameterJpaController().findParameter(this.parameterReference.getIdParameter());
        if(this.parameter.getParameterStatic()==0){
            this.databaseSelect = dao.getDatabaseSelectJpaController().findDatabaseSelect(this.parameterReference.getIdDatabaseSelect());
        }
    }

    public ParameterReference getParameterReference() {
        return parameterReference;
    }

    public void setParameterReference(ParameterReference parameterReference) {
        this.parameterReference = parameterReference;
    }
    
    public boolean persistParameterReference(RequestReference requestReference) {
        persistParameter();
        ParameterReference tmp;
        if(this.parameter.getParameterStatic()==1){
            tmp = new ParameterReference(0, requestReference.getIdRequestReference(), this.parameter.getIdParameter(), 0);
        }else{
            persistDatabaseSelect();
            tmp = new ParameterReference(0, requestReference.getIdRequestReference(), this.parameter.getIdParameter(), this.databaseSelect.getIdDatabaseSelect());
        }
        
        try {
            this.parameterReference = this.dao.getParameterReferenceJpaController().create(tmp);
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(ParameterReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ParameterReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ParameterReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Parameter getParameter() {
        return parameter;
    }
    
    public void setParameter(String name, String value, int fix) {
        this.parameter = new Parameter(0, name, value, fix);
    }

    public void persistParameter() {
        try {
            this.parameter = this.dao.getParameterJpaController().findOrAdd(this.parameter);
        } catch (Exception ex) {
            Logger.getLogger(ParameterReferenceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DatabaseSelect getDatabaseSelect() {
        return databaseSelect;
    }
    
    public void setDatabaseSelect(String databaseName, String value) {
        this.databaseSelect = new DatabaseSelect(0, databaseName, value);
    }

    public void persistDatabaseSelect() {
        try {
            this.databaseSelect = this.dao.getDatabaseSelectJpaController().findOrAdd(this.databaseSelect);
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
