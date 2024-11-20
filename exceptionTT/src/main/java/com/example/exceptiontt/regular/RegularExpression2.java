package com.example.exceptiontt.regular;

public class RegularExpression2 {
    public static void main(String[] args) {
        String s = "ab";
        //[]：匹配任一字符
        System.out.println(s.matches("^[abcd]$"));
        //{}：匹配至少-最大长度字符,{n}：前面的操作可以重复n次，[]每次匹配一个字符，重复多次可以匹配多个字符
        System.out.println(s.matches("^[abcd]{1,3}$"));
        //()：匹配字符串
        System.out.println(s.matches("^(abcd)$"));
        //|：匹配任一字符串
        System.out.println(s.matches("^(abc|bcd|abcd)$"));
        //?：前面的字符可以0或1次
        System.out.println(s.matches("^(A|a)?bc$"));
        //.：代表任一字符，后面要完全匹配
        System.out.println(s.matches("^.bc$"));
        System.out.println(s.matches("^$"));
        System.out.println(s.matches("^$"));
    }
}
