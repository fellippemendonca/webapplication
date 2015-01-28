/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Databank_Engines.Matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Fellippe de Lelis
 */
public class Row {
    List row;
    Iterator it;

    public Row() {
        this.row = new ArrayList<String>();
        this.it = row.iterator();
    }

    public List getRow() {
        return row;
    }

    public void setRow(List row) {
        this.row = row;
    }
    
}
