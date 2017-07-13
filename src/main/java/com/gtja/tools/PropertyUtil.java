package com.gtja.tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gtja.UI.panel.StatusPanel;

/**
 * Created by zhouy on 2017/2/27.
 */
public class PropertyUtil {
	private static Logger logger = Logger.getLogger(PropertyUtil.class);
    /**
     * 获取property
     * @param key
     * @return
     */
    public static String getProperty(String key){
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream (new FileInputStream(ConstantsTools.PATH_PROPERTY));
            pps.load(in);
            String value = pps.getProperty(key);
            logger.info("配置信息："+key+"="+value);
            return value;

        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
