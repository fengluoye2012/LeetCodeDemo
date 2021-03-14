package com.leetcode.demo.simple.dynamicPlanning;

import java.util.Arrays;

//给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
//初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
//提示：双指针
public class MergeToArray {

    public static void test() {
//        int[] arr1 = {1, 3, 5, 0, 0, 0, 0};
//        int[] arr2 = {2, 4, 6, 8};

        int[] arr1 = {2, 0};
        int[] arr2 = {1};
        merge(arr1, arr1.length - arr2.length, arr2, arr2.length);
    }

    //倒叙的方式，先将最大的元素放在最后面；一次遍历结束
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int length = nums1.length;
        int indexM = m - 1;
        int indexN = n - 1;

        while (length > 0) {
            length--;

            //indexM<0 如何解决；
            int valueM = indexM >= 0 ? nums1[indexM] : Integer.MIN_VALUE;
            int valueN = indexN >= 0 ? nums2[indexN] : Integer.MIN_VALUE;

            if (valueM <= valueN) {
                nums1[length] = valueN;
                indexN--;
            } else {
                nums1[length] = valueM;
                indexM--;
            }
        }
        System.out.println("nums1 = " + Arrays.toString(nums1));
    }

    //效率较低
    //[1,3,5,0,0,0,0]
    //[2,4,6,8]
    public static void merge2(int[] nums1, int m, int[] nums2, int n) {
        int length = nums1.length;
        int indexM = 0;
        int indexN = 0;
        while (indexM < m + indexN && indexN < n) {
            //num1元素小于num2元素;
            if (nums1[indexM] < nums2[indexN]) {
                indexM++;
            } else {
                //将indexM 开始的元素往后挪动 [indexM,indexN + m]
                int targetIndex = indexN + m;
                while (targetIndex > indexM) {
                    nums1[targetIndex] = nums1[targetIndex - 1];
                    targetIndex--;
                }

                nums1[indexM] = nums2[indexN];
                indexM++;
                indexN++;
            }
        }

        //num2 有剩余
        while (indexM < length && indexN < n) {
            nums1[indexM] = nums2[indexN];
            indexN++;
            indexM++;
        }
        System.out.println("nums1 = " + Arrays.toString(nums1));
    }
}
