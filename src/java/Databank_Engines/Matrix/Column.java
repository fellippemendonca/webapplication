/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Databank_Engines.Matrix;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Fellippe de Lelis
 */
public class Column {
    ArrayList<Element> column;
    Iterator<Element> it;

    public Column() {
        this.column = new ArrayList<>();
        Element E = new Element("NULL");
        this.column.add(E);
    }
    
    public ArrayList getArray(){
        return column;
    }

    public String getColumn(int i) {
            String val = this.column.get(i).getElementValue();
            return val;
    }
    
    public ArrayList getColumnArray() {
        return column;
    }

    public boolean removeColumn(int i) {
        this.column.remove(i);
        return true;
    }

    public void setColumn(int i, String value) {
        Element E = new Element("NULL");
        if(i<this.column.size()){
            E.setElementValue(value);
            this.column.set(i, E);
        } else {
            while(i>=this.column.size()){
                E.setElementValue("NULL");
                this.column.add(E);
            }
            E.setElementValue(value);
            this.column.set(i,E);
        }
    }
    
    public void putElement(Element E){
        this.column.add(E);
    }

    public int getSize(){
        return this.column.size();
    }
    
    public Element ItNext(){
        this.it = this.column.iterator();
        return this.it.next();
    }
    
    public boolean IthasNext(){
        this.it = this.column.iterator();
        return this.it.hasNext();
    }
    
    public void removeIt(){
        this.it.remove();
    }
    
}
