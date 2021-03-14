package com.leetcode.demo.simple.array;

//给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
//最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
//你可以假设除了整数 0 之外，这个整数不会以零开头。
public class PlusOne {

    public static void test() {
        int[] arr = {9, 9, 9};
        int[] ints = plusOne(arr);
        for (int i :ints) {
            System.out.println("i："+i);
        }
    }

    //[9,9,9]
    //1 <= digits.length <= 100
    //0 <= digits[i] <= 9
    public static int[] plusOne(int[] digits) {
        boolean plusOne = false;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] + 1 > 9) {
                plusOne = true;
                digits[i] = 0;
            } else {
                digits[i] = digits[i] + 1;
                plusOne = false;
            }

            if (!plusOne) {
                break;
            }
        }

        //第一位为1，其他全都是0
        if (plusOne) {
            int[] arr = new int[digits.length + 1];
            arr[0] = 1;
            return arr;
        }

        return digits;
    }
}
