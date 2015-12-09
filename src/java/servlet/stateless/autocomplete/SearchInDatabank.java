/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.stateless.autocomplete;

import DAO.RequestObjectList;
import Entities.ValueEntities.RequestTag;
import JsonObjects.Tags.JsonTag;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author fellippe.mendonca
 */
public class SearchInDatabank {

    RequestObjectList rob;

    SearchInDatabank() throws NamingException {
        this.rob = new RequestObjectList();
    }

    //OLD FUNCTION
    public List<String> selectStringArrayFrom(String param) {
        List<String> search = new ArrayList();
        switch (param.toLowerCase()) {
            case "method":
                search = this.rob.listMethodsFromDB();
                break;
            case "environment":
                search = this.rob.listEnvironmentsFromDB();
                break;
            case "scheme":
                search = this.rob.listSchemesFromDB();
                break;
            case "host":
                search = this.rob.listHostsFromDB();
                break;
            case "path":
                search = this.rob.listPathsFromDB();
                break;
            case "templatename":
                search = this.rob.listTemplatesFromDB();
                break;
            case "parametername":
                search = this.rob.listParameterNamesFromDB();
                break;
            case "parametervalue":
                search = this.rob.listParameterValuesFromDB();
                break;
            case "headername":
                search = this.rob.listHeaderNamesFromDB();
                break;
            case "headervalue":
                search = this.rob.listHeaderValuesFromDB();
                break;
            case "tag":
                search = this.rob.listTagValuesFromDB();
                break;
            case "query-tag":
                search = this.rob.listQueryTagValuesFromDB();
                break;
        }
        return search;
    }
    
    public List<JsonTag> selectTagArrayFrom() {
        return this.rob.listJsonTagValuesFromDB();
    }
    
    //NEW
    public List<String> selectConditionalStringArrayFrom(String param, String criteria) {
        List<String> search = new ArrayList();
        switch (param.toLowerCase()) {
            case "method":
                search = this.rob.listMethodsFromDB();
                break;
            case "environment":
                search = this.rob.listEnvironmentsFromDB();
                break;
            case "scheme":
                search = this.rob.listSchemesFromDB();
                break;
            case "host":
                search = this.rob.listHostsBasedOnEnvironment(criteria);
                break;
            case "path":
                search = this.rob.listPathsBasedOnHost(criteria);
                break;
            case "templatename":
                search = this.rob.listTemplatesBasedOnPath(criteria);
                break;
            case "parametername":
                search = this.rob.listParameterNamesBasedOnHost(criteria);
                break;
            case "parametervalue":
                search = this.rob.listParameterValuesBasedOnParameterName(criteria);
                break;
            case "headername":
                search = this.rob.listHeaderNamesBasedOnHost(criteria);
                break;
            case "headervalue":
                search = this.rob.listHeaderValuesBasedOnHeaderName(criteria);
                break;
            case "tag":
                search = this.rob.listTagValuesFromDB();
                break;
        }
        return search;
    }
    
}
