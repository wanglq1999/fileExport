package com.gtja;

public class ExcelData{  
    private String value;//��Ԫ���ֵ  
    private int colSpan = 1;//��Ԫ��缸��  
    private int rowSpan = 1;//��Ԫ��缸��  
    private boolean alignCenter;//��Ԫ���Ƿ���У�Ĭ�ϲ����У����ѡ���ǣ���ˮƽ�����¶�����  
    public boolean isAlignCenter() {  
        return alignCenter;  
    }  
    public void setAlignCenter(boolean alignCenter) {  
        this.alignCenter = alignCenter;  
    }  
    public String getValue() {  
        return value;  
    }  
    public void setValue(String value) {  
        this.value = value;  
    }  
    public int getColSpan() {  
        return colSpan;  
    }  
    public void setColSpan(int colSpan) {  
        this.colSpan = colSpan;  
    }  
    public int getRowSpan() {  
        return rowSpan;  
    }  
    public void setRowSpan(int rowSpan) {  
        this.rowSpan = rowSpan;  
    }  
}  
