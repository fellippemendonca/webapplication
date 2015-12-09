/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO.QueryReportObject;

import DAO.DataAccessObject;
import Entities.QueryReportEntities.QueryReport;
import Entities.QueryReportEntities.QueryTag;
import Entities.QueryReportEntities.QueryTagReference;
import JsonObjects.Tags.JsonTag;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import jpa.exceptions.RollbackFailureException;

/**
 *
 * @author fellippe.mendonca
 */
public class QueryTagObject {
    QueryTag queryTag;
    QueryTagReference queryTagReference;
    DataAccessObject dao;
    JsonTag jsonTag;
    
    public QueryTagObject(){
        this.queryTag = new QueryTag(0, "");
    }
    
    public QueryTagObject(DataAccessObject dao, String name) {
        this.dao = dao;
        this.queryTag = new QueryTag(0, name);
        this.jsonTag = new JsonTag(0,name);
        this.queryTagReference = null;
    }
    
    public QueryTagObject(QueryTagReference queryTagReference, DataAccessObject dao) {
        this.queryTagReference = queryTagReference;
        this.dao = dao;
        this.queryTag = dao.getQueryTagJpaController().findQueryTag(this.queryTagReference.getIdQueryTag());
        this.jsonTag = new JsonTag(this.queryTag.getId(), this.queryTag.getName());
    }
    
    public void persistQueryTag() {
        try {
            this.queryTag = this.dao.getQueryTagJpaController().findOrAdd(this.queryTag);
        } catch (Exception ex) {
            Logger.getLogger(QueryTagObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean persistQueryTagReference(QueryReport queryReport) {
        persistQueryTag();
        QueryTagReference tmp = new QueryTagReference(0, queryReport.getId(), this.queryTag.getId());
        try {
            this.queryTagReference = this.dao.getQueryTagReferenceJpaController().create(tmp);
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(QueryTagObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(QueryTagObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(QueryTagObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteQueryTagReference(int idQueryReport){
        return this.dao.getQueryTagReferenceJpaController().destroyByIdQueryReport(idQueryReport);
    }
    
    public boolean deleteQueryTagReference(){
        try {
             this.dao.getQueryTagReferenceJpaController().destroy(this.queryTagReference.getId());
            return true;
        } catch (NamingException ex) {
            Logger.getLogger(QueryTagObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(QueryTagObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(QueryTagObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public JsonTag getJsonTag() {
        return this.jsonTag;
    }
    
    public void setQueryTag(String tagValue) {
        this.queryTag = new QueryTag(0, tagValue);
    }
    public QueryTag getRequestTag() {
        return this.queryTag;
    }
    public QueryTagReference getQueryTagReference() {
        return this.queryTagReference;
    }
    public void setQueryTagReference(QueryTagReference queryTagReference) {
        this.queryTagReference = queryTagReference;
    }
}
