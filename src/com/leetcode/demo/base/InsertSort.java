package com.leetcode.demo.base;

import java.util.Arrays;

//插入排序
public class InsertSort {

    public static void test() {
        int[] arr = {38, 65, 97, 76, 13, 27, 49};
        //insertSortIncrease(arr);
//        insertSortDrop2(arr);
        insertSortIncrease2(arr);
        System.out.println("arr:" + Arrays.toString(arr));
    }

    //{38,65,76,97,13,27,49}
    //工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
    //插入排序在实现上，在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。
    public static void insertSortIncrease(int[] arr) {
        int insertNode;
        int exchangeIndex;
        //将当前元素在已排序的部分，重新排序；
        for (int i = 1; i < arr.length; i++) {
            exchangeIndex = -1;
            insertNode = arr[i];
            //将大于insertNode 的元素往后移动，找到TargetIndex;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > insertNode) {
                    exchangeIndex = j;
                    arr[j + 1] = arr[j];
                    continue;
                }
                break;
            }
            //将insertNode 存储targetIndex
            arr[Math.max(exchangeIndex, 0)] = insertNode;
        }
    }

    public static void insertSortIncrease2(int[] arr) {
        int insertNode;
        //将当前元素在已排序的部分，重新排序；
        for (int i = 1; i < arr.length; i++) {
            insertNode = arr[i];
            //将大于insertNode 的元素往后移动，找到TargetIndex;
            int j = i - 1;
            while (j >= 0 && arr[j] > insertNode) {
                arr[j + 1] = arr[j];
                j--;
            }
            //将insertNode 存储targetIndex
            arr[j + 1] = insertNode;
        }
    }

    //降序，也存在多交换问题 {97,65,38,76, 13, 27, 49}
    public static void insertSortDrop2(int[] arr) {
        int insertNode;
        for (int i = 1; i < arr.length; i++) {
            insertNode = arr[i];

            int j = i - 1;
            //将小于insertNode 的元素都往后移动
            while (j >= 0 && arr[j] < insertNode) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = insertNode;
        }
    }

    public static void insertSortDrop(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {

                if (arr[i] > arr[j]) {
                    int tem = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tem;
                }
            }
        }
    }
}
