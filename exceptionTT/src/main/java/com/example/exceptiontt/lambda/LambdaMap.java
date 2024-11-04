package com.example.exceptiontt.lambda;

import java.util.HashMap;
import java.util.Map;

public class LambdaMap {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "zs");
        map.put(2, "xs");
        map.put(3, "cs");
        map.put(4, "vs");

        //直接输出
        map.forEach((a, b) -> System.out.println(a + b));

        //条件判断
        map.forEach((a, b) -> {
            if (a == 2) {
                System.out.println("你好" + b);
            } else System.out.println(a + b);
        });
    }
}
