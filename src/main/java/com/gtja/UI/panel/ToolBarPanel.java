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
 * 工具栏面板
 * 
 * @author Bob
 *
 */
public class ToolBarPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static MyIconButton buttonStatus;

	/**
	 * 构造
	 */
	public ToolBarPanel() {
		initialize();
		addButtion();
		addListener();
	}

	/**
	 * 初始化
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
	 * 添加工具按钮
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

		panelUp.add(buttonStatus);

		this.add(panelUp);
	}

	/**
	 * 为各按钮添加事件动作监听
	 */
	private void addListener() {
		buttonStatus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				buttonStatus.setIcon(ConstantsUI.ICON_STATUS_ENABLE);

				AppMainWindow.mainPanelCenter.removeAll();
//				StatusPanel.setContent();
				AppMainWindow.mainPanelCenter.add(AppMainWindow.statusPanel, BorderLayout.CENTER);

				AppMainWindow.mainPanelCenter.updateUI();

			}
		});
	}
}
