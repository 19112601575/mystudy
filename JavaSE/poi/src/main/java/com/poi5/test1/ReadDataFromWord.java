package com.poi5.test1;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadDataFromWord {
    public static void main(String[] args) throws IOException {
        //找到读取的目标文件
        File file = new File("D:\\Java\\code\\JavaSE\\poi\\test.docx");

        //创建一个文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);

        //创建一个 word文档对象,将输入流存入文档
        XWPFDocument document = new XWPFDocument(fileInputStream);

        //创建一个文档执行器，使用执行器的输出方法
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);

        //获取当前word的文本内容
        String text = extractor.getText();

        System.out.println(text);

        fileInputStream.close();
    }
}
