package com.gtja.UI.panel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.gtja.DealData;
import com.gtja.UI.AppMainWindow;
import com.gtja.UI.ConstantsUI;
import com.gtja.UI.MyIconButton;
import com.gtja.tools.ConstantsTools;
import com.gtja.tools.PropertyUtil;
import com.gtja.tools.StatusLog;

/**
 * 状态面板
 *
 * @author Bob
 */
public class StatusPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(StatusPanel.class);

    public static MyIconButton buttonStartSchedule;
    public static MyIconButton buttonStop;
    public static MyIconButton buttonStartNow;

    public static JProgressBar progressTotal;
    public static JProgressBar progressCurrent;

    public static JLabel labelStatus;
    public static JLabel labelStatusDetail;
    public static JLabel labelFrom;
    public static JLabel labelTo;
    public static JLabel labelLastTime;
    public static JLabel labelKeepTime;
    public static JLabel labelNextTime;
    public static JLabel labelSuccess;
    public static JLabel labelFail;
    public static JButton fileOpen;
    private static JLabel labelLog;
    
    private static JTextField fileName;
    private static JTextField fileNameDest;
    public static JButton fileOpenDest;
    
    public static TextArea textAreaOutput;

    private static ScheduledExecutorService service;

    public static boolean isRunning = false;
    

    public static boolean isFirst = true;

    /**
     * 构造
     */
    public StatusPanel() {
        super(true);
        initialize();
        addComponent();
//        setContent();
        addListenerNew();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * 添加组件
     */
    private void addComponent() {

        this.add(getUpPanelNew(), BorderLayout.NORTH);
//        this.add(getCenterPanelNew(), BorderLayout.WEST);
        this.add(getCenterPanelNew(), BorderLayout.CENTER);
//        this.add(getDownPanel(), BorderLayout.SOUTH);

    }

    /**
     * 上部面板
     *
     * @return
     */
    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.status.title"));
        labelTitle.setFont(ConstantsUI.FONT_TITLE);
        labelTitle.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);
        
        return panelUp;
    }
    
    private JPanel getUpPanelNew() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(ConstantsUI.MAIN_BACK_COLOR);
//        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));
        panelUp.setLayout(new GridLayout(2, 1));

        //增加选择文件按钮
        JPanel panelSoure = new JPanel();
        panelSoure.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelSoure.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));
        JLabel nameSoure = new JLabel("源 文 件：");
        nameSoure.setFont(ConstantsUI.FONT_NORMAL);
        nameSoure.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelSoure.add(nameSoure);
        
        
        fileName = new JTextField(60);
        panelSoure.add(fileName);
        
        fileOpen=new JButton("选择");
        panelSoure.add(fileOpen);
        
        //增加目标文件按钮
        JPanel panelDest = new JPanel();
        panelDest.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDest.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 5));
        JLabel nameDest = new JLabel("目标文件：");
        nameDest.setFont(ConstantsUI.FONT_NORMAL);
        nameDest.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);
        panelDest.add(nameDest);
        
        
        fileNameDest = new JTextField(60);
        panelDest.add(fileNameDest);
        
        fileOpenDest=new JButton("选择");
        panelDest.add(fileOpenDest);
        
        panelUp.add(panelSoure);
        panelUp.add(panelDest);

        return panelUp;
    }
    /**
     * 中部面板
     *
     * @return
     */
    private JPanel getCenterPanelNew() {
    	 // 中间面板
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(2, 1));
//        panelCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 150));
        
        JPanel panelGrid1 = new JPanel();
        panelGrid1.setBackground(ConstantsUI.MAIN_BACK_COLOR);
//        panelGrid1.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 15));
        panelGrid1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelGrid1.setPreferredSize(new Dimension(50, 50));
        buttonStartSchedule = new MyIconButton(ConstantsUI.ICON_START_SCHEDULE, ConstantsUI.ICON_START_SCHEDULE_ENABLE,
                ConstantsUI.ICON_START_SCHEDULE_DISABLE, "");
        panelGrid1.add(buttonStartSchedule);
        panelCenter.add(panelGrid1);
        
        JPanel panelGrid2 = new JPanel();
        panelGrid2.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGrid2.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelGrid1.setPreferredSize(new Dimension(150, 150));
        
        textAreaOutput = new TextArea("这里显示处理过程消息",10,80,TextArea.SCROLLBARS_VERTICAL_ONLY);  
        
//        textAreaOutput = new JTextArea("这里显示处理过程信息", 100, 80);
//        textAreaOutput.setSelectedTextColor(Color.RED);
//        textArea.setCaretColor(Color.RED);
//        textAreaOutput.setLineWrap(true);        //激活自动换行功能 
//        textAreaOutput.setWrapStyleWord(true);            // 激活断行不断字功能
//        
//        panelGrid1.add(new JScrollPane(textArea));
//        textAreaOutput.setName("222");
        panelGrid2.add(textAreaOutput);
        panelCenter.add(panelGrid2);
        
        return panelCenter;
    }
    
    /**
     * 中部面板
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // 中间面板
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(4, 1));

        // 状态Grid
        JPanel panelGridStatus = new JPanel();
        panelGridStatus.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridStatus.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        labelStatus = new JLabel(PropertyUtil.getProperty("ds.ui.status.ready"));
        labelStatusDetail = new JLabel(PropertyUtil.getProperty("ds.ui.status.detail"));
        labelStatus.setFont(new Font(PropertyUtil.getProperty("ds.ui.font.family"), 1, 15));
        labelStatusDetail.setFont(ConstantsUI.FONT_NORMAL);

        labelStatus.setPreferredSize(ConstantsUI.LABLE_SIZE);
        labelStatusDetail.setPreferredSize(ConstantsUI.LABLE_SIZE);

        panelGridStatus.add(labelStatus);
        panelGridStatus.add(labelStatusDetail);

        // 来源/目标 Grid
        JPanel panelGridFromTo = new JPanel();
        panelGridFromTo.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridFromTo.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        labelFrom = new JLabel();
        labelTo = new JLabel();
        labelFrom.setFont(ConstantsUI.FONT_NORMAL);
        labelTo.setFont(ConstantsUI.FONT_NORMAL);
        labelFrom.setPreferredSize(ConstantsUI.LABLE_SIZE);
        labelTo.setPreferredSize(ConstantsUI.LABLE_SIZE);

        panelGridFromTo.add(labelFrom);
        panelGridFromTo.add(labelTo);

        // 详情Grid
        JPanel panelGridDetail = new JPanel();
        panelGridDetail.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridDetail.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        labelLastTime = new JLabel();
        labelKeepTime = new JLabel();
        labelNextTime = new JLabel();
        labelNextTime.setText(PropertyUtil.getProperty("ds.ui.schedule.nextTime"));
        labelSuccess = new JLabel();
        labelFail = new JLabel();
        labelLog = new JLabel(PropertyUtil.getProperty("ds.ui.status.logDetail"));

        labelLastTime.setFont(ConstantsUI.FONT_NORMAL);
        labelKeepTime.setFont(ConstantsUI.FONT_NORMAL);
        labelNextTime.setFont(ConstantsUI.FONT_NORMAL);
        labelSuccess.setFont(ConstantsUI.FONT_NORMAL);
        labelFail.setFont(ConstantsUI.FONT_NORMAL);
        labelLog.setFont(ConstantsUI.FONT_NORMAL);
        labelLastTime.setPreferredSize(new Dimension(240, 30));
        labelKeepTime.setPreferredSize(new Dimension(300, 30));
        labelNextTime.setPreferredSize(ConstantsUI.LABLE_SIZE);
        labelSuccess.setPreferredSize(new Dimension(240, 30));
        labelFail.setPreferredSize(new Dimension(236, 30));
        labelLog.setPreferredSize(ConstantsUI.LABLE_SIZE);
        labelLog.setForeground(ConstantsUI.TOOL_BAR_BACK_COLOR);

        panelGridDetail.add(labelLastTime);
        panelGridDetail.add(labelKeepTime);
        panelGridDetail.add(labelNextTime);
        panelGridDetail.add(labelSuccess);
        panelGridDetail.add(labelFail);
        panelGridDetail.add(labelLog);

        // 进度Grid
        JPanel panelGridProgress = new JPanel();
        panelGridProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGridProgress.setLayout(new GridLayout(2, 1, ConstantsUI.MAIN_H_GAP, 0));
        JPanel panelCurrentProgress = new JPanel();
        panelCurrentProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelCurrentProgress.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 20));
        JPanel panelTotalProgress = new JPanel();
        panelTotalProgress.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelTotalProgress.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 0));

        JLabel labelCurrent = new JLabel(PropertyUtil.getProperty("ds.ui.status.progress.current"));
        JLabel labelTotal = new JLabel(PropertyUtil.getProperty("ds.ui.status.progress.total"));
        labelCurrent.setFont(ConstantsUI.FONT_NORMAL);
        labelTotal.setFont(ConstantsUI.FONT_NORMAL);
        progressCurrent = new JProgressBar();
        progressTotal = new JProgressBar();

        Dimension preferredSizeLabel = new Dimension(80, 30);
        labelCurrent.setPreferredSize(preferredSizeLabel);
        labelTotal.setPreferredSize(preferredSizeLabel);
        Dimension preferredSizeProgressbar = new Dimension(640, 20);
        progressCurrent.setPreferredSize(preferredSizeProgressbar);
        progressTotal.setPreferredSize(preferredSizeProgressbar);

        panelCurrentProgress.add(labelCurrent);
        panelCurrentProgress.add(progressCurrent);
        panelTotalProgress.add(labelTotal);
        panelTotalProgress.add(progressTotal);

        panelGridProgress.add(panelCurrentProgress);
        panelGridProgress.add(panelTotalProgress);

        panelCenter.add(panelGridStatus);
        panelCenter.add(panelGridFromTo);
        panelCenter.add(panelGridDetail);
        panelCenter.add(panelGridProgress);

        return panelCenter;
    }

    /**
     * 底部面板
     *
     * @return
     */
    private JPanel getDownPanel() {
        JPanel panelDown = new JPanel();
        panelDown.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelDown.setLayout(new GridLayout(1, 2));
        JPanel panelGrid1 = new JPanel();
        panelGrid1.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGrid1.setLayout(new FlowLayout(FlowLayout.LEFT, ConstantsUI.MAIN_H_GAP, 15));
        JPanel panelGrid2 = new JPanel();
        panelGrid2.setBackground(ConstantsUI.MAIN_BACK_COLOR);
        panelGrid2.setLayout(new FlowLayout(FlowLayout.RIGHT, ConstantsUI.MAIN_H_GAP, 15));

        buttonStartSchedule = new MyIconButton(ConstantsUI.ICON_START_SCHEDULE, ConstantsUI.ICON_START_SCHEDULE_ENABLE,
                ConstantsUI.ICON_START_SCHEDULE_DISABLE, "");
        buttonStop = new MyIconButton(ConstantsUI.ICON_STOP, ConstantsUI.ICON_STOP_ENABLE,
                ConstantsUI.ICON_STOP_DISABLE, "");
        buttonStop.setEnabled(false);
        buttonStartNow = new MyIconButton(ConstantsUI.ICON_SYNC_NOW, ConstantsUI.ICON_SYNC_NOW_ENABLE,
                ConstantsUI.ICON_SYNC_NOW_DISABLE, "");
        panelGrid1.add(buttonStartSchedule);
        panelGrid1.add(buttonStop);
        panelGrid2.add(buttonStartNow);

        panelDown.add(panelGrid1);
        panelDown.add(panelGrid2);
        return panelDown;
    }

    /**
     * 设置状态面板组件内容
     */
    public static void setContent() {

        labelFrom.setText(
                PropertyUtil.getProperty("ds.ui.status.from") + ConstantsTools.CONFIGER.getHostFrom() + "/" + ConstantsTools.CONFIGER.getNameFrom());
        labelTo.setText(PropertyUtil.getProperty("ds.ui.status.to") + ConstantsTools.CONFIGER.getHostTo() + "/" + ConstantsTools.CONFIGER.getNameTo());
        labelLastTime.setText(PropertyUtil.getProperty("ds.ui.status.lastSync") + ConstantsTools.CONFIGER.getLastSyncTime());
        labelKeepTime.setText(PropertyUtil.getProperty("ds.ui.status.keepTime") + ConstantsTools.CONFIGER.getLastKeepTime() + PropertyUtil.getProperty("ds.ui.status.second"));

        labelSuccess.setText(PropertyUtil.getProperty("ds.ui.status.successTimes") + ConstantsTools.CONFIGER.getSuccessTime());
        labelFail.setText(PropertyUtil.getProperty("ds.ui.status.failTimes") + ConstantsTools.CONFIGER.getFailTime());

    }

    /**
     * 为各组件添加事件监听
     */
    private void addListener() {
    	
    	fileOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning == false) {
                	JFileChooser jfc=new JFileChooser();  
                    jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
                    jfc.showDialog(new JLabel(), "选择");  
                    File file=jfc.getSelectedFile();  
                    if(file.isDirectory()){
                    	fileName.setText(file.getAbsolutePath());
                        System.out.println("文件夹:"+file.getAbsolutePath());  
                    }else if(file.isFile()){  
                    	fileName.setText(file.getAbsolutePath());
                        System.out.println("文件:"+file.getAbsolutePath());  
                    }  
                    System.out.println(jfc.getSelectedFile().getName());  
                }
            }
        });
    	
        buttonStartNow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning == false) {
                    buttonStartNow.setEnabled(false);
                    buttonStartSchedule.setEnabled(false);
                    StatusPanel.setContent();
                    StatusPanel.progressTotal.setValue(0);
                    StatusPanel.progressCurrent.setValue(0);
                    labelStatus.setText(PropertyUtil.getProperty("ds.ui.status.manu"));
//                    ExecuteThread syncThread = new ExecuteThread();
//                    syncThread.start();
                }
            }
        });
        buttonStartSchedule.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                buttonStartSchedule.setEnabled(false);
                buttonStop.setEnabled(true);
//                StatusPanel.setContent();

//                StatusPanel.progressTotal.setValue(0);
//                StatusPanel.progressCurrent.setValue(0);
//                labelStatus.setText(PropertyUtil.getProperty("ds.ui.status.scheduledRunning"));
            }
        });
        buttonStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buttonStartSchedule.setEnabled(true);
                StatusPanel.buttonStartNow.setEnabled(true);
                service.shutdown();
                StatusLog.setNextTime("");
                labelStatus.setText(PropertyUtil.getProperty("ds.ui.status.ready"));
                buttonStop.setEnabled(false);
            }
        });
        labelLog.addMouseListener(new MouseListener() {

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
                labelLog.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().open(new File(ConstantsUI.CURRENT_DIR + File.separator + "log"));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    logger.error(e1.toString());
                }
            }
        });
    }
    
    /**
     * 为各组件添加事件监听
     */
    private void addListenerNew() {
    	
    	fileOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	JFileChooser jfc=new JFileChooser();  
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
//                jfc.showDialog(new JLabel(), "选择");
                JFrame fileFrame = new JFrame();
                fileFrame.setIconImage(ConstantsUI.IMAGE_ICON);
                jfc.showDialog(fileFrame, "选择");
                File file=jfc.getSelectedFile();  
                if(file.isDirectory()){
                	JOptionPane.showMessageDialog(AppMainWindow.statusPanel,
                            "目标只能选择目录，不能选择具体文件", "Sorry~", JOptionPane.WARNING_MESSAGE);
                }else if(file.isFile()){  
                	fileName.setText(file.getAbsolutePath());
                    System.out.println("文件:"+file.getAbsolutePath());  
                }  
                System.out.println(jfc.getSelectedFile().getName());  
            }
        });
    	
    	fileOpenDest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	JFileChooser jfc=new JFileChooser();  
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//目标文件只选择目录
//                JLabel chLabel = new JLabel();
//                chLabel.setIcon(ConstantsUI.ICON_DATA_SYNC);
//                jfc.showDialog(chLabel, "选择");
                JFrame fileFrame = new JFrame();
                fileFrame.setIconImage(ConstantsUI.IMAGE_ICON);
                jfc.showDialog(fileFrame, "选择");
                File file=jfc.getSelectedFile();  
                if(file.isDirectory()){
                	fileNameDest.setText(file.getAbsolutePath());
                    System.out.println("文件夹:"+file.getAbsolutePath());  
                }else if(file.isFile()){  
//                    	fileNameDest.setText(file.getAbsolutePath());
//                        System.out.println("文件:"+file.getAbsolutePath());
                  JOptionPane.showMessageDialog(AppMainWindow.statusPanel,
                  "原文件只支持excel类型，不能是一个目录", "Sorry~", JOptionPane.WARNING_MESSAGE);
                }  
                System.out.println(jfc.getSelectedFile().getName());  
            }
        });
    	 buttonStartSchedule.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) {
            	 
            	 //判断是否首次初始化时进入此方法，首次时按钮置亮，且不开始业务逻辑
            	 if(isFirst){
            		 isFirst = false;
                     buttonStartSchedule.setEnabled(true); 
            	 }else{
            		 //主动点击按钮时
                     buttonStartSchedule.setEnabled(false); 
                	 StatusPanel.businessStart();//开始业务逻辑处理
                	 buttonStartSchedule.setEnabled(true);
            	 }
                 
             }
         });
    }

    /**
     * 获取指定时间对应的毫秒数
     *
     * @param time "HH:mm:ss"
     * @return
     */
    private static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static void businessStart(){
    	 String sourceFileName = fileName.getText();
    	 String destFileName = fileNameDest.getText();
    	 DealData dd = new DealData();
    	 try{
    		 dd.dealDate(sourceFileName, destFileName,textAreaOutput);
    	 }catch(Exception ex){
    		 logger.info(ex.getMessage());
    		 JOptionPane.showMessageDialog(AppMainWindow.statusPanel,
    				 ex.getMessage(), "Sorry~", JOptionPane.WARNING_MESSAGE);
    	 }
    }

}
