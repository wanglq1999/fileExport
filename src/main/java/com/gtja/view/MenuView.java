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
  
    //����򿪺ͱ���Ի���  
    private FileDialog openDia;  
  
    //�����ı�����������򿪵�����  
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
  
        fileMenu = new Menu("��ʼ");  
//        subMenu = new Menu("�Ӳ˵�");  
              
        openItem = new MenuItem("�������ļ�");  
//        saveItem = new MenuItem("����");  
//        subItem1 = new MenuItem("����Ŀ1");  
//        subItem2 = new MenuItem("����Ŀ2");  
        closeItem = new MenuItem("�˳�");  
  
//        subMenu.add(subItem1);  
//        subMenu.add(subItem2);  
          
  
        fileMenu.add(openItem);  
//        fileMenu.add(saveItem);  
//        fileMenu.add(subMenu);  
        fileMenu.add(closeItem);  
        bar.add(fileMenu);  
  
        f.setMenuBar(bar);  
  
        //Ĭ��ģʽΪ FileDialog.LOAD  
        openDia = new FileDialog(f,"�ҵĴ�",FileDialog.LOAD);  
  
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
            //���ô��ļ�����  
            public void actionPerformed(ActionEvent e)  
            {  
                openDia.setVisible(true);  
                String dirPath = openDia.getDirectory();//��ȡ�ļ�·��  
                String fileName = openDia.getFile();//��ȡ�ļ�����  
                System.out.println(dirPath +"++"+ fileName);  
                  
                //�����·�� �� Ŀ¼Ϊ�� �򷵻ؿ�  
                if(dirPath == null || fileName == null)  
                        return ;  
  
                ta.setText(dirPath+fileName);//����ı�  
  
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
                    throw new RuntimeException("�ļ���ȡʧ�ܣ�");  
                }  
  */
                  
  
            }  
        });  
  
        closeItem.addActionListener(new ActionListener()  
        {  
            //�����˳�����  
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

