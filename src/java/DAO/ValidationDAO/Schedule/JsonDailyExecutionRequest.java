/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO.ValidationDAO.Schedule;

import JsonObjects.Validation.Scheduled.JsonScheduledRequestExecutionLog;
import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonDailyExecutionRequest {
    int idRequestRefernce;
    String executionDate;
    List<JsonScheduledRequestExecutionLog> jsonScheduledRequestExecutionLogList;

    public JsonDailyExecutionRequest(int idRequestRefernce, String executionDate, List<JsonScheduledRequestExecutionLog> jsonScheduledRequestExecutionLogList) {
        this.idRequestRefernce = idRequestRefernce;
        this.executionDate = executionDate;
        this.jsonScheduledRequestExecutionLogList = jsonScheduledRequestExecutionLogList;
    }

    public int getIdRequestRefernce() {
        return idRequestRefernce;
    }

    public void setIdRequestRefernce(int idRequestRefernce) {
        this.idRequestRefernce = idRequestRefernce;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    public List<JsonScheduledRequestExecutionLog> getJsonScheduledRequestExecutionLogList() {
        return jsonScheduledRequestExecutionLogList;
    }

    public void setJsonScheduledRequestExecutionLogList(List<JsonScheduledRequestExecutionLog> jsonScheduledRequestExecutionLogList) {
        this.jsonScheduledRequestExecutionLogList = jsonScheduledRequestExecutionLogList;
    }
    
    
}
