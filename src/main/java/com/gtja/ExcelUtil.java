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
	 * excel导出到输出流 
	 * 谁调用谁负责关闭输出流 
	 * @param os 输出流 
	 * @param excelExtName excel文件的扩展名，支持xls和xlsx，不带点号 
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
	            throw new Exception("当前文件不是excel文件");  
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
	 * excel导出到输出流 
	 * 谁调用谁负责关闭输出流 
	 * @param os 输出流 
	 * @param excelExtName excel文件的扩展名，支持xls和xlsx，不带点号 
	 * @param data excel数据，map中的key是标签页的名称，value对应的list是标签页中的数据。list中的子list是标签页中的一行，子list中的对象是一个单元格的数据，包括是否居中、跨几行几列以及存的值是多少 
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
	            throw new Exception("当前文件不是excel文件");  
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
	            //i 代表第几行 从0开始  
	            for (int i = 0; i < rowList.size(); i++) {  
	                List<ExcelData> cellList = rowList.get(i);  
	                Row row = sheet.createRow(i);  
	                int j = 0;//j 代表第几列 从0开始  
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
        List<List<ExcelData>> sheet1 = new ArrayList();//第一页  
          
        List<ExcelData> list1 = new ArrayList();//第一行  
        ExcelData excelData = new ExcelData();//第一个单元格  
        excelData.setColSpan(6);  
        excelData.setRowSpan(1);  
        excelData.setValue("xxx");  
        excelData.setAlignCenter(true);  
        list1.add(excelData);  
          
        List<ExcelData> list2 = new ArrayList();//第二行  
        excelData = new ExcelData();//第一个单元格  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("a");  
        list2.add(excelData);  
        excelData = new ExcelData();//第二个单元格  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("b");  
        list2.add(excelData);  
        excelData = new ExcelData();//第三个单元格  
        excelData.setColSpan(2);  
        excelData.setRowSpan(4);  
        excelData.setValue("c");  
        excelData.setAlignCenter(true);  
        list2.add(excelData);  
        excelData = new ExcelData();//第四个单元格  
        excelData.setColSpan(2);  
        excelData.setRowSpan(2);  
        excelData.setValue("d");  
        excelData.setAlignCenter(true);  
        list2.add(excelData);  
          
        List<ExcelData> list3 = new ArrayList();//第三行  
        excelData = new ExcelData();//第一个单元格  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("e");  
        list3.add(excelData);  
        excelData = new ExcelData();//第二个单元格  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("f");  
        list3.add(excelData);  
        list3.add(null);//第三个单元格  
        list3.add(null);//第四个单元格  
        list3.add(null);//第五个单元格  
        list3.add(null);//第六个单元格  
          
        List<ExcelData> list4 = new ArrayList();//第四行  
        excelData = new ExcelData();//第一个单元格  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("i");  
        list4.add(excelData);  
        excelData = new ExcelData();//第二个单元格  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("j");  
        list4.add(excelData);  
        list4.add(null);//第三个单元格  
        list4.add(null);//第四个单元格  
        excelData = new ExcelData();//第五个单元格  
        excelData.setRowSpan(1);  
        excelData.setColSpan(1);  
        excelData.setValue("g");  
        list4.add(excelData);  
        excelData = new ExcelData();//第六个单元格  
        excelData.setRowSpan(1);  
        excelData.setColSpan(1);  
        excelData.setValue("h");  
        list4.add(excelData);  
          
        List<ExcelData> list5 = new ArrayList();//第五行  
        excelData = new ExcelData();//第一个单元格  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("k");  
        list5.add(excelData);  
        excelData = new ExcelData();//第二个单元格  
        excelData.setColSpan(1);  
        excelData.setRowSpan(1);  
        excelData.setValue("l");  
        list5.add(excelData);  
        list5.add(null);//第三个单元格  
        list5.add(null);//第四个单元格  
        excelData = new ExcelData();//第五个单元格  
        excelData.setRowSpan(1);  
        excelData.setColSpan(1);  
        excelData.setValue("m");  
        list5.add(excelData);  
        excelData = new ExcelData();//第六个单元格  
        excelData.setRowSpan(1);  
        excelData.setColSpan(1);  
        excelData.setValue("n");  
        list5.add(excelData);  
          
        sheet1.add(list1);  
        sheet1.add(list2);  
        sheet1.add(list3);  
        sheet1.add(list4);  
        sheet1.add(list5);  
          
        data.put("表1", sheet1);  
          
        testWrite(new FileOutputStream(new File("C:\\Users\\wanglq\\Desktop\\test.xlsx")), "xlsx", data);
	}
	
	/** 
	 * 适用于没有标题行的excel，例如 
	 * 张三   25岁     男   175cm 
	 * 李四   22岁     女   160cm 
	 * 每一行构成一个map，key值是列标题，value是列值。没有值的单元格其value值为null 
	 * 返回结果最外层的list对应一个excel文件，第二层的list对应一个sheet页，第三层的map对应sheet页中的一行 
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
	            throw new Exception("读取的不是excel文件");  
	        }  
	          
	        List<List<List<String>>> result = new ArrayList<List<List<String>>>();//对应excel文件  
	          
	        int sheetSize = wb.getNumberOfSheets();  
	        for (int i = 0; i < sheetSize; i++) {//遍历sheet页  
	            Sheet sheet = wb.getSheetAt(i);  
	            List<List<String>> sheetList = new ArrayList<List<String>>();//对应sheet页  
	              
	            int rowSize = sheet.getLastRowNum() + 1;  
	            for (int j = 0; j < rowSize; j++) {//遍历行  
	                Row row = sheet.getRow(j);  
	                if (row == null) {//略过空行  
	                    continue;  
	                }  
	                int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列  
	                List<String> rowList = new ArrayList<String>();//对应一个数据行  
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
	 * 读取
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
	            throw new Exception("读取的不是excel文件");  
	        }  
            List<String> rowList = new ArrayList<String>();//对应一个数据行  
	        int sheetSize = wb.getNumberOfSheets();  
	        for (int i = 0; i < sheetSize; i++) {//遍历sheet页  
	            Sheet sheet = wb.getSheetAt(i);  
	              
	            int rowSize = sheet.getLastRowNum() + 1;  
	            for (int j = 0; j < rowSize; j++) {//遍历行  
	                Row row = sheet.getRow(j);  
	                if (row == null) {//略过空行  
	                    continue;  
	                }  
	                int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列  

	                for (int k = 0; k < cellSize; k++) {  
	                    Cell cell = row.getCell(k);  
	                    String value = null;  
	                    if (cell != null) {  
	                    	value = cell.toString(); 
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
	 * 适用于第一行是标题行的excel，例如 
	 * 姓名   年龄  性别  身高 
	 * 张三   25  男   175 
	 * 李四   22  女   160 
	 * 每一行构成一个map，key值是列标题，value是列值。没有值的单元格其value值为null 
	 * 返回结果最外层的list对应一个excel文件，第二层的list对应一个sheet页，第三层的map对应sheet页中的一行 
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
	            throw new Exception("读取的不是excel文件");  
	        }  
	          
	        List<List<Map<String, String>>> result = new ArrayList<List<Map<String,String>>>();//对应excel文件  
	          
	        int sheetSize = wb.getNumberOfSheets();  
	        for (int i = 0; i < sheetSize; i++) {//遍历sheet页  
	            Sheet sheet = wb.getSheetAt(i);  
	            List<Map<String, String>> sheetList = new ArrayList<Map<String, String>>();//对应sheet页  
	              
	            List<String> titles = new ArrayList<String>();//放置所有的标题  
	              
	            int rowSize = sheet.getLastRowNum() + 1;  
	            for (int j = 0; j < rowSize; j++) {//遍历行  
	                Row row = sheet.getRow(j);  
	                if (row == null) {//略过空行  
	                    continue;  
	                }  
	                int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列  
	                if (j == 0) {//第一行是标题行  
	                    for (int k = 0; k < cellSize; k++) {  
	                        Cell cell = row.getCell(k);  
	                        titles.add(cell.toString());  
	                    }  
	                } else {//其他行是数据行  
	                    Map<String, String> rowMap = new HashMap<String, String>();//对应一个数据行  
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
	            throw new Exception("读取的不是excel文件");  
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
