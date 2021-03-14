package com.leetcode.demo.simple.dynamicPlanning;

//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
public class MaxSubArray {

    public static void test() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int maxSub = maxSubArray2(arr);
        System.out.println("maxSub:" + maxSub);
    }

    //[-5，-2,8,-1,-10]
    //[-2,1,-3,4,-1,2,1,-5,4]
    private static int maxSubArray(int[] nums) {
        //以index= 0 作为最大值；
        int max = nums[0];
        int sum = 0;

        for (int num : nums) {
            //当前sum的值只要大于0；就继续相加;
            if (sum > 0) {
                sum += num;
            } else {
                //sum每次一个开始的值都是大于0；
                sum = num;
            }

            //记录每次的循环后的最大值；
            max = Math.max(sum, max);
        }
        return max;
    }

    //当max<0 时，再往后追加也不会成为最大和的连续数组，所以中断重新计算；
    //每次循环比较当前连续最大值和上一个最大和的连续数组；
    private static int maxSubArray2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxSub = nums[0];
        int max = 0;

        for (int num : nums) {
            //当max<0 时，再往后追加也不会成为最大和的连续数组，所以中断重新计算
            if (max > 0) {
                max += num;
            } else {
                max = num;
            }

            //每次循环都记录最大值
            maxSub = Math.max(max, maxSub);
        }

        return maxSub;
    }
}
