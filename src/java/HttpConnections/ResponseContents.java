/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HttpConnections;

import java.io.StringReader;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;


/**
 *
 * @author fellippe.mendonca
 */
public class ResponseContents {
    String request;
    String status;
    String contents;
    Date endDate;
    int execTime;
    

    public ResponseContents() {
        this.request  = "";
        this.status   = "";
        this.contents = "";
        this.endDate = null;
        this.execTime = 0;
    }
    
    public ResponseContents(String request, String status, String contents) {
        this.request  = request;
        this.status   = status;
        this.contents = contents;
        this.endDate = null;
        this.execTime = 0;
    }
    
    public String getRequest() {
        return this.request;
    }

    public void setRequest(String status) {
        this.request = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContents() {
        return this.contents;
    }
    
    public void setContents(String contents) {
        this.contents = contents;
    }
    
    public JsonObject getJsonObject() {
        JsonObject jObject ;
        try (JsonReader jsonReader = Json.createReader(new StringReader(this.contents))) {
            jObject = jsonReader.readObject(); 
            return jObject;
        }
    }
    
    public JsonArray getJsonArray() {
        JsonArray jObject ;
        try (JsonReader jsonReader = Json.createReader(new StringReader(this.contents))) {
            jObject = jsonReader.readArray();
            return jObject;
        }
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate() {
        this.endDate = new Date();
    }

    public int getExecTime() {
        return execTime;
    }

    public void setExecTime(int execTime) {
        this.execTime = execTime;
    }
    
    
    
    public int getDiffMilliseconds(Date startDate) {
        if(startDate==null || this.endDate==null){
            setExecTime(9999);
            return 9999;
        }else{
            setExecTime((int)(getEndDate().getTime()-startDate.getTime()));
            return getExecTime();
        }
    }
    
    
}
