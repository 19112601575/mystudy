package com.poi5.test1;

import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ReadTableFromWord {
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\Java\\code\\JavaSE\\poi\\test.docx";

        FileInputStream fileInputStream = new FileInputStream(filePath);

        XWPFDocument document = new XWPFDocument(fileInputStream);

        List<XWPFTable> tables = document.getTables();

        //遍历表格
        for (XWPFTable table : tables){
            List<XWPFTableRow> rows = table.getRows();
            //遍历行
            for (XWPFTableRow row : rows){
                List<XWPFTableCell> cells = row.getTableCells();
                //遍历单元格
                for (XWPFTableCell cell : cells){
                    String text = cell.getText();
                    System.out.println(text);

                    //拿到了值，进行逻辑处理
                }
            }
        }
    }
}
