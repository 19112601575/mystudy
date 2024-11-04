package com.example.exceptiontt.lambda;

public class LambdaT2 {
    public static void main(String[] args) {
        modify(new Modify() {
            @Override
            public int modify(int a, int b) {
                return a + b;
            }
        });

        modify((a, b) -> a + b);
        modify(Integer::sum);
    }

    public static void modify(Modify modify) {

        System.out.println(modify.modify(10, 20));
    }

    public static void delete(int a, int b) {
        System.out.println(a - b);
    }
}
