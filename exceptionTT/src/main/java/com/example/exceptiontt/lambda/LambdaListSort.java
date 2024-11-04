package com.example.exceptiontt.lambda;


import java.util.*;

public class LambdaListSort {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(3, 4, 1, 2, 5);

        //匿名内部类用法
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        list.forEach(System.out::print);

        //默认升序
        list.sort(Comparator.comparingInt(o -> o));

        //降序
        list.sort(((o1, o2) -> o2 - o1));

        //最大数字
        Integer maxInt = list.stream().max(Integer::compareTo).get();
        System.out.println("最大数字:"+maxInt);

        //最长字符串
        List<String> stringList = Arrays.asList("赵云", "张飞", "夏侯惇", "诸葛孔明");

        Optional<String> max = stringList.stream().max(Comparator.comparing(String::length));
        System.out.println("最长字段:" + max.get());
    }
}
