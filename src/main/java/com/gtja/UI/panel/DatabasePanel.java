package com.gtja.UI.panel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gtja.UI.AppMainWindow;
import com.gtja.UI.ConstantsUI;
import com.gtja.tools.PropertyUtil;

/**
 * ���ݿ��������
 * 
 * @author Bob
 *
 */
public class DatabasePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static JPanel panelFrom;
	public static JPanel panelTo;
	public static JPanel databaseSettingPanel;
	private static JPanel databaseSettingPanelFrom;
	private static JPanel databaseSettingPanelTo;

	/**
	 * ����
	 */
	public DatabasePanel() {
		initialize();
		addComponent();
		addListener();
	}

	/**
	 * ��ʼ�����
	 */
	private void initialize() {
		this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
		this.setLayout(new BorderLayout());
		databaseSettingPanelFrom = new DatabasePanelFrom();
//		databaseSettingPanelTo = new DatabasePanelTo();
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

		JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.database.title"));
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
		panelCenter.setLayout(new BorderLayout());

		// ���ݿ��б�Panel
		JPanel panelList = new JPanel();
		Dimension preferredSize = new Dimension(245, ConstantsUI.MAIN_WINDOW_HEIGHT);
		panelList.setPreferredSize(preferredSize);
		panelList.setBackground(new Color(62, 62, 62));
		panelList.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		panelFrom = new JPanel();
		panelFrom.setBackground(new Color(69, 186, 121));
		panelFrom.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
		Dimension preferredSizeListItem = new Dimension(245, 48);
		panelFrom.setPreferredSize(preferredSizeListItem);
//		panelTo = new JPanel();
//		panelTo.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);
//		panelTo.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 13));
//		panelTo.setPreferredSize(preferredSizeListItem);

		JLabel labelFrom = new JLabel(PropertyUtil.getProperty("ds.ui.database.label.from"));
//		JLabel labelTo = new JLabel(PropertyUtil.getProperty("ds.ui.database.label.to"));
		Font fontListItem = new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 15);
		labelFrom.setFont(fontListItem);
//		labelTo.setFont(fontListItem);
		labelFrom.setForeground(Color.white);
//		labelTo.setForeground(Color.white);
		panelFrom.add(labelFrom);
//		panelTo.add(labelTo);

		panelList.add(panelFrom);
//		panelList.add(panelTo);

		// ���ݿ�����Panel

		databaseSettingPanel = new JPanel();
		databaseSettingPanel.setBackground(ConstantsUI.MAIN_BACK_COLOR);
		databaseSettingPanel.setLayout(new BorderLayout());
		databaseSettingPanel.add(databaseSettingPanelFrom);

		panelCenter.add(panelList, BorderLayout.WEST);
		panelCenter.add(databaseSettingPanel, BorderLayout.CENTER);

		return panelCenter;
	}

	/**
	 * ������������¼�����
	 */
	private void addListener() {
		panelFrom.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelFrom.setBackground(new Color(69, 186, 121));
				panelTo.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);

				DatabasePanel.databaseSettingPanel.removeAll();
				DatabasePanelFrom.setContent();
				DatabasePanel.databaseSettingPanel.add(databaseSettingPanelFrom);
				AppMainWindow.databasePanel.updateUI();

			}
		});
/**
 * 
 
		panelTo.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelTo.setBackground(new Color(69, 186, 121));
				panelFrom.setBackground(ConstantsUI.TOOL_BAR_BACK_COLOR);

				DatabasePanel.databaseSettingPanel.removeAll();
				DatabasePanelTo.setContent();
				DatabasePanel.databaseSettingPanel.add(databaseSettingPanelTo);
				AppMainWindow.databasePanel.updateUI();

			}
		});
*/
	}
}
