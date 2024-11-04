package com.example.exceptiontt.board;

import java.io.*;

public class GetBoard {
    public static void main(String[] args) throws Exception {
        String result = "";

        File file = File.createTempFile("real", ".vbs");
        file.deleteOnExit();
        FileWriter fos = new FileWriter(file);

        String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n"
                + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n"
                + "    exit for  ' do the first cpu only! \n" + "Next \n";

        fos.write(vbs);
        fos.close();

        Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            result += line;
        }
        System.out.println(result);
        br.close();
    }
}
