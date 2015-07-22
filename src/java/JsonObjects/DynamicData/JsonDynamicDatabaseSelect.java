/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.DynamicData;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonDynamicDatabaseSelect {
    String databaseName;
    String request;

    public JsonDynamicDatabaseSelect(String databaseName, String request) {
        this.databaseName = databaseName;
        this.request = request;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
