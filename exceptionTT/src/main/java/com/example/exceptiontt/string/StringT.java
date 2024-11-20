package com.example.exceptiontt.string;


public class StringT {
    public static void main(String[] args) {
        String s = "吕布辕门射戟";
//        System.out.println(s.length());
//        StringUtils.;
        int i = s.indexOf("布");
        String concat = s.concat("刘备");
        String trim = s.trim();
        String repeat = s.repeat(2);
        CharSequence charSequence = s.subSequence(0, 1);
        System.out.println(repeat);
        System.out.println(charSequence);
        System.out.println(trim);
        System.out.println(i);
        System.out.println(concat);
    }
}
