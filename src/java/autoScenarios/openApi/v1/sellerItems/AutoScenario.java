/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoScenarios.openApi.v1.sellerItems;
import DAO.RequestObjectList;
import DAO.RequestReferenceObject;
import HttpConnections.ResponseContents;
import HttpConnections.RestRequester;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
/**
 *
 * @author fellippe.mendonca
 */
public class AutoScenario implements Serializable {

    List<RestRequester> restRequesterList;

    public AutoScenario() {
        this.restRequesterList = new ArrayList<>();
    }

    public List<RestRequester> getScenarioList() {
        return this.restRequesterList;
    }
    
    public void fillRequestListFromDB() throws NamingException{
        this.restRequesterList.clear();
        this.restRequesterList = new ArrayList<>();
        RequestObjectList rob = new RequestObjectList();
        for(RequestReferenceObject rro:rob.getObjectRequestList()){
            this.restRequesterList.add(rro.generateRestRequester());
        }
    }

    public int getScenarioListSize() {
        return this.restRequesterList.size();
    }

    public ResponseContents executeScenario(int index) throws IOException, URISyntaxException {
        ResponseContents rc = new ResponseContents();
        rc = restRequesterList.get(index).Request();
        return rc;
    }

    public String getRequest() throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonRequestList();
    }
    
    public ResponseContents execRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.setRequest(json).generateRestRequester().Request();
    }
}
