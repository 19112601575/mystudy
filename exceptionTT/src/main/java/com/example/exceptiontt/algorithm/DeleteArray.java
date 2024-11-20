package com.example.exceptiontt.algorithm;

import java.util.Arrays;

public class DeleteArray {
    //删除数组中指定元素
    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int val = 2;
        deleteArray1(nums,val);
    }

    public static void deleteArray1(int[] nums, int val) {
        int p1 = 0;
        for (int p2 = 0; p2 < nums.length; p2++) {
            if (nums[p2] != val) {
                nums[p1] = nums[p2];
                p1++;
            }
        }
         int k = nums.length-p1;
    }
}
