package com.example.exceptiontt.algorithm;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class removeRepeat {

    public static void main(String[] args) {
        removeRepeatArray();
        removeRepeatArray2();
    }

    //删除有序数组中的重复元素
    public static void removeRepeatArray() {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int fast = 1;
        int slow = 1;
        while (fast < nums.length) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        System.out.println(Arrays.toString(nums));
    }
    //删除有序数组中的重复元素,删除重复次数超过两次的
    public static void removeRepeatArray2(){
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        //0011223344
        int fast = 2;
        int slow = 2;
        while (fast < nums.length) {
            //不同于重复一次的，此判断的是slow的-2
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        System.out.println(Arrays.toString(nums));
    }
}
