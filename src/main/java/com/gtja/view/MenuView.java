package com.gtja.view;

import java.awt.*;  
import java.awt.event.*;  
import java.io.*;  
  
class MenuView 
{  
    private Frame f;  
    private MenuBar bar;  
    private Menu fileMenu;  
    private MenuItem closeItem;  
    private MenuItem openItem;  
  
    //定义打开和保存对话框  
    private FileDialog openDia;  
  
    //设置文本区域来保存打开的数据  
    private TextArea ta;  
  
    private File file;  
  
    MenuView()  
    {  
        init();  
    }  
          
    public void init()  
    {  
        f = new Frame("my window");  
        f.setBounds(200,200,200,200);  
        //f.setLayout(new FlowLayout());  
  
        bar = new MenuBar();  
  
        ta = new TextArea(1,1);  
  
        fileMenu = new Menu("开始");  
//        subMenu = new Menu("子菜单");  
              
        openItem = new MenuItem("打开配置文件");  
//        saveItem = new MenuItem("保存");  
//        subItem1 = new MenuItem("子条目1");  
//        subItem2 = new MenuItem("子条目2");  
        closeItem = new MenuItem("退出");  
  
//        subMenu.add(subItem1);  
//        subMenu.add(subItem2);  
          
  
        fileMenu.add(openItem);  
//        fileMenu.add(saveItem);  
//        fileMenu.add(subMenu);  
        fileMenu.add(closeItem);  
        bar.add(fileMenu);  
  
        f.setMenuBar(bar);  
  
        //默认模式为 FileDialog.LOAD  
        openDia = new FileDialog(f,"我的打开",FileDialog.LOAD);  
  
        f.add(new Label("Hi There!"));
        f.add(new Label("sssssssss!"));
//        f.add(ta);  
  
        myEvent();  
  
        f.setVisible(true);  
  
  
    }  
  
    private void myEvent()  
    {    
  
        openItem.addActionListener(new ActionListener()  
        {  
            //设置打开文件功能  
            public void actionPerformed(ActionEvent e)  
            {  
                openDia.setVisible(true);  
                String dirPath = openDia.getDirectory();//获取文件路径  
                String fileName = openDia.getFile();//获取文件名称  
                System.out.println(dirPath +"++"+ fileName);  
                  
                //如果打开路径 或 目录为空 则返回空  
                if(dirPath == null || fileName == null)  
                        return ;  
  
                ta.setText(dirPath+fileName);//清空文本  
  
                file = new File(dirPath,fileName);  
                /**
                 * 
                 
                  
                try  
                {  
                        BufferedReader bufr = new BufferedReader(new FileReader(file));  
                          
                        String line = null;  
  
                        while( (line = bufr.readLine())!= null)  
                        {  
                            ta.append(line +"\r\n");  
                        }  
                        bufr.close();  
                }  
                catch (IOException ex)  
                {  
                    throw new RuntimeException("文件读取失败！");  
                }  
  */
                  
  
            }  
        });  
  
        closeItem.addActionListener(new ActionListener()  
        {  
            //设置退出功能  
            public void actionPerformed(ActionEvent e)  
            {  
                System.exit(0);  
            }  
        });  
  
        f.addWindowListener(new WindowAdapter()  
        {  
            public void windowClosing(WindowEvent e)  
            {  
                System.exit(0);  
            }  
        });  
    }  
  
    public static void main(String []args)  
    {  
     new MenuView();  
    }  
}  

