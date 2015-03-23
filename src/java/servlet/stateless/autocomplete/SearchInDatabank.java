/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.stateless.autocomplete;

import DAO.RequestObjectList;
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

    public List<String> selectStarFrom(String param) {
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
        }
        return search;
    }
}
