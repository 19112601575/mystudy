package com.javaSe;

public class MyTest {
    //不传参，默认是张三
    public static void main(String[] args) {
        String name="";
        ps(name);
    }

    public static void ps(String name){
//         name == ""? "张三":"李四";


        System.out.println(name);
    }
}
