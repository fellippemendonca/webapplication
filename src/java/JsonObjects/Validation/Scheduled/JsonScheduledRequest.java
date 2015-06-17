/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.Validation.Scheduled;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonScheduledRequest {
    int idScheduledRequest;
    int idRequestReference;

    public JsonScheduledRequest(int idScheduledRequest, int idRequestReference) {
        this.idScheduledRequest = idScheduledRequest;
        this.idRequestReference = idRequestReference;
    }

    public int getIdScheduledRequest() {
        return idScheduledRequest;
    }

    public void setIdScheduledRequest(int idScheduledRequest) {
        this.idScheduledRequest = idScheduledRequest;
    }

    public int getIdRequestReference() {
        return idRequestReference;
    }

    public void setIdRequestReference(int idRequestReference) {
        this.idRequestReference = idRequestReference;
    }
    
}
