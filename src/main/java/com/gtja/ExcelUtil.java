package com.gtja;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil {
	/** 
	 * excel����������� 
	 * ˭����˭����ر������ 
	 * @param os ����� 
	 * @param excelExtName excel�ļ�����չ����֧��xls��xlsx��������� 
	 * @param data  
	 * @throws IOException 
	 */  
	public static void writeExcel(OutputStream os, String excelExtName, Map<String, List<List<String>>> data) throws IOException{  
	    Workbook wb = null;  
	    try {  
	        if ("xls".equals(excelExtName)) {  
	            wb = new HSSFWorkbook();  
	        } else if ("xlsx".equals(excelExtName)) {  
	            wb = new XSSFWorkbook();  
	        } else {  
	            throw new Exception("��ǰ�ļ�����excel�ļ�");  
	        }  
	        for (String sheetName : data.keySet()) {  
	            Sheet sheet = wb.createSheet(sheetName);  
	            List<List<String>> rowList = data.get(sheetName);  
	            for (int i = 0; i < rowList.size(); i++) {  
	                List<String> cellList = rowList.get(i);  
	                Row row = sheet.createRow(i);  
	                for (int j = 0; j < cellList.size(); j++) {  
	                    Cell cell = row.createCell(j);  
	                    cell.setCellValue(cellList.get(j));  
	                }  
	            }  
	        }  
	        wb.write(os);  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (wb != null) {  
	            wb.close();  
	        }  
	    }  
	}  
	/** 
	 * excel����������� 
	 * ˭����˭����ر������ 
	 * @param os ����� 
	 * @param excelExtName excel�ļ�����չ����֧��xls��xlsx��������� 
	 * @param data excel���ݣ�map�е�key�Ǳ�ǩҳ�����ƣ�value��Ӧ��list�Ǳ�ǩҳ�е����ݡ�list�е���list�Ǳ�ǩҳ�е�һ�У���list�еĶ�����һ����Ԫ������ݣ������Ƿ���С��缸�м����Լ����ֵ�Ƕ��� 
	 * @throws IOException 
	 */  
	@SuppressWarnings("deprecation")
	public static void testWrite(OutputStream os, String excelExtName, Map<String, List<List<ExcelData>>> data) throws IOException{  
	    Workbook wb = null;  
	    CellStyle cellStyle = null;  
	    boolean isXls;  
	    try {  
	        if ("xls".equals(excelExtName)) {  
	            wb = new HSSFWorkbook();  
	            isXls = true;  
	        } else if ("xlsx".equals(excelExtName)) {  
	            wb = new XSSFWorkbook();  
	            isXls = false;  
	        } else {  
	            throw new Exception("��ǰ�ļ�����excel�ļ�");  
	        }  
	        cellStyle = wb.createCellStyle();  
	        if (isXls) {  
	            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	        } else {  
	            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
	            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
	        }  
	        for (String sheetName : data.keySet()) {  
	            Sheet sheet = wb.createSheet(sheetName);  
	            List<List<ExcelData>> rowList = data.get(sheetName);  
	            //i ����ڼ��� ��0��ʼ  
	            for (int i = 0; i < rowList.size(); i++) {  
	                List<ExcelData> cellList = rowList.get(i);  
	                Row row = sheet.createRow(i);  
	                int j = 0;//j ����ڼ��� ��0��ʼ  
	                for (ExcelData excelData : cellList) {  
	                    if (excelData != null) {  
	                        if (excelData.getColSpan() > 1 || excelData.getRowSpan() > 1) {  
	                            CellRangeAddress cra = new CellRangeAddress(i, i + excelData.getRowSpan() - 1, j, j + excelData.getColSpan() - 1);  
	                            sheet.addMergedRegion(cra);  
	                        }  
	                        Cell cell = row.createCell(j);  
	                        cell.setCellValue(excelData.getValue());  
	                        if (excelData.isAlignCenter()) {  
	                            cell.setCellStyle(cellStyle);  
	                        }  
	                        j = j + excelData.getColSpan();  
	                    } else {  
	                        j++;  
	                    }  
	                }  
	            }  
	        }  
	        wb.write(os);  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (wb != null) {  
	            wb.close();  
	        }  
	    }  
	}  
	public static void test() throws FileNotFoundException, IOException{
		Map<String, List<List<ExcelData>>> data = new LinkedHashMap();  
        List<List<ExcelData>> sheet1 = new ArrayList();//��һҳ  
          
        List<ExcelData> list1 = new ArrayList();//��һ��  
        ExcelData excelData = new ExcelData();//��һ����Ԫ��  
        excelData.setColSpan(6);  
        excelData.setRowSpan(1);  
        excelData.setValue("xxx");  
        excelData.setAlignCenter(true);  
        list1.add(excelData);  
          
        List<ExcelData> list2 = new ArrayList();//�ڶ���  
        excelData = new ExcelData();//��һ����Ԫ��  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("a");  
        list2.add(excelData);  
        excelData = new ExcelData();//�ڶ�����Ԫ��  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("b");  
        list2.add(excelData);  
        excelData = new ExcelData();//��������Ԫ��  
        excelData.setColSpan(2);  
        excelData.setRowSpan(4);  
        excelData.setValue("c");  
        excelData.setAlignCenter(true);  
        list2.add(excelData);  
        excelData = new ExcelData();//���ĸ���Ԫ��  
        excelData.setColSpan(2);  
        excelData.setRowSpan(2);  
        excelData.setValue("d");  
        excelData.setAlignCenter(true);  
        list2.add(excelData);  
          
        List<ExcelData> list3 = new ArrayList();//������  
        excelData = new ExcelData();//��һ����Ԫ��  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("e");  
        list3.add(excelData);  
        excelData = new ExcelData();//�ڶ�����Ԫ��  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("f");  
        list3.add(excelData);  
        list3.add(null);//��������Ԫ��  
        list3.add(null);//���ĸ���Ԫ��  
        list3.add(null);//�������Ԫ��  
        list3.add(null);//��������Ԫ��  
          
        List<ExcelData> list4 = new ArrayList();//������  
        excelData = new ExcelData();//��һ����Ԫ��  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("i");  
        list4.add(excelData);  
        excelData = new ExcelData();//�ڶ�����Ԫ��  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("j");  
        list4.add(excelData);  
        list4.add(null);//��������Ԫ��  
        list4.add(null);//���ĸ���Ԫ��  
        excelData = new ExcelData();//�������Ԫ��  
        excelData.setRowSpan(1);  
        excelData.setColSpan(1);  
        excelData.setValue("g");  
        list4.add(excelData);  
        excelData = new ExcelData();//��������Ԫ��  
        excelData.setRowSpan(1);  
        excelData.setColSpan(1);  
        excelData.setValue("h");  
        list4.add(excelData);  
          
        List<ExcelData> list5 = new ArrayList();//������  
        excelData = new ExcelData();//��һ����Ԫ��  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("k");  
        list5.add(excelData);  
        excelData = new ExcelData();//�ڶ�����Ԫ��  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("l");  
        list5.add(excelData);  
        list5.add(null);//��������Ԫ��  
        list5.add(null);//���ĸ���Ԫ��  
        excelData = new ExcelData();//�������Ԫ��  
        excelData.setRowSpan(1);  
        excelData.setColSpan(1);  
        excelData.setValue("m");  
        list5.add(excelData);  
        excelData = new ExcelData();//��������Ԫ��  
        excelData.setRowSpan(1);  
        excelData.setColSpan(1);  
        excelData.setValue("n");  
        list5.add(excelData);  
          
        sheet1.add(list1);  
        sheet1.add(list2);  
        sheet1.add(list3);  
        sheet1.add(list4);  
        sheet1.add(list5);  
          
        data.put("��1", sheet1);  
          
        testWrite(new FileOutputStream(new File("C:\\Users\\wanglq\\Desktop\\test.xlsx")), "xlsx", data);
	}
	
	/** 
	 * ������û�б����е�excel������ 
	 * ����   25��     ��   175cm 
	 * ����   22��     Ů   160cm 
	 * ÿһ�й���һ��map��keyֵ���б��⣬value����ֵ��û��ֵ�ĵ�Ԫ����valueֵΪnull 
	 * ���ؽ��������list��Ӧһ��excel�ļ����ڶ����list��Ӧһ��sheetҳ���������map��Ӧsheetҳ�е�һ�� 
	 * @throws Exception  
	 */  
	public static List<List<List<String>>> readExcelWithoutTitle(String filepath) throws Exception{  
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
	            throw new Exception("��ȡ�Ĳ���excel�ļ�");  
	        }  
	          
	        List<List<List<String>>> result = new ArrayList<List<List<String>>>();//��Ӧexcel�ļ�  
	          
	        int sheetSize = wb.getNumberOfSheets();  
	        for (int i = 0; i < sheetSize; i++) {//����sheetҳ  
	            Sheet sheet = wb.getSheetAt(i);  
	            List<List<String>> sheetList = new ArrayList<List<String>>();//��Ӧsheetҳ  
	              
	            int rowSize = sheet.getLastRowNum() + 1;  
	            for (int j = 0; j < rowSize; j++) {//������  
	                Row row = sheet.getRow(j);  
	                if (row == null) {//�Թ�����  
	                    continue;  
	                }  
	                int cellSize = row.getLastCellNum();//�����ж��ٸ���Ԫ��Ҳ�����ж�����  
	                List<String> rowList = new ArrayList<String>();//��Ӧһ��������  
	                for (int k = 0; k < cellSize; k++) {  
	                    Cell cell = row.getCell(k);  
	                    String value = null;  
	                    if (cell != null) {  
	                        value = cell.toString();  
	                    }  
	                    rowList.add(value);  
	                }  
	                sheetList.add(rowList);  
	            }  
	            result.add(sheetList);  
	        }  
	          
	        return result;  
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
	
	/** 
	 * ��ȡ
	 * @throws Exception  
	 */  
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
	            throw new Exception("不识别的格式");  
	        }  
            List<String> rowList = new ArrayList<String>();//��Ӧһ��������  
	        int sheetSize = wb.getNumberOfSheets();  
	        for (int i = 0; i < sheetSize; i++) {//����sheetҳ  
	            Sheet sheet = wb.getSheetAt(i);  
	              
	            int rowSize = sheet.getLastRowNum() + 1;  
	            for (int j = 0; j < rowSize; j++) {//������  
	                Row row = sheet.getRow(j);  
	                if (row == null) {//�Թ�����  
	                    continue;  
	                }  
	                int cellSize = row.getLastCellNum();//�����ж��ٸ���Ԫ��Ҳ�����ж�����  

	                for (int k = 0; k < cellSize; k++) {  
	                    Cell cell = row.getCell(k);  
	                    String value = null;  
	                    if (cell != null) {  
	                    	value = cell.toString(); 
	                    }  
	                    if(rowList.contains(value)){
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
	
	/** 
	 * �����ڵ�һ���Ǳ����е�excel������ 
	 * ����   ����  �Ա�  ��� 
	 * ����   25  ��   175 
	 * ����   22  Ů   160 
	 * ÿһ�й���һ��map��keyֵ���б��⣬value����ֵ��û��ֵ�ĵ�Ԫ����valueֵΪnull 
	 * ���ؽ��������list��Ӧһ��excel�ļ����ڶ����list��Ӧһ��sheetҳ���������map��Ӧsheetҳ�е�һ�� 
	 * @throws Exception  
	 */  
	public static List<List<Map<String, String>>> readExcelWithTitle(String filepath) throws Exception{  
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
	            throw new Exception("不识别的格式");  
	        }  
	          
	        List<List<Map<String, String>>> result = new ArrayList<List<Map<String,String>>>();//��Ӧexcel�ļ�  
	          
	        int sheetSize = wb.getNumberOfSheets();  
	        for (int i = 0; i < sheetSize; i++) {//����sheetҳ  
	            Sheet sheet = wb.getSheetAt(i);  
	            List<Map<String, String>> sheetList = new ArrayList<Map<String, String>>();//��Ӧsheetҳ  
	              
	            List<String> titles = new ArrayList<String>();//�������еı���  
	              
	            int rowSize = sheet.getLastRowNum() + 1;  
	            for (int j = 0; j < rowSize; j++) {
	                Row row = sheet.getRow(j);  
	                if (row == null) {
	                    continue;  
	                }  
	                int cellSize = row.getLastCellNum();
	                if (j == 0) {
	                    for (int k = 0; k < cellSize; k++) {  
	                        Cell cell = row.getCell(k);  
	                        titles.add(cell.toString());  
	                    }  
	                } else {
	                    Map<String, String> rowMap = new HashMap<String, String>();
	                    for (int k = 0; k < titles.size(); k++) {  
	                        Cell cell = row.getCell(k);  
	                        String key = titles.get(k);  
	                        String value = null;  
	                        if (cell != null) {  
	                            value = cell.toString();  
	                        }  
	                        rowMap.put(key, value);  
	                    }  
	                    sheetList.add(rowMap);  
	                }  
	            }  
	            result.add(sheetList);  
	        }  
	          
	        return result;  
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
	public static void writeExcelSimple(String excelExtName, List<List<String>> data) throws IOException{  
	    Workbook wb = null;  
	    String fileType = excelExtName.substring(excelExtName.lastIndexOf(".") + 1, excelExtName.length());  
	    FileOutputStream os = new FileOutputStream(new File(excelExtName));
	    try {    
	    	if (fileType.equals("xls")) {  
	            wb = new HSSFWorkbook();  
	        } else if (fileType.equals("xlsx")) {  
	            wb = new XSSFWorkbook();  
	        } else {  
	            throw new Exception("不识别的格式");  
	        }  
            Sheet sheet = wb.createSheet("result");  
            for (int i = 0; i < data.size(); i++) {  
                List<String> cellList = data.get(i);  
                Row row = sheet.createRow(i);  
                for (int j = 0; j < cellList.size(); j++) {  
                    Cell cell = row.createCell(j);  
                    cell.setCellValue(cellList.get(j));  
                }  
            }  
	        wb.write(os);  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (wb != null) {  
	            wb.close();  
	        }  
	        if (os != null) {  
	        	os.close();  
	        } 
	    }  
	}
}
