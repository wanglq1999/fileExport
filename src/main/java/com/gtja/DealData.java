package com.gtja;


import java.awt.TextArea;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gtja.dao.DBHelper;

public class DealData {
	
	public static long total = 0l;//ԭ�ļ��ܼ�¼��
	public static long emptyTotal = 0l;//ԭ�ļ���Ϊ�յ�����
	public static long repTotal = 0l;//ԭ�ļ����ظ����ֵ�����
	public static long noDBTotal = 0l;//���ݿ���δ�鵽��¼������
	public static long count = 0l;//������
	
	public static void dealDate(String sourceFile,String resultFile,TextArea text) throws Exception{
		
		total=0;
		emptyTotal=0;
		repTotal=0;
		noDBTotal=0;
		
		if(StringUtils.isEmpty(sourceFile)){
			throw new Exception("sourceFile����Ϊ��"+sourceFile);
		}
		if(StringUtils.isEmpty(resultFile)){
			throw new Exception("resultFile����Ϊ��"+resultFile);
		}
		
		text.append("\r\nsourceFileԭ�ļ�·��Ϊ"+sourceFile+"\r\n");
		text.setCaretPosition(text.getText().length());
		text.append("\r\nresultFileĿ���ļ�·��Ϊ"+resultFile+"\r\n");
		text.setCaretPosition(text.getText().length());

		String configFile = "C:\\gtjaData\\config.txt";
		String errorLog = "C:\\gtjaData\\error.log";
		BufferedWriter bufferedWriter = null;
		FileWriter fw = null;
		try{
			Map<String,String> map = ReadConfig.readConfig(configFile);
			text.append("\r\n�����ļ���ȡ���"+configFile+"\r\n");
			text.setCaretPosition(text.getText().length());
			fw = new FileWriter(errorLog);
			bufferedWriter= new BufferedWriter(fw);

			List<String> date = readExcelSimple(sourceFile);
			text.append("\r\nԭ�ļ���ȡ���"+sourceFile+"���� "+total+" ������"+"�����и����ǿ�ֵ��"+emptyTotal+" �����ظ����ֵ�"+repTotal+" ����"+"\r\n");
			text.setCaretPosition(text.getText().length());
			List cellList =new ArrayList();
			DBHelper dbHelper = new DBHelper();
			String userName = map.get("userName");
			String passwd = map.get("passwd");
			String dbip = map.get("dbip");
			String databaseName = map.get("databaseName");
		    String sDBUrl = "jdbc:sqlserver://"+dbip+";databaseName="+databaseName; 
			dbHelper.GetConn(userName, passwd,sDBUrl);
			text.append("\r\n���ݿ�"+dbip+"���ӳɹ�\r\n");
			text.setCaretPosition(text.getText().length());
			String tableName = map.get("tableName");
			String colNameOne = map.get("colNameOne");
			String sqlStart = "select * from "+tableName+" where "+colNameOne+" = '";
			String sqlend = "'";
			String colNames = map.get("colNames");
			String removeDuCols = map.get("removeDuCols");//��Ҫ���ص���
			List reDuCols = new ArrayList();
			
			if(!"".equals(removeDuCols)&&null!=removeDuCols){
				String[] colName = removeDuCols.split(",");
				for(String attribute : colName) {
					reDuCols.add(attribute);
				}
			}
			
			if("".equals(colNames)||null==colNames){
				throw new Exception("config�����ļ���colNames����Ϊ��");
			}
			String[] colName = colNames.split(",");
			int totalDB =0;
			List rowListColName =new ArrayList();
			for(String attribute : date) {
				
				if(attribute.indexOf(".")>-1){
					attribute=attribute.substring(0, attribute.lastIndexOf("."));
				}
				List rowList =new ArrayList();
				
				if(totalDB==0){
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
				Map mapDu = new HashMap();
				while(resultSet.next()){
					for(int i=0;i<colName.length;i++){
						if(count==0){
							cols[i]=new StringBuffer("");//���ȳ�ʼ��һ��StringBuffer
						}
						cols[i].append(resultSet.getString(colName[i]).trim()).append("��");
					}
					count ++;
				}
				if (count==0){
					for(String colTmp:colName){
						rowList.add("���ݿ���δ�鵽������¼");
					}
					noDBTotal++;
				}else{
					for(int i=0;i<cols.length;i++){
						
						if(reDuCols.contains(colName[i])){//���������Ҫȥ�أ����⴦��
							String temp = cols[i].substring(0, cols[i].lastIndexOf("��"));
							if(null==temp||"".equals(temp)){
								continue;
							}
							String[] allValue = temp.split("��");
							if(null==allValue){
								continue;
							}
							Set set = new HashSet();
							for(String value:allValue){
								set.add(value);//����set������ȥ��
							}
							Iterator<String> it = set.iterator();  
							StringBuffer duCol=new StringBuffer("");
							while (it.hasNext()) {  
							  String str = it.next();  
							  duCol.append(str).append("��");
							}  
							rowList.add(duCol.substring(0, duCol.lastIndexOf("��")));
						}else{
							rowList.add(cols[i].substring(0, cols[i].lastIndexOf("��")));
						}
					}
				}
				
				cellList.add(rowList);
				count++;
				totalDB++;
				if(count%5000==0){
					text.append("\r\nsĿǰ�����ݿ��ѯ"+count+"������\r\n");
					text.setCaretPosition(text.getText().length());
				}
			}
			dbHelper.CloseConn();
			resultFile = resultFile +"\\result.xlsx";
			ExcelUtil.writeExcelSimple(resultFile, cellList);
			text.append("\r\nд����ļ���ɣ��ļ����λ�ã�"+resultFile+"\r\n");
			text.setCaretPosition(text.getText().length());
			System.out.println("over");	
			text.append("�� "+String.valueOf(total)+"  ��¼�������� "+String.valueOf(noDBTotal)+" �������ݿ����޼�¼");
			text.setCaretPosition(text.getText().length());
		}catch(Exception e){
			e.printStackTrace();
			text.append("\r\n"+e.getMessage()+"\r\n");
			text.setCaretPosition(text.getText().length());
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

	public static List<String> readExcelSimple(String filepath) throws Exception{  
	    String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());  
	    InputStream is = null;  
	    Workbook wb = null;  
	    try {  
	        is = new FileInputStream(filepath);  
	          
	        if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook(is);  
	        } else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(is);  
	        } else {  
	            throw new Exception("��ʶ��ĸ�ʽ");  
	        }  
            List<String> rowList = new ArrayList<String>();
	        int sheetSize = wb.getNumberOfSheets();  
	        for (int i = 0; i < sheetSize; i++) { 
	            Sheet sheet = wb.getSheetAt(i);  
	              
	            int rowSize = sheet.getLastRowNum() + 1;  
	            for (int j = 0; j < rowSize; j++) {  
	                Row row = sheet.getRow(j);  
	                if (row == null) {
	                    continue;  
	                }  
	                int cellSize = row.getLastCellNum();

	                for (int k = 0; k < cellSize; k++) {
	                	total ++;
	                    Cell cell = row.getCell(k);  
	                    String value = null;  
	                    if (cell == null) {
	                    	emptyTotal++;
	                    	continue;
	                    } 
	                    value = cell.toString();
	                    
	                    if(StringUtils.isEmpty(value)){
	                    	emptyTotal++;
	                    	continue;
	                    }
	                    
	                    if(rowList.contains(value)){
	                    	repTotal++;
	                    	continue;
	                    }
	                    rowList.add(value);  
	                }  
	            }  
	        }  
	          
	        return rowList;  
	    } catch (FileNotFoundException e) {  
	        throw e;  
	    } finally {  
	        if (wb != null) {  
	            wb.close();  
	        }  
	        if (is != null) {  
	            is.close();  
	        }  
	    }  
	} 
}

