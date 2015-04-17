/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HttpConnections;

import java.io.StringReader;
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

    public ResponseContents() {
        this.request  = "";
        this.status   = "";
        this.contents = "";
    }
    
    public ResponseContents(String request, String status, String contents) {
        this.request  = request;
        this.status   = status;
        this.contents = contents;
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
}
