package com.gtja.dao;

import java.util.List;

public class DataTable {  
    List<DataRow> dataRow;  
    public DataTable(){}  
    public DataTable(List<DataRow> row){
    	
    }
      
    public List<DataRow> GetRow()  
    {  
        return dataRow;  
    }  
      
    public void SetRow(List<DataRow> row)  
    {  
    	dataRow = row;  
    }  
   
    public static void PrintTable(DataTable dt) {   
        for (DataRow r : dt.GetRow()) {   
            for (DataColumn c : r.GetColumn()) {   
                System.out.print(c.GetKey() + ":" + c.GetValue() + "  ");   
            }   
            System.out.println("");   
        }   
    }   
      
    public static int RowCount=0;  
    public static int ColumnCount=0;  
}  
