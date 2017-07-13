package com.gtja.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {  
    Connection connection = null;  
      
    public boolean GetConn(String sUser, String sPwd, String dburl)throws  Exception{  
        if(connection!=null)return true;  
        try {             
            String sDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";   

            Class.forName(sDriverName);  
            connection = DriverManager.getConnection(dburl, sUser, sPwd);  
  
        } catch (Exception ex) {  
            throw ex;
        }  
        return true;  
    }  
      
    public void CloseConn()  
    {  
        try {  
        	connection.close();  
        	connection = null;  
        } catch (Exception ex) {  
            connection=null;   
        }  
    }  
   
      
      
    public ResultSet GetResultSet(String sSQL,Object[] objParams)  throws  Exception
    {  
        ResultSet rs=null;  
        try  
        {  
            PreparedStatement ps = connection.prepareStatement(sSQL);  
            if(objParams!=null)  
            {  
                for(int i=0;i< objParams.length;i++)  
                {  
                    ps.setObject(i+1, objParams[i]);  
                }  
            }  
            rs=ps.executeQuery();  
        }catch(Exception ex)  
        {  
            throw ex;
//            CloseConn();  
        }  
        finally  
        {  
//            CloseConn();            
        }  
        return rs;  
    }  
      
    public Object GetSingle(String sSQL,Object... objParams)  
    {  
//        GetConn();  
        try  
        {  
            PreparedStatement ps = connection.prepareStatement(sSQL);  
            if(objParams!=null)  
            {  
                for(int i=0;i< objParams.length;i++)  
                {  
                    ps.setObject(i+1, objParams[i]);  
                }  
            }  
            ResultSet rs=ps.executeQuery();  
            if(rs.next())  
                return rs.getString(1);//������1��ʼ  
        }catch(Exception ex)  
        {  
            System.out.println(ex.getMessage());  
        }  
        finally  
        {  
            CloseConn();              
        }  
        return null;  
    }  
      
    public DataTable GetDataTable(String sSQL,Object... objParams)  
    {  
//        GetConn();  
        DataTable dt=null;  
        try  
        {  
            PreparedStatement ps = connection.prepareStatement(sSQL);  
            if(objParams!=null)  
            {  
                for(int i=0;i< objParams.length;i++)  
                {  
                    ps.setObject(i+1, objParams[i]);  
                }  
            }  
            ResultSet rs=ps.executeQuery();  
            ResultSetMetaData rsmd=rs.getMetaData();  
              
            List<DataRow> row=new ArrayList<DataRow>(); //�������м���  
            List<DataColumn> col=null; //�������м���  
            DataRow r=null;// ����һ��  
            DataColumn c=null;//����һ��  
              
            String columnName;  
            Object value;  
            int iRowCount=0;  
            while(rs.next())//��ʼѭ����ȡ��ÿ�������в���һ�м�¼  
            {  
                iRowCount++;  
                col=new ArrayList<DataColumn>();//��ʼ���м���  
                for(int i=1;i<=rsmd.getColumnCount();i++)  
                {  
                    columnName=rsmd.getColumnName(i);  
                    value=rs.getObject(columnName);  
                    c=new DataColumn(columnName,value);//��ʼ����Ԫ��  
                    col.add(c); //������Ϣ���뵽�м���  
                }  
                r=new DataRow(col);//��ʼ����Ԫ��  
                row.add(r);//������Ϣ���뵽�м���  
            }  
            dt = new DataTable(row);  
            dt.RowCount=iRowCount;  
            dt.ColumnCount = rsmd.getColumnCount();  
        }catch(Exception ex)  
        {  
            System.out.println(ex.getMessage());  
        }  
        finally  
        {  
            CloseConn();              
        }  
        return dt;  
    }  
      
    public int UpdateData(String sSQL,Object[] objParams)  
    {  
//        GetConn();  
        int iResult=0;  
          
        try  
        {  
            PreparedStatement ps = connection.prepareStatement(sSQL);  
            if(objParams!=null)  
            {  
                for(int i=0;i< objParams.length;i++)  
                {  
                    ps.setObject(i+1, objParams[i]);  
                }  
            }  
            iResult = ps.executeUpdate(sSQL);  
        }catch(Exception ex)  
        {  
            System.out.println(ex.getMessage());  
            return -1;  
        }  
        finally  
        {  
            CloseConn();              
        }  
        return iResult;  
    }  
      
      
  
} 