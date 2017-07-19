package com.gtja.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.gtja.UI.panel.StatusPanel;
import com.gtja.UI.panel.ToolBarPanel;

/**
 * ������ڣ�������Frame
 *
 * @author Bob
 */
public class AppMainWindow {
    private static Logger logger = Logger.getLogger(AppMainWindow.class);

    private JFrame frame;

    private static JPanel mainPanel;
    public static JPanel mainPanelCenter;

    public static StatusPanel statusPanel;
    // �½�����dialog
    public static JDialog dialog;

    /**
     * �������main
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppMainWindow window = new AppMainWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * ���죬����APP
     */
    public AppMainWindow() {
        initialize();
        StatusPanel.buttonStartSchedule.doClick();
    }

    /**
     * ��ʼ��frame����
     */
    private void initialize() {
        PropertyConfigurator
                .configure(ConstantsUI.CURRENT_DIR + File.separator + "config" + File.separator + "log4j.properties");
        logger.info("==================AppInitStart");
        // ����ϵͳĬ����ʽ
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
        	logger.error("����ϵͳĬ����ʽ����"+e.getMessage());
        }

        // ��ʼ��������
        frame = new JFrame();
        frame.setBounds(ConstantsUI.MAIN_WINDOW_X, ConstantsUI.MAIN_WINDOW_Y, ConstantsUI.MAIN_WINDOW_WIDTH,
                ConstantsUI.MAIN_WINDOW_HEIGHT);
        frame.setTitle(ConstantsUI.APP_NAME_NEW);
        frame.setIconImage(ConstantsUI.IMAGE_ICON);
        frame.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        mainPanel = new JPanel(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        ToolBarPanel toolbar = new ToolBarPanel();
        statusPanel = new StatusPanel();
//        databasePanel = new DatabasePanel();
//        schedulePanel = new SchedulePanel();
//        backupPanel = new BackupPanel();
//        settingPanel = new SettingPanel();

        mainPanel.add(toolbar, BorderLayout.WEST);

        mainPanelCenter = new JPanel(true);
        mainPanelCenter.setLayout(new BorderLayout());
        mainPanelCenter.add(statusPanel, BorderLayout.CENTER);

        mainPanel.add(mainPanelCenter, BorderLayout.CENTER);

        // ������ݿⱸ�ݶԻ���
//        addDialog();

        frame.add(mainPanel);

        frame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (!StatusPanel.buttonStartSchedule.isEnabled()) {
//                    JOptionPane.showMessageDialog(AppMainWindow.statusPanel,
//                            PropertyUtil.getProperty("ds.ui.mainwindow.exitconfirm"), "Sorry~", JOptionPane.WARNING_MESSAGE);
                	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        logger.info("==================AppInitEnd");
    }

}
