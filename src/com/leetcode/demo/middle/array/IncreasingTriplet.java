package com.leetcode.demo.middle.array;

public class IncreasingTriplet {

    public static void test() {
        //int[] arr = {2, 1, 5, 0, 4, 6};
        int[] arr = {20, 100, 10, 12, 5, 13}; //10 12 13
        boolean b = increasingTriplet(arr);

        System.out.println("b:" + b);
    }

    //给你一个整数数组nums ，判断这个数组中是否存在长度为 3 的递增子序列(可以是非连续的)。
    //如果存在这样的三元组下标 (i, j, k)且满足 i < j < k ，使得nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。

    //递增子序列可以是非连续的；不能使用下标的方式；
    private static boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        //设置Int最大值；
        int min = Integer.MAX_VALUE;
        int max = Integer.MAX_VALUE;

        for (int num : nums) {
            //第三个递增的数
            if (num > max) {
                return true;
            } else if (num > min) {
                //较大值
                max = num;
            } else {
                //小于小值两种可能：1）新成为小值 2）较大值没变化
                min = num;
            }
        }
        return false;
    }


    //递增子序列可以是连续的
    public boolean increasingTriplet2(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        int length = nums.length;
        int slow = 0;
        int fast = slow + 1;

        int continueLen = 0;
        while (fast < length) {
            //非连续递增,两两之间递增；
            if (nums[fast] <= nums[slow + continueLen]) {
                //将slow的只赋值为fast，因为之前的肯定是非连续递增的；
                slow = fast;
                fast = slow + 1;
                continueLen = 0;
                continue;
            }

            fast++;
            continueLen++;

            //判断连续的
            if (continueLen >= 2) {
                return true;
            }
        }
        return false;
    }

}
