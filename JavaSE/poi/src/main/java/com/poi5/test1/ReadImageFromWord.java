package com.poi5.test1;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ReadImageFromWord {
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\Java\\code\\JavaSE\\poi\\test.docx";
        File file = new File(filePath);

        //创建文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);

        //创建文档对象
        XWPFDocument document = new XWPFDocument(fileInputStream);

        //获得文档所有内容-->段落
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        //准备一个集合，存储拿到的图片的名字和二进制数据
        HashMap<String, byte[]> images = new HashMap<>();

        //遍历段落
        for (XWPFParagraph paragraph: paragraphs){
            //获得所有文本区域
            List<XWPFRun> runs = paragraph.getRuns();
            //遍历文本区域
            for (XWPFRun run : runs){
                //获得所有图片
                List<XWPFPicture> pics = run.getEmbeddedPictures();
                //遍历图片
                for (XWPFPicture pic : pics){
                    //获得每个图片名
                    String fileName = pic.getPictureData().getFileName();

                    //图片二进制,可以通过输出流的read方法写出
                    byte[] data = pic.getPictureData().getData();

                    //集合：key是文件地址，value是二进制数据
                    images.put("D:\\Java\\code\\JavaSE\\poi\\"+fileName,data);
                }
            }
        }

        //遍历map集合，
        for(Map.Entry<String, byte[]> entry :images.entrySet()){
            FileOutputStream fileOutputStream = new FileOutputStream(entry.getKey());
            fileOutputStream.write(entry.getValue());
            fileOutputStream.close();
        }
    }
}