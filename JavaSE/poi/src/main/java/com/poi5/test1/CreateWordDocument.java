package com.poi5.test1;

import org.apache.poi.xwpf.usermodel.*;

import java.io.*;

public class CreateWordDocument {
    public static void main(String[] args) throws IOException {
        //创建一个word 文档对象 ：操作一个拓展名为  .docx 的word文档
        XWPFDocument doc = new XWPFDocument();

        //word文档分段落，首先创建一个段落
        XWPFParagraph paragraph = doc.createParagraph();

        //创建一个具有相同属性的文本区域 --盒子：在该文本区域写入内容
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setText("你好，今天天气不错");

        //段落居中
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        //文本区域加粗
        paragraphRun.setBold(true);
        //设置字体大小
        paragraphRun.setFontSize(30);

        XWPFParagraph paragraph2 = doc.createParagraph();
        //首行缩进
        paragraph2.setIndentationFirstLine(400);
        XWPFRun paragraph2Run = paragraph2.createRun();
        paragraph2Run.setText("表格的一格相当于一个完整的docx文档，" +
                "只是没有页眉和页脚。里面可以有表格，使用xwpfTableCell.getTables()获取");

        //向word 文档输出一个表格
        XWPFTable table = doc.createTable();
        table.setWidth("100%");//表格占据文档的百分比

        //表格分为行，行中有单元格
        //先获取表格的第一行，再对单元格进行操作
        XWPFTableRow oneRow = table.getRow(0);
        XWPFTableCell oneRowCell = oneRow.getCell(0);
        oneRowCell.setText("我是第一行，第一列");

        //创建第一行第二列
        oneRow.addNewTableCell().setText("我是第一行,第二列");

        //创建第二行
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText("我是第二行，第一列");
        row.getCell(1).setText("我是第二行，第二列");
        //创建一个文件对象
        File file = new File("D:\\Java\\code\\JavaSE\\poi\\test.docx");

        //创建一个文件输出流
        FileOutputStream outputStream = new FileOutputStream(file);

        //通过文件输出流，将目标写入到 磁盘
        doc.write(outputStream);

        //关闭 文件输出流
        outputStream.close();

        System.out.println("操作成功");
    }
}
