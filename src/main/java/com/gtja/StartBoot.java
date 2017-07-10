package com.gtja;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gtja.dao.DBHelper;


public class StartBoot {

	public static void main(String[] args) throws Exception {
		String configFile = "C:\\gtjaData\\config.txt";
		String errorLog = "C:\\gtjaData\\error.log";
		BufferedWriter bufferedWriter = null;
		FileWriter fw = null;
		try{
			Map<String,String> map = ReadConfig.readConfig(configFile);
			fw = new FileWriter(errorLog);
			bufferedWriter= new BufferedWriter(fw);
	
	
			String sourceFile = map.get("sourceFile");
			String result = map.get("destFile");
			List<String> date = ExcelUtil.readExcelSimple(sourceFile);
			List cellList =new ArrayList();
			DBHelper dbHelper = new DBHelper();
			String userName = map.get("userName");
			String passwd = map.get("passwd");
			String dbip = map.get("dbip");
			String databaseName = map.get("databaseName");
		    String sDBUrl = "jdbc:sqlserver://"+dbip+";databaseName="+databaseName; 
			dbHelper.GetConn(userName, passwd,sDBUrl);
			String tableName = map.get("tableName");
			String colNameOne = map.get("colNameOne");
			String sqlStart = "select * from "+tableName+" where "+colNameOne+" = '";
			String sqlend = "'";
			String colNames = map.get("colNames");
			if("".equals(colNames)||null==colNames){
				throw new Exception("配置文件中colNames为空");
			}
			String[] colName = colNames.split(",");
//			String colNameThr = map.get("colNameThr"); 
			int total =0;
			int other =0;
			List rowListColName =new ArrayList();
			for(String attribute : date) {

				List rowList =new ArrayList();
				
				if(total==0){
					rowListColName.add(colNameOne);
					for(String colTmp:colName){
						rowListColName.add(colTmp);
					}
					cellList.add(rowListColName);
				}
				
				rowList.add(attribute);
				String sql = sqlStart+attribute+sqlend;
				ResultSet resultSet = dbHelper.GetResultSet(sql, null);
				int count = 0;
				StringBuffer[] cols = new StringBuffer[colName.length];
				while(resultSet.next()){
//					System.out.println(resultSet.getString(colNameOne)+","+resultSet.getString(colNameTwo)+","+resultSet.getString("department"));
//					custname.append(resultSet.getString(colNameTwo)).append("、");
//					department.append(resultSet.getString(colNameThr)).append("、");
					for(int i=0;i<colName.length;i++){
						if(count==0){
							cols[i]=new StringBuffer("");
						}
						cols[i].append(resultSet.getString(colName[i]).trim()).append("、");
					}
					count ++;
				}
				if (count==0){
					for(String colTmp:colName){
						rowList.add("数据库中无此客户代码");
					}
					other++;
				}else{
					for(int i=0;i<cols.length;i++){
						rowList.add(cols[i].substring(0, cols[i].lastIndexOf("、")));
					}
				}
				
				cellList.add(rowList);
				total++;
			}
			dbHelper.CloseConn();
			ExcelUtil.writeExcelSimple(result, cellList);
			System.out.println("over");	
			bufferedWriter.write("共处理数据"+String.valueOf(total)+"条，其中数据库中无记录的有"+String.valueOf(other)+"条");
			bufferedWriter.newLine();
		}catch(Exception e){
			e.printStackTrace();
			bufferedWriter.write(e.toString());
		}finally{
			if(bufferedWriter!=null){
				bufferedWriter.close();
			}
			if(fw!=null){
				fw.close();
			}
		}
		
			
	}

}
