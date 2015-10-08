/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Databank_Engines.DatabankConnector;
import Databank_Engines.Matrix.DynamicMatrix;
import JsonObjects.DynamicData.JsonDynamicData;
import JsonObjects.DynamicData.JsonDynamicDatabaseSelect;
import JsonObjects.DynamicData.JsonFieldAndData;
import JsonObjects.JsonRequestObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class DynamicDataObject {

    JsonRequestObject jsonRequestObject;

    DynamicDataObject(JsonRequestObject jsonRequestObject) {
        this.jsonRequestObject = jsonRequestObject;
    }

    public JsonRequestObject setDynamicallyGeneratedValues() {
        JsonRequestObject updatedJsonRequestObject = null;

        System.out.println("Adjusting dynamic request data...");

        String jsonReplace = "";

        try {
            jsonReplace = new Gson().toJson(this.jsonRequestObject);
        } catch (Exception ex) {
            System.out.println("Problem occured while converting jsonRequestObject to String, " + ex);
        }

        String replaceAll = "";
        int count = 0;
        for (JsonFieldAndData fieldAndData : getJsonFieldAndDataList(this.jsonRequestObject.getJsonDynamicData())) {
            String formatted = fieldAndData.getData().replaceAll("\"", "\\\\\\\\\"");
            System.out.println("Replacing: " + fieldAndData.getField() + " by " + formatted);
            replaceAll = jsonReplace.replaceAll(fieldAndData.getField(), formatted);
            jsonReplace = replaceAll;
            count++;
        }

        if (jsonReplace.equals("") || count==0) {
            return null;
        } else {
            try {
                updatedJsonRequestObject = new Gson().fromJson(jsonReplace, JsonRequestObject.class);
            } catch (JsonSyntaxException ex) {
                System.out.println("Problem occured while parsing updatedJsonRequestObject , " + ex);
            }
            return updatedJsonRequestObject;
        }
    }

    public List<JsonFieldAndData> getJsonFieldAndDataList(JsonDynamicData jsonDynamicData) {
        List<JsonFieldAndData> fieldAndDataList = new ArrayList();

        switch (jsonDynamicData.getRequestType()) {
            case "SQL":
                JsonDynamicDatabaseSelect jsonSelect = null;

                try {
                    jsonSelect = new Gson().fromJson(jsonDynamicData.getJsonRequest(), JsonDynamicDatabaseSelect.class);
                } catch (JsonSyntaxException ex) {
                    System.out.println("Problem occured while parsing jsonRequestObject, " + ex);
                }

                if (jsonSelect != null) {
                    DatabankConnector databankConnector = new DatabankConnector();
                    String query = jsonSelect.getRequest().replaceAll(";", " ");
                    DynamicMatrix DX = databankConnector.executeQuery(jsonSelect.getDatabaseName(), query);
                    if (DX.getMaxRow() > 0) {
                        for (int i = 0; i < DX.getMaxCol(0); i++) {
                            JsonFieldAndData jsonFieldAndData = new JsonFieldAndData(DX.getColumnName(i), DX.getValue(0, i));
                            fieldAndDataList.add(jsonFieldAndData);
                        }
                    }
                }
                break;
        }
        return fieldAndDataList;
    }
}
