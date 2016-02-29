/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Databank_Engines.Matrix;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Fellippe de Lelis
 */
public class DynamicMatrix {
    List<Column> rows;
    Column colName;

    public DynamicMatrix() {
        this.rows = new ArrayList<>();
        this.colName = new Column();
    }
    
    
    public boolean setColumnName(int x, String value){
        this.colName.setColumn(x, value);
        return true;
    }
    
    public String getColumnName(int x){
        return this.colName.getColumn(x);
    }
    
    public String getValueByColumnName(String column) {
        String value="";
        for(int i = 0; i<this.colName.getSize(); i++){
            if(getColumnName(i).toUpperCase().equals(column.toUpperCase())){
                value = getValue(0, i);
            }
        }
        return value;
    }
            
    public String getValue(int x, int y) {
        if(x < this.rows.size()){
            if(y < this.rows.get(x).getSize()){
                return this.rows.get(x).getColumn(y);
            }else{
            return null;
        }
        }else{
           return null;
        }
    }

    public boolean setValue(int x, int y, String value) {
        if(x<this.rows.size()){
            this.rows.get(x).setColumn(y, value);
            return true;
        } else {
            while(x>=this.rows.size()){
                Column c1 = new Column();
                c1.setColumn(y, "NULL");
                this.rows.add(c1);
            }
            Column c = new Column();
            c.setColumn(y, value);
            this.rows.set(x,c);
            return true;
        }
    }
    
    public int getMaxRow() {
        return this.rows.size();
    }

    public int getMaxCol(int index) {
        if(getMaxRow()>0){
            return this.rows.get(index).getSize(); 
        } else {
            return 0;
        }
        
    }
    
    public String stringMatrix(int x, int y){
        String MX = "";
        int maxX;
        if(x<this.getMaxRow()){
            maxX = x;
        } else {
            maxX = this.getMaxRow();
        }
            for(int i=0; i<maxX; i++){
                int maxY;
                if(y<this.getMaxCol(i)){
                    maxY=y;
                }else{
                    maxY=this.getMaxCol(i);
                }
                
                for(int j=0; j<maxY; j++){
                    MX=MX+""+this.getValue(i, j);
                    if((j+1)<maxY){
                    MX=MX+" | ";
                    }     
                }
                MX=MX+"\n";
            }
            return MX;
    }
}
