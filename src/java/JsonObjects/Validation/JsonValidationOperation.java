/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JsonObjects.Validation;

/**
 *
 * @author fellippe.mendonca
 */
public class JsonValidationOperation {
    int idValidationOperation;
    String name;
    String description;

    public JsonValidationOperation(int idValidationOperation, String name, String description) {
        this.idValidationOperation = idValidationOperation;
        this.name = name;
        this.description = description;
    }

    public int getIdValidationOperation() {
        return idValidationOperation;
    }

    public void setIdValidationOperation(int idValidationOperation) {
        this.idValidationOperation = idValidationOperation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
