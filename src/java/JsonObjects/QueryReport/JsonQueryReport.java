/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.QueryReport;

import JsonObjects.Tags.JsonTag;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonQueryReport {
    int id;
    String name;
    String description;
    String dbName;
    String selectQuery;
    List<JsonTag> jsonTags;
    
    public JsonQueryReport() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.dbName = "";
        this.selectQuery = "";
        this.jsonTags = new ArrayList();
    }
    
    public JsonQueryReport(int id, String name, String description, String dbName, String selectQuery, List<JsonTag> jsonTags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dbName = dbName;
        this.selectQuery = selectQuery;
        this.jsonTags = jsonTags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getSelectQuery() {
        return selectQuery;
    }

    public void setSelectQuery(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    public List<JsonTag> getJsonTags() {
        return jsonTags;
    }

    public void setJsonTags(List<JsonTag> jsonTags) {
        this.jsonTags = jsonTags;
    }
    
}
