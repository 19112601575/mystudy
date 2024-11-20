package com.example.exceptiontt.algorithm;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class MergeArray {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 3, 5};
        int m = 3;
        int n = 3;
//        mergeArrayPointer(nums1,nums2,m,n);
        mergeArrayJava(nums1, nums2, m, n);
    }

    public static void mergeArrayPointer(int[] nums1, int[] nums2, int m, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] >= nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
        //剩余元素复制
        while (p2 >= 0) {
            nums1[p] = nums2[p2];
            p2--;
            p--;
        }
        System.out.println("方式一point:" + Arrays.toString(nums1));
    }

    public static void mergeArrayJava(int[] nums1, int[] nums2, int m, int n) {
        //将数组二加到数组一，排序
        while (n - 1 >= 0) {
            nums1[m] = nums2[n - 1];
            n--;
            m++;
        }
        Arrays.sort(nums1);
        System.out.println("方式二" + Arrays.toString(nums1));
    }
}
