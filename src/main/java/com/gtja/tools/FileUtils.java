package com.gtja.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.opencsv.CSVReader;

/**
 * �ļ�������
 * 
 * @author Bob
 *
 */
public class FileUtils {
	/**
	 * �����ļ�
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		// �½��ļ����������������л���
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// �½��ļ���������������л���
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// ��������
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// ˢ�´˻���������
		outBuff.flush();

		// �ر���
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}

	/**
	 * �����ļ���
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// �½�Ŀ��Ŀ¼
		(new File(targetDir)).mkdirs();
		// ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// Դ�ļ�
				File sourceFile = file[i];
				// Ŀ���ļ�
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// ׼�����Ƶ�Դ�ļ���
				String dir1 = sourceDir + "/" + file[i].getName();
				// ׼�����Ƶ�Ŀ���ļ���
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	/**
	 * �����ļ��� MD5 ֵ
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[8192];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * �����ļ���SHA-1ֵ
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileSha1(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[8192];
		int len;
		try {
			digest = MessageDigest.getInstance("SHA-1");
			in = new FileInputStream(file);
			while ((len = in.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * ����ļ���
	 * 
	 * @param dir
	 */
	public static void clearDirectiory(String dir) {
		File dirFile = new File(dir);
		for (File file : dirFile.listFiles()) {
			file.delete();
		}

	}

	/**
	 * ��ȡcsv�ļ���List
	 * 
	 * @param csvFile
	 * @return ����List��ÿ�е��ַ������飩
	 * @throws IOException
	 */
	public static ArrayList<String[]> getCsvFileContentList(File csvFile) throws IOException {
		FileReader fReader = null;
		CSVReader csvReader = null;
		ArrayList<String[]> list = new ArrayList<String[]>();

		try {
			// ��ʼ��reader
			fReader = new FileReader(csvFile);
			csvReader = new CSVReader(fReader);
			// ��ȡ����csv�ļ�
			list = (ArrayList<String[]>) csvReader.readAll();
			return list;

		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (fReader != null) {
				try {
					fReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (csvReader != null) {
				try {
					csvReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ��ȡsql�ļ���List
	 * 
	 * @param sqlFile
	 * @return ����List��ÿ�е��ַ�����
	 * @throws IOException
	 */
	public static ArrayList<String> getSqlFileContentList(File sqlFile) throws IOException {
		ArrayList<String> list = new ArrayList<String>();

		BufferedReader br = null;
		try {
			// ��ʼ��reader
			br = new BufferedReader(new InputStreamReader(new FileInputStream(sqlFile), "UTF-8"));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt = lineTxt.trim();
				if ("".equals(lineTxt) || lineTxt.startsWith("//")) {
					// ����ע�ͺͿ���
					continue;
				} else {
					if (lineTxt.contains("//")) {
						// ȥ��ע��
						lineTxt = lineTxt.substring(0, lineTxt.indexOf("//")).trim();
					}
					list.add(lineTxt);
				}
			}
			return list;

		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ת���ļ���С
	 * 
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
}
