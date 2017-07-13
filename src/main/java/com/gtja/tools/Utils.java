package com.gtja.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * С������
 * 
 * @author Bob
 *
 */
public class Utils {

	/**
	 * ��ȡϵͳ��ǰʱ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		return df.format(new Date());
	}

	/**
	 * ��ȡ�����ļ���(ϵͳ��ǰʱ��yyyy-MM-dd HHmmss)
	 * 
	 * @return
	 */
	public static String getCurrentTimeForBakName() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HHmmss");// �������ڸ�ʽ
		return df.format(new Date());
	}

	/**
	 * ������һ��ͬ��ʱ��
	 * 
	 * @return
	 */
	public static String getNextSyncTime() {

		// 1����ȡ��ǰʱ�䣬��ȡ����ʱ��������long���͵ģ���λ�Ǻ���
		long currentTime = System.currentTimeMillis();
		// 2������������ϼ��϶�ʱִ��ʱ�䣺
		String schedule = ConstantsTools.CONFIGER.getSchedule();
		if ("true,false,false,false,false,false,false".equals(schedule)) {
			currentTime += 5 * 60 * 1000;
		} else if ("false,true,false,false,false,false,false".equals(schedule)) {
			currentTime += 15 * 60 * 1000;
		} else if ("false,false,true,false,false,false,false".equals(schedule)) {
			currentTime += 30 * 60 * 1000;
		} else if ("false,false,false,true,false,false,false".equals(schedule)) {
			currentTime += 60 * 60 * 1000;
		} else if ("false,false,false,false,true,false,false".equals(schedule)) {
			currentTime += 24 * 60 * 60 * 1000;
		} else if ("false,false,false,false,false,true,false".equals(schedule)) {
			currentTime += 7 * 24 * 60 * 60 * 1000;
		} else if ("false,false,false,false,false,false,true".equals(schedule)) {
			return "���� " + ConstantsTools.CONFIGER.getScheduleFixTime();
		}

		// 3����ʽ��ʱ�䣬��ȡ���ľ��ǵ�ǰʱ����϶�ʱִ��֮���ʱ��
		Date date = new Date(currentTime);
		// 4������ʱ���ʽ������
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * ��ȡĳ�ַ������ַ��������е�index
	 * 
	 * @param arr
	 * @param key
	 * @return ����ĳ�ַ������ַ��������е�index�����Ҳ�������-1
	 */
	public static int getStrArrIndex(String arr[], String key) {

		for (int i = 0; i < arr.length; i++) {
			if (key.equals(arr[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * �Ƚ�����key��ȫ��ͬ��map�����Ƿ���ȫһ��
	 * 
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static boolean mapCompare4PrimKey(Map<String, String> map1, Map<String, String> map2) {
		boolean ifEquals = true;
		for (String key : map1.keySet()) {
			if (!map1.get(key).equals(map2.get(key))) {
				ifEquals = false;
			}
		}

		return ifEquals;
	}

	/**
	 * ������ת��Ϊȫƴ
	 * 
	 * @param src
	 * @return
	 */
	public static String getPingYin(String src) {

		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// �ж��Ƿ�Ϊ�����ַ�
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else
					t4 += Character.toString(t1[i]);
			}
			// System.out.println(t4);
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	/**
	 * ��ȡlinkedHashSet��Ԫ�ص�����λ��
	 * 
	 * @param linkedHashSet
	 * @param string
	 * @return
	 */
	public static int getIndexInLinkedHashSet(LinkedHashSet<String> linkedHashSet, String string) {
		int index = -1;
		Iterator<String> linkedSetStringIt = linkedHashSet.iterator();
		while (linkedSetStringIt.hasNext()) {
			index++;
			String temp = linkedSetStringIt.next();
			if (temp.equals(string)) {
				return index;
			}

		}

		return -1;
	}

}
