package com.example.exceptiontt.lambda;

import java.util.ArrayList;
import java.util.List;

public class LambdaList {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.forEach(System.out::println);
    }
}
