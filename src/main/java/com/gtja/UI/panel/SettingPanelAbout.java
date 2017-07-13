package com.gtja.UI.panel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gtja.UI.ConstantsUI;
import com.gtja.UI.MyIconButton;
import com.gtja.tools.PropertyUtil;

/**
 * �������
 * 
 * @author Bob
 *
 */
public class SettingPanelAbout extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 */
	public SettingPanelAbout() {
		initialize();
		addComponent();
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
		panelCenter.setLayout(new GridLayout(3, 1));

		// ͼ�ꡢ�汾Grid
		JPanel panelGridIcon = new JPanel();
		panelGridIcon.setBackground(ConstantsUI.MAIN_BACK_COLOR);
		panelGridIcon.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

		// ��ʼ�����
		MyIconButton icon = new MyIconButton(ConstantsUI.ICON_DATA_SYNC, ConstantsUI.ICON_DATA_SYNC,
				ConstantsUI.ICON_DATA_SYNC, "");
		JLabel labelName = new JLabel(ConstantsUI.APP_NAME);
		JLabel labelVersion = new JLabel(ConstantsUI.APP_VERSION);

		// ����
		labelName.setFont(ConstantsUI.FONT_NORMAL);
		labelVersion.setFont(ConstantsUI.FONT_NORMAL);

		// ��С
		Dimension size = new Dimension(200, 30);
		labelName.setPreferredSize(size);
		labelVersion.setPreferredSize(size);

		// ���Ԫ��
		panelGridIcon.add(icon);
		panelGridIcon.add(labelName);
		panelGridIcon.add(labelVersion);

		// ������� Grid
		JPanel panelGridHelp = new JPanel();
		panelGridHelp.setBackground(ConstantsUI.MAIN_BACK_COLOR);
		panelGridHelp.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

		// ��ʼ�����
		JLabel labelAdvice = new JLabel(PropertyUtil.getProperty("ds.ui.app.advice"));
		JLabel labelHelp = new JLabel(PropertyUtil.getProperty("ds.ui.app.help"));

		// ����
		labelAdvice.setFont(ConstantsUI.FONT_NORMAL);
		labelHelp.setFont(ConstantsUI.FONT_NORMAL);

		// ��С
		labelAdvice.setPreferredSize(ConstantsUI.LABLE_SIZE);
		labelHelp.setPreferredSize(ConstantsUI.LABLE_SIZE);

		// ���Ԫ��
		panelGridHelp.add(labelAdvice);
		panelGridHelp.add(labelHelp);

		panelCenter.add(panelGridIcon);
		// panelCenter.add(panelGridHelp);
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
		panelDown.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 15));

		JLabel labelInfo = new JLabel(PropertyUtil.getProperty("ds.ui.app.info"));
		labelInfo.setFont(ConstantsUI.FONT_NORMAL);
		labelInfo.setForeground(Color.gray);

		panelDown.add(labelInfo);

		return panelDown;
	}

}
