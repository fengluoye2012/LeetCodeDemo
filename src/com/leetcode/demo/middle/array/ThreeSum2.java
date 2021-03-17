package com.leetcode.demo.middle.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，
//使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
//答案中不可以包含重复的三元组。
public class ThreeSum2 {

    public static void test() {
//        int[] arr = {-1, 0, 1, 2, -1, -4};
//        int[] arr = {0, 0, 0, 0};
        int[] arr = {-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4};
        List<List<Integer>> lists = threeSum(arr);
        System.out.println("size:" + lists.size());
        for (List<Integer> list : lists) {
            System.out.println("list:" + Arrays.toString(list.toArray()));
        }
    }

    //输入：nums = [-1,0,1,2,-1,-4]  [-4,-2,-1,-1,0,1,2]   [0,0,0,0]
    //输出：[[-1,-1,2],[-1,0,1]]
    private static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> targetList = new ArrayList<>();
        if (nums == null) {
            return targetList;
        }
        //先排序
        Arrays.sort(nums);

        int length = nums.length;
        int target = Integer.MAX_VALUE;
        int curTarget;
        //倒序[-4,-3,-2,-1,-1,0,0,1,2,3,4]
        for (int i = length - 1; i >= 2; i--) {
            if (target == nums[i]) {
                continue;
            }
            target = nums[i];
            int slow = 0;
            int fast = i - 1;

            int slowValue = Integer.MAX_VALUE;
            int fastValue = Integer.MAX_VALUE;
            curTarget = -target;
            while (slow < fast) {
                //两个相邻的值相同 则跳过
                if (slowValue == nums[slow]) {
                    slow++;
                    continue;
                }

                if (fastValue == nums[fast]) {
                    fast--;
                    continue;
                }

                slowValue = nums[slow];
                fastValue = nums[fast];

                int sum = nums[slow] + nums[fast];
                if (sum < curTarget) {
                    slow++;
                } else if (sum > curTarget) {
                    fast--;
                } else {
                    List<Integer> subList = new ArrayList<>();
                    subList.add(nums[slow]);
                    subList.add(nums[fast]);
                    subList.add(target);
                    targetList.add(subList);

                    slow++;
                    fast--;
                }
            }
        }
        return targetList;
    }
}
