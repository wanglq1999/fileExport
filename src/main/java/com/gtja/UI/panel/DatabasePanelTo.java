package com.gtja.UI.panel;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.gtja.UI.AppMainWindow;
import com.gtja.UI.ConstantsUI;
import com.gtja.UI.MyIconButton;
import com.gtja.tools.ConstantsTools;
import com.gtja.tools.PropertyUtil;

/**
 * Ŀ�����ݿ����
 *
 * @author Bob
 */
public class DatabasePanelTo extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonTestLink;
    private static MyIconButton buttonSave;
    private static JTextField textFieldDatabaseHost;
    private static JTextField textFieldDatabaseName;
    private static JTextField textFieldDatabaseUser;
    private static JPasswordField passwordFieldDatabasePassword;

    private static Logger logger = Logger.getLogger(DatabasePanelTo.class);

    /**
     * ����
     */
    public DatabasePanelTo() {
        initialize();
        addComponent();
        setContent();
        addListener();
    }

    /**
     * ��ʼ��
     */
    private void initialize() {
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * ������
     */
    private void addComponent() {

        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);

    }

    /**
     * �в����
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // �м����
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(2, 1));

        // ����Grid
        JPanel panelGridSetting = new JPanel();
        panelGridSetting.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridSetting.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        // ��ʼ�����
        JLabel labelDatabaseType = new JLabel(PropertyUtil.getProperty("ds.ui.database.type"));
        JLabel labelDatabaseHost = new JLabel(PropertyUtil.getProperty("ds.ui.database.host"));
        JLabel labelDatabaseName = new JLabel(PropertyUtil.getProperty("ds.ui.database.name"));
        JLabel labelDatabaseUser = new JLabel(PropertyUtil.getProperty("ds.ui.database.user"));
        JLabel labelDatabasePassword = new JLabel(PropertyUtil.getProperty("ds.ui.database.password"));
        JComboBox<String> comboxDatabaseType = new JComboBox<String>();
        comboxDatabaseType.addItem("MySQL");
        comboxDatabaseType.setEditable(false);
        textFieldDatabaseHost = new JTextField();
        textFieldDatabaseName = new JTextField();
        textFieldDatabaseUser = new JTextField();
        passwordFieldDatabasePassword = new JPasswordField();

        // ����
        labelDatabaseType.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseHost.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseName.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabaseUser.setFont(ConstantsUI.FONT_NORMAL);
        labelDatabasePassword.setFont(ConstantsUI.FONT_NORMAL);
        comboxDatabaseType.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseHost.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseName.setFont(ConstantsUI.FONT_NORMAL);
        textFieldDatabaseUser.setFont(ConstantsUI.FONT_NORMAL);
        passwordFieldDatabasePassword.setFont(ConstantsUI.FONT_NORMAL);

        // ��С
        labelDatabaseType.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseHost.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseName.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabaseUser.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        labelDatabasePassword.setPreferredSize(ConstantsUI.LABLE_SIZE_ITEM);
        comboxDatabaseType.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseHost.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseName.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        textFieldDatabaseUser.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);
        passwordFieldDatabasePassword.setPreferredSize(ConstantsUI.TEXT_FIELD_SIZE_ITEM);

        // ���Ԫ��
        panelGridSetting.add(labelDatabaseType);
        panelGridSetting.add(comboxDatabaseType);
        panelGridSetting.add(labelDatabaseHost);
        panelGridSetting.add(textFieldDatabaseHost);
        panelGridSetting.add(labelDatabaseName);
        panelGridSetting.add(textFieldDatabaseName);
        panelGridSetting.add(labelDatabaseUser);
        panelGridSetting.add(textFieldDatabaseUser);
        panelGridSetting.add(labelDatabasePassword);
        panelGridSetting.add(passwordFieldDatabasePassword);

        panelCenter.add(panelGridSetting);
        return panelCenter;
    }

    /**
     * �ײ����
     *
     * @return
     */
    private JPanel getDownPanel() {
        JPanel panelDown = new JPanel();
        panelDown.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 15));

        buttonTestLink = new MyIconButton(ConstantsUI.ICON_TEST_LINK, ConstantsUI.ICON_TEST_LINK_ENABLE,
                ConstantsUI.ICON_TEST_LINK_DISABLE, "");
        buttonSave = new MyIconButton(ConstantsUI.ICON_SAVE, ConstantsUI.ICON_SAVE_ENABLE,
                ConstantsUI.ICON_SAVE_DISABLE, "");
        panelDown.add(buttonTestLink);
        panelDown.add(buttonSave);

        return panelDown;
    }

    /**
     * �����ı�������
     */
    public static void setContent() {
        textFieldDatabaseHost.setText(ConstantsTools.CONFIGER.getHostTo());
        textFieldDatabaseName.setText(ConstantsTools.CONFIGER.getNameTo());

        String user = "";
        String password = "";
        try {
//            DESPlus des = new DESPlus();
//            user = des.decrypt(ConstantsTools.CONFIGER.getUserTo());
//            password = des.decrypt(ConstantsTools.CONFIGER.getPasswordTo());
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.decode") + e.toString());
            e.printStackTrace();
        }
        textFieldDatabaseUser.setText(user);
        passwordFieldDatabasePassword.setText(password);

    }

    /**
     * Ϊ����������¼�����
     */
    private void addListener() {
        buttonSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    ConstantsTools.CONFIGER.setHostTo(textFieldDatabaseHost.getText());
                    ConstantsTools.CONFIGER.setNameTo(textFieldDatabaseName.getText());

                    String password = "";
                    String user = "";
                    try {
//                        DESPlus des = new DESPlus();
//                        user = des.encrypt(textFieldDatabaseUser.getText());
//                        password = des.encrypt(new String(passwordFieldDatabasePassword.getPassword()));
                    } catch (Exception e1) {
                        logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.encode") + e1.toString());
                        e1.printStackTrace();
                    }
                    ConstantsTools.CONFIGER.setUserTo(user);
                    ConstantsTools.CONFIGER.setPasswordTo(password);

                    JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.save.success"), PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.PLAIN_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.ERROR_MESSAGE);
                    logger.error("Write to xml file error" + e1.toString());
                }

            }
        });

        buttonTestLink.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
//                    DbUtilMySQL dbMySQL = DbUtilMySQL.getInstance();
//                    String DBUrl = textFieldDatabaseHost.getText();
//                    String DBName = textFieldDatabaseName.getText();
//                    String DBUser = textFieldDatabaseUser.getText();
//                    String DBPassword = new String(passwordFieldDatabasePassword.getPassword());
//                    Connection conn = dbMySQL.testConnection(DBUrl, DBName, DBUser, DBPassword);
//                    if (conn == null) {
//                        JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.fail"), PropertyUtil.getProperty("ds.ui.tips"),
//                                JOptionPane.ERROR_MESSAGE);
//                    } else {
//                        JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.success"), PropertyUtil.getProperty("ds.ui.tips"),
//                                JOptionPane.PLAIN_MESSAGE);
//                    }

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(AppMainWindow.databasePanel, PropertyUtil.getProperty("ds.ui.database.err.link.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

    }
}
