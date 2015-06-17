/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.ValidationDAO.Schedule;

import DAO.DataAccessObject;
import Entities.Scheduled.ScheduledRequest;
import Entities.Scheduled.ScheduledRequestExecutionLog;
import JsonObjects.Validation.JsonRequestValidation;
import JsonObjects.Validation.Scheduled.JsonScheduledRequestExecutionLog;
import com.google.gson.Gson;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fellippe.mendonca
 */
public class ScheduledRequestExecutionLogObject {

    //ScheduledRequestExecutionLog scheduledRequestExecutionLog;
    DataAccessObject dao;

    public ScheduledRequestExecutionLogObject(DataAccessObject dao) {
        this.dao = dao;
    }

    public boolean executeRequestsValidationAndGenerateLog() {
        boolean logSuccess = true;
        for (ScheduledRequest scheduledRequest : this.dao.getScheduledRequestJpaController().findScheduledRequestEntities()) {
            JsonRequestValidation jsonRequestValidation = new ScheduledRequestObject(this.dao, scheduledRequest.getIdRequestReference()).executeValidation();
            int idRequestReference = scheduledRequest.getIdRequestReference();
            Date date = new Date();
            String stringJsonRequestValidation = new Gson().toJson(jsonRequestValidation);
            int success = 0;
            if (jsonRequestValidation.isSuccess()) {
                success = 1;
            }
            ScheduledRequestExecutionLog scheduledRequestExecutionLog = new ScheduledRequestExecutionLog(0, idRequestReference, date, stringJsonRequestValidation, success);
            try {
                dao.getScheduledRequestExecutionLogJpaController().create(scheduledRequestExecutionLog);
            } catch (Exception ex) {
                logSuccess = false;
                Logger.getLogger(ScheduledRequestExecutionLogObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return logSuccess;
    }
    
    public JsonDailyExecutionRequest getDailyRequestValidationLog(int idRequestReference, String executionDate) throws ParseException {
        List<JsonScheduledRequestExecutionLog> jsonScheduledRequestExecutionLogList = new ArrayList();
        if(this.dao.getScheduledRequestExecutionLogJpaController().findByIdAndDate(idRequestReference, executionDate) == null){
            return null;
        } else {
        
        for(ScheduledRequestExecutionLog log : this.dao.getScheduledRequestExecutionLogJpaController().findByIdAndDate(idRequestReference, executionDate)){
            JsonRequestValidation jsonRequestValidation = new Gson().fromJson(log.getJsonRequestValidation(), JsonRequestValidation.class);
            boolean success = (log.getSuccess()==1);
            JsonScheduledRequestExecutionLog jsonLog = new JsonScheduledRequestExecutionLog(log.getIdScheduledRequestExecutionLog(), log.getIdRequestReference(), log.getExecutionDate(), jsonRequestValidation, success);
            jsonScheduledRequestExecutionLogList.add(jsonLog);
        }
        JsonDailyExecutionRequest jsonDailyExecutionRequest = new JsonDailyExecutionRequest(idRequestReference, executionDate, jsonScheduledRequestExecutionLogList);
        
    return jsonDailyExecutionRequest;
    }
    }
    
    
    //public 

}
