package com.javaSe;

public class PatternsTest {
    public static void main(String[] args) {
        System.out.println("\\\\");
        System.out.println("\\,\\");
        System.out.println("\\");
        System.out.println("\\,");
        //报错System.out.println("\,");
        //System.out.println("\,\\,\");
        //总结：两个反斜线相当于一个反斜线。单个反斜线没有转义作用
    }
}
