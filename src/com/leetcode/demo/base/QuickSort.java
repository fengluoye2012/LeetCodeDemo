package com.leetcode.demo.base;

import java.util.Arrays;

//快速排序及优化（Java版）https://blog.csdn.net/scgaliguodong123_/article/details/46473365
public class QuickSort {
    public static void test() {
        //int[] arr = {4, 3, 1, 6, 4, 9, 2};
        int[] arr = {4,2,9,1,6,3};
        quickSort3(arr);
        System.out.println("arr:" + Arrays.toString(arr));
    }

    /**
     * 快排基于分治思想，对分治后的子数组进行排序；时间复杂度O(NLogN)
     * 优化：
     * 1、基准数选择：1）固定值 2）随机  3）三数取中
     * 2、交换采用位运算
     * 3、优化小数组排序方案：快排使用于非常大的数组；数组小于7或者5 采用插入排序；
     * 4、优化递归
     *
     * @param arr
     */
    public static void quickSort(int[] arr) {
        int length;
        if (arr == null || (length = arr.length) <= 1) {
            return;
        }
        quickSort(arr, 0, length - 1);

        System.out.println("arr = " + Arrays.toString(arr));
    }

    //1). 先从数列中取出一个数作为基准数。
    //2). 分区过程，将比基准数大的数全放到它的右边，小于或等于基准数全放到它的左边。
    //3). 递归，再对左右区间重复第二步，直到各区间只有一个数。
    //[4,1,6,7,2]  0  4
    private static void quickSort(int[] arr, int left, int right) {
        if (left > right) {
            return;
        }

        //基准数-固定位置
        int base = arr[left];
        //基准数-随机
//        int random  = new Random().nextInt(right-left+1)+left;
//        int base =arr[random];

        //基准数-三数取中法
        //chooseBaseKey(arr,left,right);
        //int base = arr[left];

        int i = left;
        int j = right;

        //分区-将比基准数大的数全放到它的右边，小于或等于基准数全放到它的左边。
        while (i != j) {//[4,1,6,7,2]--[4,1,2,7,6]
            //先从右边往左开始找，直到找到比base 小的数
            while (arr[j] >= base && i < j) {  //j=4-2  j=2-2
                j--;
            }

            //再从左往右找 直到找到比base 大的数
            while (arr[i] <= base && i < j) {//i=2-6
                i++;
            }

            //上面的循环结束了 表示找到了位置或者i>=j；交换两个数的位置
            if (i < j) {
                swap(arr, i, j);
            }
            System.out.println(" i = " + i + ", j = " + j);
        }

        //基准数放在中间位置
        arr[left] = arr[i];
        arr[i] = base;

        //[2,1,4,7,6]--i=2

        //递归 继续向基准的左右两边执行和上面同样的操作
        //i 的索引处为上面已经确定的基准位置，无需处理
        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int left = 0;
        int right = arr.length - 1;
        quickSort2(arr, left, right);
    }

    //1). 先从数列中取出一个数作为基准数。
    //2). 分区过程，将比基准数大的数全放到它的右边，小于或等于基准数全放到它的左边。
    //3). 递归，再对左右区间重复第二步，直到各区间只有一个数。
    //{4, 3, 1, 6, 4, 9, 2};-- i=3  j=6 {4，3，1，2，4，9，6}--j=3 i=3 {4，3，1，2，4，9，6}
    private static void quickSort2(int[] arr, int left, int right) {
        //只有一个元素
        if (right <= left) {
            return;
        }

        //基准数
        int base = arr[left];

        //分区过程，将比基准数大的数全放到它的右边，小于或等于基准数全放到它的左边。
        int i = left;
        int j = right;

        while (i < j) {//x != y 和 x<y 的效果是否一致
            //从右往左 找到小于base的index
            while (arr[j] >= base && i < j) {
                j--;
            }

            //从左往右 找到大于base的index
            while (arr[i] <= base && i < j) {
                i++;
            }

            //交换
            if (i < j) {
                swap(arr, i, j);
            }
            System.out.println(" i = " + i + ", j = " + j);
        }

        //调整基准数位置,完成分区
        swap(arr, left, i);

        quickSort2(arr, left, i - 1);
        quickSort2(arr, i + 1, right);
    }

    private static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort3(arr, 0, arr.length-1);
    }

    //i = 0 j = 5 {4,2,9,1,6,3}
    //i = 2 j = 5 {4,2,3,1,6,9}
    //i = 3 j = 3
    //i = 3 j = 3  {1,2,3,4,6,9}
    //1). 先从数列中取出一个数作为基准数。
    //2). 分区过程，将比基准数大的数全放到它的右边，小于或等于基准数全放到它的左边。
    //3). 递归，再对左右区间重复第二步，直到各区间只有一个数。
    private static void quickSort3(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        //选中基准数
        int base = arr[left];
        int i = left;
        int j = right;

        //分区过程
        while (i != j) {//一定要用 != 号
            //从右往左 找到比基准数小的index
            while (i < j && arr[j] > base) {
                j--;
            }

            //从左往右 找到比基准数大的index
            while (i < j && arr[i] <= base) {
                i++;
            }

            //交换
            if (i < j) {
                swap(arr, i, j);
            }
        }

        //此时i和j相等
        //将基准数放入对应位置
        swap(arr,left,i);

        //递归
        quickSort3(arr, left, i - 1);
        quickSort3(arr, i + 1, right);
    }

    //交换 可以考虑采用位运算
    private static void swap(int[] arr, int i, int j) {
        int tem = arr[i];
        arr[i] = arr[j];
        arr[j] = tem;
    }

    //交换 可以考虑采用位运算
    private static void swap2(int[] arr, int i, int j) {
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }

    //int[] arr = {4, 3, 1, 6, 4, 9, 2};   4 6 2--2 6 4 -- 2 4 6 -- 4 2 6
    //三数取中，将三个数的中间值作为基准
    public static void chooseBaseKey(int[] arr, int left, int right) {
        int mid = left + (right - left) / 2;
        if (arr[left] > arr[right]) {//保证左端较小
            swap(arr, left, right);
        }

        if (arr[mid] > arr[right]) {//保证中间次小
            swap(arr, mid, right);
        }

        //此时最大值在最右边
        if (arr[mid] > arr[left]) {
            swap(arr, mid, left);
        }
    }
}
