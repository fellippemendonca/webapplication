/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Projects.Rest;

import HttpConnections.RestRequester;
import Projects.Rest.OpenApi.OpenApi;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author fellippe.mendonca
 */
public class RestRequests extends RestRequester {
    RestRequester restRequester;
    
    public RestRequests() {
        super();
        this.restRequester = new RestRequester();
    }
    
    public OpenApi openApi(String env, String store) throws IOException, URISyntaxException{
        OpenApi openApi = new OpenApi(this.restRequester, env, store);
        return openApi;
    }
}
