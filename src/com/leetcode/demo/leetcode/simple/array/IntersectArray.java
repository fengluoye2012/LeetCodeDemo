package com.leetcode.demo.leetcode.simple.array;

import java.util.Arrays;

//给定两个数组，编写一个函数来计算它们的交集。
public class IntersectArray {
    public static void test() {
        int[] num1 = {1, 2, 2, 1};
        int[] num2 = {2, 2, 3};
        int[] intersect = intersect(num1, num2);
        for (int i :intersect) {
            System.out.println("i = "+i);
        }
    }

    //1.暴力破解 将 nums1 中的数组从大到小 在nums2 中依次判断是否包含该数组 包含则返回nums1
    //2.a.已经排好序？b.如果nums1比nums2小很多 c.nums存储在磁盘上不能一次加载 以nums1做窗口从nums2中做滑动窗口对比，每次只加载nums个值？

    //2ms
    public static int[] intersect(int[] nums1, int[] nums2) {
        //[3,6,1,4]  [8,4,9]
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int length1 = nums1.length;
        int length2 = nums2.length;
        int[] arr = new int[Math.min(length1, length2)];

        //[1,3,4,6]  [4,8,9]
        int start1 = 0;
        int start2 = 0;

        int index = 0;
        while (start1 < length1 && start2 < length2) {
            int value1 = nums1[start1];
            int value2 = nums2[start2];
            if (value1 < value2) {
                start1++;
            } else if (value1 > value2) {
                start2++;
            } else {
                arr[index] = value1;
                index++;
                start1++;
                start2++;
            }
        }
        //复制指定长度的数据
        return Arrays.copyOfRange(arr,0,index);
    }
}
