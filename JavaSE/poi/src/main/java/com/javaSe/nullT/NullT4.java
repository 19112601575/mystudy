package com.javaSe.nullT;

public class NullT4 {
    //null可以被动的被找到，但null本身不能指向别的
    public static void main(String[] args) {
        String s = null;
        String b = "01";
        System.out.println(b.equals(s));

    }
}
