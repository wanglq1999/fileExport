package com.gtja.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.gtja.UI.panel.BackupPanel;
import com.gtja.UI.panel.DatabasePanel;
import com.gtja.UI.panel.SchedulePanel;
import com.gtja.UI.panel.SettingPanel;
import com.gtja.UI.panel.StatusPanel;
import com.gtja.UI.panel.ToolBarPanel;
import com.gtja.tools.PropertyUtil;

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
    public static DatabasePanel databasePanel;
    public static SchedulePanel schedulePanel;
    public static BackupPanel backupPanel;
    public static SettingPanel settingPanel;
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

    /**
     * ���ݿⱸ�ݶԻ���
     */
    private void addDialog() {
        // ���ݿⱸ�ݶԻ���
        dialog = new JDialog(frame, PropertyUtil.getProperty("ds.ui.mainwindow.dialog.newBackUp"), true);
        dialog.setBounds(460, 220, 400, 250);
        JPanel panelDialog = new JPanel(new BorderLayout());
        panelDialog.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        JPanel panelDialogCenter = new JPanel(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 10));
        JPanel panelDialogDown = new JPanel(new GridLayout(1, 2));
        JPanel grid1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        JPanel grid2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JLabel labelType = new JLabel(PropertyUtil.getProperty("ds.ui.mainwindow.dialog.type"));
        JLabel labelComment = new JLabel(PropertyUtil.getProperty("ds.ui.mainwindow.dialog.remarks"));
        JLabel labelProgress = new JLabel(PropertyUtil.getProperty("ds.ui.mainwindow.dialog.progress"));
        JComboBox<String> comboxType = new JComboBox<>(new String[]{PropertyUtil.getProperty("ds.ui.mainwindow.dialog.comboxType0"),
                PropertyUtil.getProperty("ds.ui.mainwindow.dialog.comboxType1"), PropertyUtil.getProperty("ds.ui.mainwindow.dialog.comboxType2"),
                PropertyUtil.getProperty("ds.ui.mainwindow.dialog.comboxType3")});
        JTextField textFieldComment = new JTextField();
        JProgressBar progressbar = new JProgressBar();

        labelType.setFont(ConstantsUI.FONT_NORMAL);
        labelComment.setFont(ConstantsUI.FONT_NORMAL);
        labelProgress.setFont(ConstantsUI.FONT_NORMAL);
        comboxType.setFont(ConstantsUI.FONT_NORMAL);
        textFieldComment.setFont(ConstantsUI.FONT_NORMAL);

        Dimension preferredSize = new Dimension(250, 30);
        comboxType.setPreferredSize(preferredSize);
        textFieldComment.setPreferredSize(preferredSize);
        progressbar.setPreferredSize(preferredSize);

        panelDialogCenter.add(labelType);
        panelDialogCenter.add(comboxType);
        panelDialogCenter.add(labelComment);
        panelDialogCenter.add(textFieldComment);
        panelDialogCenter.add(labelProgress);
        panelDialogCenter.add(progressbar);

        JButton buttonSure = new JButton(PropertyUtil.getProperty("ds.ui.sure"));
        JButton buttonCancel = new JButton(PropertyUtil.getProperty("ds.ui.cancel"));
        buttonSure.setFont(ConstantsUI.FONT_NORMAL);
        buttonCancel.setFont(ConstantsUI.FONT_NORMAL);

        grid1.add(buttonSure);
        grid2.add(buttonCancel);
        panelDialogDown.add(grid1);
        panelDialogDown.add(grid2);

        panelDialog.add(panelDialogCenter, BorderLayout.CENTER);
        panelDialog.add(panelDialogDown, BorderLayout.SOUTH);

        dialog.add(panelDialog);

        buttonCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);

            }
        });
    }

}
