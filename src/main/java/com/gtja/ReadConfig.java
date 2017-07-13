package com.gtja;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ReadConfig {
	public static Map<String,String> readConfig(String configFile) throws Exception{
		File inFile = null;
		FileInputStream input=null;
		BufferedReader bufferedReader = null;
		Map<String,String> map = new HashMap<String,String>();
		try{
			inFile = new File(configFile);
			input = new FileInputStream(inFile);
			InputStreamReader inputStreamReader = new InputStreamReader(input,"GBK");
			bufferedReader = new BufferedReader(inputStreamReader);
			String readString = null;
			while ((readString = bufferedReader.readLine())!= null){
				String[] config = readString.split("=");
				if(config.length>1){
					map.put(config[0].trim(), config[1].trim());
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(input!=null){
				try{
					input.close();
				}catch(Exception e1){
				}
			}
			if(bufferedReader!=null){
				try{
					bufferedReader.close();
				}catch(Exception e2){
				}
			}
		}
		
		return map;
	}
	public static void writeLog(String configFile) throws Exception{
		BufferedWriter bufferedWriterExc = new BufferedWriter(new FileWriter(configFile));
	}
}
