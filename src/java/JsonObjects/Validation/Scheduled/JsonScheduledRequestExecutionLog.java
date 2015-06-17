/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.Validation.Scheduled;

import JsonObjects.Validation.JsonRequestValidation;
import java.util.Date;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonScheduledRequestExecutionLog {
    int idScheduledRequestExecutionLog;
    int idRequestReference;
    Date executionDate;
    JsonRequestValidation jsonRequestValidation;
    boolean success;

    public JsonScheduledRequestExecutionLog(int idScheduledRequestExecutionLog, int idRequestReference, Date executionDate, JsonRequestValidation jsonRequestValidation, boolean success) {
        this.idScheduledRequestExecutionLog = idScheduledRequestExecutionLog;
        this.idRequestReference = idRequestReference;
        this.executionDate = executionDate;
        this.jsonRequestValidation = jsonRequestValidation;
        this.success = success;
    }

    public int getIdScheduledRequestExecutionLog() {
        return idScheduledRequestExecutionLog;
    }

    public void setIdScheduledRequestExecutionLog(int idScheduledRequestExecutionLog) {
        this.idScheduledRequestExecutionLog = idScheduledRequestExecutionLog;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public JsonRequestValidation getJsonRequestValidation() {
        return jsonRequestValidation;
    }

    public void setJsonRequestValidation(JsonRequestValidation jsonRequestValidation) {
        this.jsonRequestValidation = jsonRequestValidation;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
}
