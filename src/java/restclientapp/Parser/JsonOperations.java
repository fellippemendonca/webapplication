/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restclientapp.Parser;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;


/**
 *
 * @author fellippe.mendonca
 */
public class JsonOperations {

    private String jsonString;
    public JsonObject jsonObject;
    
    public JsonOperations(){
        this.jsonString = "{\"json\":{\"estah\":\"vazio\"}}";
        this.jsonObject = null;
    }
    public JsonOperations(String jsonString){
        this.jsonString =jsonString;
        this.jsonObject = null;
    }

    public JsonObject getJsonObject() {
        try (JsonReader jsonReader = Json.createReader(new StringReader(this.jsonString))) {
            this.jsonObject = jsonReader.readObject();
        }
        return this.jsonObject;
    }

    public String printJson() {
       System.out.println(this.jsonString);
        return null;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
