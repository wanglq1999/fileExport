package com.gtja.UI.panel;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.gtja.UI.AppMainWindow;
import com.gtja.UI.ConstantsUI;
import com.gtja.UI.MyIconButton;
import com.gtja.tools.PropertyUtil;

/**
 * ���������
 * 
 * @author Bob
 *
 */
public class ToolBarPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static MyIconButton buttonStatus;
	private static MyIconButton buttonDatabase;
	private static MyIconButton buttonSchedule;
	private static MyIconButton buttonBackup;
	private static MyIconButton buttonSetting;

	/**
	 * ����
	 */
	public ToolBarPanel() {
		initialize();
		addButtion();
		addListener();
	}

	/**
	 * ��ʼ��
	 */
	private void initialize() {
		Dimension preferredSize = new Dimension(48, ConstantsUI.MAIN_WINDOW_HEIGHT);
		this.setPreferredSize(preferredSize);
		this.setMaximumSize(preferredSize);
		this.setMinimumSize(preferredSize);
		this.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
		this.setLayout(new GridLayout(2, 1));
	}

	/**
	 * ��ӹ��߰�ť
	 */
	private void addButtion() {

		JPanel panelUp = new JPanel();
		panelUp.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
		panelUp.setLayout(new FlowLayout(-2, -2, -4));
		JPanel panelDown = new JPanel();
		panelDown.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
		panelDown.setLayout(new BorderLayout(0, 0));

		buttonStatus = new MyIconButton(ConstantsUI.ICON_STATUS_ENABLE, ConstantsUI.ICON_STATUS_ENABLE,
				ConstantsUI.ICON_STATUS, PropertyUtil.getProperty("ds.ui.status.title"));
		buttonDatabase = new MyIconButton(ConstantsUI.ICON_DATABASE, ConstantsUI.ICON_DATABASE_ENABLE,
				ConstantsUI.ICON_DATABASE, PropertyUtil.getProperty("ds.ui.database.title"));
		buttonSchedule = new MyIconButton(ConstantsUI.ICON_SCHEDULE, ConstantsUI.ICON_SCHEDULE_ENABLE,
				ConstantsUI.ICON_SCHEDULE, PropertyUtil.getProperty("ds.ui.schedule.title"));
		buttonBackup = new MyIconButton(ConstantsUI.ICON_BACKUP, ConstantsUI.ICON_BACKUP_ENABLE,
				ConstantsUI.ICON_BACKUP, PropertyUtil.getProperty("ds.ui.backup.title"));
		buttonSetting = new MyIconButton(ConstantsUI.ICON_SETTING, ConstantsUI.ICON_SETTING_ENABLE,
				ConstantsUI.ICON_SETTING, PropertyUtil.getProperty("ds.ui.setting.title"));

		panelUp.add(buttonStatus);
//		panelUp.add(buttonDatabase);
//		panelUp.add(buttonSchedule);
//		panelUp.add(buttonBackup);

//		panelDown.add(buttonSetting, BorderLayout.SOUTH);
		this.add(panelUp);
//		this.add(panelDown);

	}

	/**
	 * Ϊ����ť����¼���������
	 */
	private void addListener() {
		buttonStatus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonStatus.setIcon(ConstantsUI.ICON_STATUS_ENABLE);
				buttonDatabase.setIcon(ConstantsUI.ICON_DATABASE);
				buttonSchedule.setIcon(ConstantsUI.ICON_SCHEDULE);
				buttonBackup.setIcon(ConstantsUI.ICON_BACKUP);
				buttonSetting.setIcon(ConstantsUI.ICON_SETTING);

				AppMainWindow.mainPanelCenter.removeAll();
//				StatusPanel.setContent();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.statusPanel, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});
		buttonDatabase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
				buttonDatabase.setIcon(ConstantsUI.ICON_DATABASE_ENABLE);
				buttonSchedule.setIcon(ConstantsUI.ICON_SCHEDULE);
				buttonBackup.setIcon(ConstantsUI.ICON_BACKUP);
				buttonSetting.setIcon(ConstantsUI.ICON_SETTING);

				AppMainWindow.mainPanelCenter.removeAll();
				DatabasePanelFrom.setContent();
//				DatabasePanelTo.setContent();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.databasePanel, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});

		buttonSchedule.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
				buttonDatabase.setIcon(ConstantsUI.ICON_DATABASE);
				buttonSchedule.setIcon(ConstantsUI.ICON_SCHEDULE_ENABLE);
				buttonBackup.setIcon(ConstantsUI.ICON_BACKUP);
				buttonSetting.setIcon(ConstantsUI.ICON_SETTING);

				AppMainWindow.mainPanelCenter.removeAll();
				AppMainWindow.schedulePanel.setCurrentSchedule();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.schedulePanel, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});

		buttonBackup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
				buttonDatabase.setIcon(ConstantsUI.ICON_DATABASE);
				buttonSchedule.setIcon(ConstantsUI.ICON_SCHEDULE);
				buttonBackup.setIcon(ConstantsUI.ICON_BACKUP_ENABLE);
				buttonSetting.setIcon(ConstantsUI.ICON_SETTING);

				BackupPanel.initTableData();
				BackupPanel.tableFrom.validate();

				AppMainWindow.mainPanelCenter.removeAll();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.backupPanel, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});

		buttonSetting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonStatus.setIcon(ConstantsUI.ICON_STATUS);
				buttonDatabase.setIcon(ConstantsUI.ICON_DATABASE);
				buttonSchedule.setIcon(ConstantsUI.ICON_SCHEDULE);
				buttonBackup.setIcon(ConstantsUI.ICON_BACKUP);
				buttonSetting.setIcon(ConstantsUI.ICON_SETTING_ENABLE);

				AppMainWindow.mainPanelCenter.removeAll();
				SettingPanelOption.setCurrentOption();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.settingPanel, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});
	}
}
