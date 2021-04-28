package com.leetcode.demo.leetcode.simple.array;

import java.util.Arrays;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 */
public class SingleNumber {

    public static void test() {
        int[] arr = {1, 1, 2, 2, 3, 4, 4};
        int number2 = singleNumber2(arr);
        System.out.println("number2:" + number2);
    }

    //8 ms 时间效率差
    //2*N+1；
    //你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
    public int singleNumber(int[] nums) {
        //N+NlogN
        Arrays.sort(nums);

        int length = nums.length / 2;
        //[0,1,1,2,2,4,4] 7  3   2
        //[1,1,2,2,3,4,4]
        //[1,1,3,3,4,5,5]
        for (int i = 0; i < length; i++) {//i=2
            //当前这一对不相等
            if (nums[2 * i] != nums[2 * i + 1] && nums[2 * i + 1] == nums[2 * i + 2]) {
                return nums[2 * i];
            }
        }
        return nums[nums.length - 1];
    }

    /**
     * 位运算^=   两个二进制对应位相同时，结果为0，否则结果为1；
     * 0 的二进制
     */
    public static int singleNumber2(int[] nums) {
        int single = 0;

        //将十进制转换为二进制字符串
        String s = Integer.toBinaryString(7);
        System.out.println("s = " + s);
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }
}
