/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.ValidationDAO;

import HttpConnections.ResponseContents;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author fellippe.mendonca
 */
public class ValidationOperationSource {

    public boolean validateElement(String operation, String expected, ResponseContents responseContents) {
        boolean success = false;
        switch (operation) {
            case "Compare Json Structure":
                success = compareBothJsonElements(expected, responseContents.getContents());
                break;
            case "Find Json Element":
                success = findInJsonElement(responseContents.getContents(), expected);
                break;
            case "Find String":
                success = responseContents.getContents().contains(expected);
                break;
            case "Find Http Status":
                success = responseContents.getStatus().contains(expected);
                break;
            case "Response Time":
                success = responseContents.getExecTime() <= convertToNumber(expected);
                break;
            case "Numerically Bigger":
                success = convertToNumber(responseContents.getContents()) >= convertToNumber(expected);
                break;
            case "Numerically Smaller":
                success = convertToNumber(responseContents.getContents()) <= convertToNumber(expected);
                break;
            case "Numerically Equal":
                success = convertToNumber(responseContents.getContents()) == convertToNumber(expected);
                break;
        }
        return success;
    }
    
    public boolean detectNumber(String contents){
        boolean isNumber = false;
        try{
            Integer.parseInt(contents);
            isNumber = true;
        } catch(NumberFormatException e) {System.out.println(e); isNumber = false;}
        return isNumber;
    }
    
    public int convertToNumber(String contents){
        if(detectNumber(contents)){
            return Integer.parseInt(contents);
        } else {
            return -99;
        }
    }

    public boolean compareBothJsonElements(String json1, String json2) {
        JsonParser parser1; 
        JsonParser parser2; 
        boolean success = false;
        try {
            parser1 = new JsonParser();
            parser2 = new JsonParser();
            JsonElement element1 = parser1.parse(json1);
            JsonElement element2 = parser2.parse(json2);
            success = jsonCompare(element1, element2);
        } catch (JsonSyntaxException name) {
            System.err.println("JsonSyntaxException: " + name);
        }
        finally{
            return success;
        }
    }

    public boolean jsonCompare(JsonElement element1, JsonElement element2) {

        //-------------------Caso o Json seja NULO------------------------------
        if (element1.isJsonNull()) {
            //System.out.println("Element is null");

            //---------------Caso o Json seja uma primitiva-------------------------
        } else if (element1.isJsonPrimitive()) {
            //System.out.println("Element is primitive");

            //----------------Caso o Json seja um Objeto----------------------------
        } else if (element1.isJsonObject()) {
            //System.out.println("Element is object");
            Iterator<Map.Entry<String, JsonElement>> CrunchifyIterator = element1.getAsJsonObject().entrySet().iterator();
            while (CrunchifyIterator.hasNext()) {
                Map.Entry<String, JsonElement> aux = CrunchifyIterator.next();
                //System.out.println("Field:" + aux.getKey());
                if (findElement(element2, aux.getKey()) == false) {
                    //System.out.println("NOT FOUND Field:" + aux.getKey());
                    return false;
                } else if (findElement(element2, aux.getKey()) == true) {
                    //System.out.println("FOUND Field:" + aux.getKey());
                    if (jsonCompare(aux.getValue(), element2) == false) {
                        return false;
                    }
                }
            }
            //------------Caso o json seja um array---------------------------------
        } else if (element1.isJsonArray()) {
            //System.out.println("Element is array");
            for (JsonElement jsonElement : element1.getAsJsonArray()) {
                if (jsonCompare(jsonElement, element2) == false) {
                    return false;
                }
            }
        }
        return true;
    }

//-------------------------FIND ELEMENTS ON JSON--------------------------------
    public boolean findInJsonElement(String json, String field) {
        JsonParser parser2;
        boolean success = false;
        try {
            parser2 = new JsonParser();
            JsonElement elementX = parser2.parse(json);
            success = findElement(elementX, field);
        } catch (JsonSyntaxException name) {
            System.err.println("JsonSyntaxException: " + name);
        } finally{
            return success;
        }
    }

    public boolean findElement(JsonElement element, String field) {

        //-------------------Caso o Json seja NULO------------------------------
        if (element.isJsonNull()) {
            //System.out.println("Element is null");

            //---------------Caso o Json seja uma primitiva-------------------------
        } else if (element.isJsonPrimitive()) {
            //System.out.println("Element is primitive");

            //----------------Caso o Json seja um Objeto----------------------------
        } else if (element.isJsonObject()) {
            //System.out.println("Element is object");

            if (element.getAsJsonObject().has(field)) {
                //System.out.println("Object found --> " + element.getAsJsonObject().get(field).toString());
                return true;
            } else {
                Iterator<Map.Entry<String, JsonElement>> CrunchifyIterator = element.getAsJsonObject().entrySet().iterator();
                while (CrunchifyIterator.hasNext()) {
                    if (findElement(CrunchifyIterator.next().getValue(), field) == true) {
                        return true;
                    }
                }
            }
            //------------Caso o json seja um array---------------------------------
        } else if (element.isJsonArray()) {
            //System.out.println("Element is array");
            for (JsonElement jsonElement : element.getAsJsonArray()) {
                if (findElement(jsonElement, field) == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean findElementAndValue(JsonElement element, String field, String value) {

        //-------------------Caso o Json seja NULO------------------------------
        if (element.isJsonNull()) {
            //System.out.println("Element is null");

            //---------------Caso o Json seja uma primitiva-------------------------
        } else if (element.isJsonPrimitive()) {
            //System.out.println("Element is primitive");

            //----------------Caso o Json seja um Objeto----------------------------
        } else if (element.isJsonObject()) {
            //System.out.println("Element is object");

            if (element.getAsJsonObject().has(field) && element.getAsJsonObject().get(field).toString().contains(value)) {
                //System.out.println("Object found --> " + element.getAsJsonObject().get(field).toString());
                return true;
            } else {
                Iterator<Map.Entry<String, JsonElement>> CrunchifyIterator = element.getAsJsonObject().entrySet().iterator();
                while (CrunchifyIterator.hasNext()) {
                    if (findElement(CrunchifyIterator.next().getValue(), field) == true) {
                        return true;
                    }
                }
            }
            //------------Caso o json seja um array---------------------------------
        } else if (element.isJsonArray()) {
            //System.out.println("Element is array");
            for (JsonElement jsonElement : element.getAsJsonArray()) {
                if (findElement(jsonElement, field) == true) {
                    return true;
                }
            }
        }
        return false;
    }

}
