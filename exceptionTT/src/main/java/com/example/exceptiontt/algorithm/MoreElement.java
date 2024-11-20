package com.example.exceptiontt.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MoreElement {
    public static void main(String[] args) {
more();
more1();
    }
    public static void more1(){
        int[] nums = {0, 0, 4, 1, 1, 3, 3,1, 1, 1 };
        Arrays.sort(nums);
        System.out.println(nums[nums.length/2]);
    }

    public static void more(){
        int[] nums = {0, 0, 4, 1, 1, 3, 3,1, 1, 1 };
        Map<Integer,Integer> map =new HashMap<>();
        for (int i =0;i<nums.length;i++){
            if (map.containsKey(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
            }else {
                map.put(nums[i],1);
            }
        }
//        int maxVal=0 ;
//        int maxKey=0;
//        for (Map.Entry<Integer, Integer> entry :
//                map.entrySet()) {
//            if (entry.getValue()>maxVal){
//                maxVal = entry.getValue();
//                maxKey=entry.getKey();
//            }
//        }
        Optional<Map.Entry<Integer, Integer>> max = map.entrySet().stream().max((e1, e2) -> e1.getValue().compareTo(e2.getValue()));
        System.out.println(max.get().getKey()+" "+max.get().getValue());
//        System.out.println(maxKey+" "+maxVal);
    }
}
