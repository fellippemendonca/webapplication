/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Projects.Rest.OpenApi;

import HttpConnections.RestRequester;
import Projects.Rest.OpenApi.V1.V1;
import Projects.Rest.OpenApi.V2.V2;

/**
 *
 * @author fellippe.mendonca
 */
public class OpenApi extends RestRequester{
    RestRequester restRequester;
    
    
    public OpenApi(RestRequester restRequester, String env, String store){
        this.restRequester = restRequester; 
        
        switch(env.toUpperCase()){
            case "HLG":
                this.restRequester.setScheme("http");
                //this.restRequester.setHost("busapi.mp.hlg.dc.nova");
                this.restRequester.setHost("atd.mp.hlg.dc.nova");
                break;
            case "PRD":
                this.restRequester.setScheme("https");
                this.restRequester.setHost("api.extra.com.br");
                break;
        }
        
        switch(store){
            case "3":
                this.restRequester.addHeader("nova-app-token", "abc");
                this.restRequester.addHeader("nova-auth-token", "CIT-3");
                break;
            case "10025":
                this.restRequester.addHeader("nova-app-token", "hlg_multisites");
                this.restRequester.addHeader("nova-auth-token", "hlg_multisites");
                break;
        }
        this.restRequester.addHeader("Content-type", "application/json; charset=utf-8");
    }
    
    public V1 v1(){
        V1 v1 = new V1(this.restRequester);
        return v1;
    }
    
    public V2 v2(){
        V2 v2 = new V2(this.restRequester);
        return v2;
    }
}
