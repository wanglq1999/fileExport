package com.gtja.UI.panel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.gtja.UI.AppMainWindow;
import com.gtja.UI.ConstantsUI;
import com.gtja.UI.MyIconButton;
import com.gtja.logic.ConstantsLogic;
import com.gtja.tools.FileUtils;
import com.gtja.tools.PropertyUtil;

/**
 * �������
 *
 * @author Bob
 */
public class BackupPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static JTable tableFrom;

    private static MyIconButton buttonNewBakFrom;

    private static Object[][] tableDatas;

    /**
     * ����
     */
    public BackupPanel() {
        initialize();
        initTableData();
        addComponent();
        addListener();
    }

    /**
     * ��ʼ�����
     */
    private void initialize() {
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * Ϊ���������
     */
    private void addComponent() {

        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
    }

    /**
     * ����ϲ�
     *
     * @return
     */
    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.backup.title"));
        labelTitle.setFont(ConstantsUI.FONT_TITLE);
        labelTitle.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);

        return panelUp;
    }

    /**
     * ����в�
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // �м����
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(1, 1));

        panelCenter.add(getPanelGridBakFrom());

        return panelCenter;
    }

    /**
     * ���ݿ���ԴGrid���
     *
     * @return
     */
    private JPanel getPanelGridBakFrom() {
        // ��Դ����Grid
        JPanel panelGridBakFrom = new JPanel();
        panelGridBakFrom.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridBakFrom.setLayout(new BorderLayout());

        JPanel panelFromControl = new JPanel();
        panelFromControl.setLayout(new GridLayout(1, 2));
        JPanel panelFromTable = new JPanel();
        panelFromTable.setLayout(new BorderLayout());

        // ��ʼ���������
        JPanel panelFromControlLeft = new JPanel();
        panelFromControlLeft.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));
        panelFromControlLeft.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        JPanel panelFromControlRight = new JPanel();
        panelFromControlRight.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 5));
        panelFromControlRight.setBackground(ConstantsUI.MAIN_BACK_COLOR);

        JLabel labelFrom = new JLabel(PropertyUtil.getProperty("ds.ui.database.label.to"));
        labelFrom.setFont(new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 18));
        labelFrom.setForeground(Color.gray);
        panelFromControlLeft.add(labelFrom);

        buttonNewBakFrom = new MyIconButton(ConstantsUI.ICON_NEW_BAK, ConstantsUI.ICON_NEW_BAK_ENABLE,
                ConstantsUI.ICON_NEW_BAK_DISABLE, "");
        MyIconButton buttonRecvBakFrom = new MyIconButton(ConstantsUI.ICON_RECOVER_BAK,
                ConstantsUI.ICON_RECOVER_BAK_ENABLE, ConstantsUI.ICON_RECOVER_BAK_DISABLE, "");
        MyIconButton buttonDelBakFrom = new MyIconButton(ConstantsUI.ICON_DEL_BAK, ConstantsUI.ICON_DEL_BAK_ENABLE,
                ConstantsUI.ICON_DEL_BAK_DISABLE, "");
        panelFromControlRight.add(buttonNewBakFrom);
        panelFromControlRight.add(buttonRecvBakFrom);
        panelFromControlRight.add(buttonDelBakFrom);

        panelFromControl.add(panelFromControlLeft);
        panelFromControl.add(panelFromControlRight);

        panelGridBakFrom.add(panelFromControl, BorderLayout.NORTH);

        // ��ʼ��������

        tableFrom = new JTable(tableDatas, new String[]{PropertyUtil.getProperty("ds.ui.backup.table.head0"), PropertyUtil.getProperty("ds.ui.backup.table.head1"), PropertyUtil.getProperty("ds.ui.backup.table.head2")});
        tableFrom.setFont(ConstantsUI.FONT_NORMAL);
        tableFrom.getTableHeader().setFont(ConstantsUI.FONT_NORMAL);
        tableFrom.getTableHeader().setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        tableFrom.setRowHeight(31);
        tableFrom.setGridColor(ConstantsUI.TABLE_LINE_COLOR);
        tableFrom.setSelectionBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        // �����п�
        tableFrom.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableFrom.getColumnModel().getColumn(0).setMaxWidth(50);
        tableFrom.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableFrom.getColumnModel().getColumn(2).setMaxWidth(150);

        JScrollPane panelScroll = new JScrollPane(tableFrom);
        panelScroll.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridBakFrom.add(panelScroll, BorderLayout.CENTER);

        return panelGridBakFrom;
    }

    public static void initTableData() {
        File bakupFilesDir = new File(ConstantsLogic.PATH_MYSQL_BAK);
        if (!bakupFilesDir.exists()) {
            bakupFilesDir.mkdirs();
        }
        File bakupFiles[] = bakupFilesDir.listFiles();
        tableDatas = new Object[bakupFiles.length][3];
        for (int i = 0; i < bakupFiles.length; i++) {
            tableDatas[i] = new Object[]{i + 1, bakupFiles[i].getName(),
                    FileUtils.FormetFileSize(bakupFiles[i].length())};
        }
    }

    private void addListener() {
        buttonNewBakFrom.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AppMainWindow.dialog.setVisible(true);

            }
        });

    }

}
