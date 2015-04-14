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
    
    /*public void fillRequestListFromDB() throws NamingException{
        this.restRequesterList.clear();
        this.restRequesterList = new ArrayList<>();
        RequestObjectList rob = new RequestObjectList();
        for(RequestReferenceObject rro:rob.getObjectRequestList()){
            this.restRequesterList.add(rro.generateRestRequester());
        }
    }*/

    public String getRequest() throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonRequestList();
    }
    
    public String getFilteredRequest(String jsonList) throws NamingException {
        RequestObjectList rob = new RequestObjectList();
        return rob.getJsonFilteredRequestList(jsonList);
    }
    
    
    
    public ResponseContents execRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.setRequest(json).generateRestRequester().Request();
    }
    
    
    
//--------------------------------CRUD REQUESTS---------------------------------
    public boolean createRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.persistRequest(json);
    }
    
    public boolean updateRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.updateRequest(json);
    }
    
    public boolean removeRequest(String json) throws NamingException, IOException, URISyntaxException {
        RequestObjectList rob = new RequestObjectList();
        return rob.deleteRequest(json);
    }
}
