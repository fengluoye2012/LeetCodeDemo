package com.leetcode.demo.base;

//二分查找
public class BinarySearch {

    public static void test() {
        int[] arr = {1, 2, 3, 5, 6, 7, 8, 9};
        binarySearch2(12, arr);
    }

    //[1,2,3,4,5,6,7,8,9]  3
    private static void binarySearch(int target, int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        int first = 0;
        int last = arr.length - 1;
        int mid;

        if (arr[first] > target || arr[last] < target) {
            return;
        }

        while (first <= last) {
            mid = (first + last) / 2;
            if (arr[mid] < target) {
                //在中间值的右侧
                first = mid + 1;
            } else if (arr[mid] > target) {
                //在中间值的左侧
                last = mid - 1;
            } else {
                System.out.println("target = " + target + ", mid = " + mid);
                return;
            }
        }
        System.out.println("没有找到target = " + target);
    }

    /**
     * @param target
     * @param arr    升序数组
     */
    private static void binarySearch2(int target, int[] arr) {
        if (arr == null || arr.length == 0 || arr[0] > target || arr[arr.length - 1] < target) {
            System.out.println("没有找到 target = " + target);
            return;
        }

        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int midIndex = (left + right) / 2;
            int mid = arr[midIndex];
            if (mid < target) {
                left = midIndex + 1;
            } else if (mid > target) {
                right = midIndex - 1;
            } else {
                System.out.println("target = " + target + ", midIndex = " + midIndex);
                return;
            }
        }
        System.out.println("没有找到 target = " + target);
    }
}

