package com.leetcode.demo;

import java.util.Arrays;

/**
 * 给定一个包括n 个整数的数组nums和 一个目标值target。找出nums中的三个整数，
 * 使得它们的和与target最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 */
public class ThreeSumClosest {

    public static void test() {
        int[] arr = {-1, 2, 1, -4};
        int[] arr1 = {1, 1, -1, -1, 3};
        int[] arr2 = {-5, -5, -4, 0, 0, 3, 3, 4, 5};
        int[] arr3 = {1, 1, 1, 0};
        int closest = threeSumClosest(arr3, -100);
        System.out.println("closest:" + closest);
    }

    /**
     * a+b= target-c
     */
    public static int threeSumClosest(int[] nums, int target) {

        if (nums == null || nums.length < 3) {
            return 0;
        }

        //先排序
        Arrays.sort(nums);

        int length = nums.length;

        //将result设置为最偏离的数
        int result = Math.abs(target - nums[length - 1] * 3) > Math.abs(target - nums[0] * 3) ? nums[length - 1] * 3 : nums[0] * 3;

        //[0,1,1,1]  -100
        //[-4,-1,1,2,17,18,19] 1   -4
        for (int i = 0; i < nums.length; i++) {
            int c = target - nums[i];

//            if (c < 0) {
//                continue;
//            }

//            //排除i 相等的情况
//            if (i > 0 && c == target - nums[i - 1]) {
//                continue;
//            }

            int aIndex = i + 1;//排除index相等的情况
            int bIndex = nums.length - 1;

            while (aIndex < bIndex) {
                int a = nums[aIndex];
                int b = nums[bIndex];

                //本次更接近target,
                if (Math.abs(target - (a + b + nums[i])) < Math.abs(target - result)) {
                    result = a + b + nums[i];
                    if (a + b + nums[i] == target) {
                        break;
                    }
                } else {
                    break;
                }

                if (a + b < c) {
                    aIndex++;
                } else if (a + b > c) {
                    bIndex--;
                }
            }
            if (result == target) {
                break;
            }
        }
        return result;
    }
}
