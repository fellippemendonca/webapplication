/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.Tags;

import java.util.List;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonTagList {
    List<JsonTag> jsonTagList;

    public JsonTagList(List<JsonTag> jsonTagList) {
        this.jsonTagList = jsonTagList;
    }

    public List<JsonTag> getJsonTagList() {
        return jsonTagList;
    }

    public void setJsonTagList(List<JsonTag> jsonTagList) {
        this.jsonTagList = jsonTagList;
    }
    
}
