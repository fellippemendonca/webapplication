/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.ValidationDAO.Schedule;

import DAO.DataAccessObject;
import DAO.ValidationDAO.RequestValidationObject;
import Entities.Scheduled.ScheduledRequest;
import JsonObjects.Validation.JsonRequestValidation;
import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class ScheduledRequestObject {

    DataAccessObject dao;
    RequestValidationObject requestValidationObject;
    ScheduledRequest scheduledRequest;

    public ScheduledRequestObject(DataAccessObject dao, int idRequestReference) {
        this.dao = dao;
        this.requestValidationObject = new RequestValidationObject(dao, idRequestReference);
        this.scheduledRequest = new ScheduledRequest(0, idRequestReference);
    }

    public boolean persistSchedule() throws Exception {
        this.dao.getScheduledRequestJpaController().findOrAdd(this.scheduledRequest);
        return this.scheduledRequest.getIdScheduledRequest() > 0;
    }

    public ScheduledRequest findSchedule(int idRequestReference) {
        this.scheduledRequest = new ScheduledRequest(0, idRequestReference);
        return this.dao.getScheduledRequestJpaController().find(this.scheduledRequest);
    }

    public boolean deleteSchedule() throws Exception {
        this.dao.getScheduledRequestJpaController().destroy(this.dao.getScheduledRequestJpaController().find(this.scheduledRequest).getIdScheduledRequest());
        return true;
    }
    
    public JsonRequestValidation executeValidation(){
        return this.requestValidationObject.getJsonRequestValidationExecuted();
    }

}
