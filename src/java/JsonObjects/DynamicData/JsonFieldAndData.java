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
public class JsonFieldAndData {
    String field;
    String data;

    public JsonFieldAndData(String field, String data) {
        this.field = field;
        this.data = data;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
}
