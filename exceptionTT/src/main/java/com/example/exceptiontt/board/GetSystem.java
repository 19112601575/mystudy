package com.example.exceptiontt.board;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GetSystem {
    public static void main(String[] args) throws Exception {
        getBoardInfo();
    }

    //cpu信息
    public static void getCpuInfo() throws Exception {
        Process process = Runtime.getRuntime().exec("systeminfo");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    //主板信息
    public static void getBoardInfo() throws Exception {
        Process process = Runtime.getRuntime()
//                .exec("wmic baseboard get product,manufacturer,version,serialnumber");
                .exec("wmic baseboard get serialnumber");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}
