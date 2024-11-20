package com.example.exceptiontt.string;

import java.time.LocalDateTime;

public class StringFormat {
    public static void main(String[] args) {
        String i = "hello";
        String v = "sunday%s：<%%sss%%>\n";
       String s =  String.format(v, "hello");
        System.out.println(s);
    }
}
