package com.example.exceptiontt.lambda;

public class LambdaT {
    public static void main(String[] args) throws Exception{
        //匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("启动");
            }
        }).start();

        /*Lambda:
            使用lambda时，接口必须是函数式接口：只有一个抽象方法的接口，可以使用@FunctionalInterface定义函数式接口
         */
        new Thread(() ->{
            System.out.println("启动");
        }).start();
        //lambda只有一行时，可以省略大括号
        new Thread(()-> System.out.println("启动")).start();
    }
}
