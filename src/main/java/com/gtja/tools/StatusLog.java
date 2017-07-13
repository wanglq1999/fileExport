package com.gtja.tools;

import org.apache.log4j.Logger;

import com.gtja.UI.panel.StatusPanel;


/**
 * ��־��װ�࣬����ϵͳ��Ϣ�������log�ļ����ļ�¼
 *
 * @author Bob
 */
public class StatusLog {
    private static Logger logger = Logger.getLogger(StatusLog.class);

    /**
     * ����״̬���״̬��Ϣ
     *
     * @param status
     */
    public static void setStatus(String status) {
        StatusPanel.labelStatus.setText(status);
    }

    /**
     * ����״̬���״̬����ϸ��Ϣ+д����־�ļ���Debugģʽ�£�
     *
     * @param statusDetail
     * @param level
     */
    public static void setStatusDetail(String statusDetail, Enum<LogLevel> level) {
        StatusPanel.labelStatusDetail.setText(PropertyUtil.getProperty("ds.tool.detail") + statusDetail);
        if ("true".equals(ConstantsTools.CONFIGER.getDebugMode())) {

            if (level.toString().equals("INFO")) {
                logger.info(statusDetail);
            } else if (level.toString().equals("DEBUG")) {
                logger.debug(statusDetail);
            }

        }
        if (level.toString().equals("WARN")) {
            logger.warn(statusDetail);
        } else if (level.toString().equals("ERROR")) {
            logger.error(statusDetail);
        } else if (level.toString().equals("FATAL")) {
            logger.fatal(statusDetail);
        }
    }

    /**
     * ����״̬�������һ��ͬ��ʱ�䣬��д��conf
     *
     * @param lastTime
     */
    public static void setLastTime(String lastTime) {
        StatusPanel.labelLastTime.setText(PropertyUtil.getProperty("ds.ui.status.lastSync") + lastTime);
        try {
            ConstantsTools.CONFIGER.setLastSyncTime(lastTime);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * ����״̬����еĳ���ʱ�䣬��д��conf
     *
     * @param keepTime
     */
    public static void setKeepTime(String keepTime) {
        StatusPanel.labelKeepTime.setText(PropertyUtil.getProperty("ds.ui.status.keepTime") + keepTime
                + PropertyUtil.getProperty("ds.ui.status.second"));
        try {
            ConstantsTools.CONFIGER.setLastKeepTime(keepTime);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * ����״̬�������һ��ͬ��ʱ��
     *
     * @param nextTime
     */
    public static void setNextTime(String nextTime) {
        StatusPanel.labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime") + nextTime);
    }

    /**
     * ����״̬����еĳɹ��ܴ�������д��conf
     *
     * @param success
     */
    public static void setSuccess(String success) {
        StatusPanel.labelSuccess.setText(PropertyUtil.getProperty("ds.ui.status.successTimes") + success);
        try {
            ConstantsTools.CONFIGER.setSuccessTime(success);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * ����״̬����е�ʧ���ܴ�������д��conf
     *
     * @param fail
     */
    public static void setFail(String fail) {
        StatusPanel.labelFail.setText(PropertyUtil.getProperty("ds.ui.status.failTimes") + fail);
        try {
            ConstantsTools.CONFIGER.setFailTime(fail);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }
}
