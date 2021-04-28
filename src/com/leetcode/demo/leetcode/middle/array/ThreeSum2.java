package com.leetcode.demo.leetcode.middle.array;

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
//        int[] arr = {-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4};
        int[] arr = {-2, 0, 0, 2, 2};
        List<List<Integer>> lists = threeSum(arr);
        System.out.println("size:" + lists.size());
        for (List<Integer> list : lists) {
            System.out.println("list:" + Arrays.toString(list.toArray()));
        }
    }


    /**
     *
     * @param nums
     * @return
     */
    private static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> targetList = new ArrayList<>();
        if (nums == null) {
            return targetList;
        }

        //先排序
        Arrays.sort(nums);

        int target = Integer.MAX_VALUE;
        int length = nums.length;
        int slowValue;
        int fastValue;
        for (int i = length - 1; i >= 2; i--) {
            //已经排序过，如果target小于0，则从小于target的部分也无法目标a和b;
            if (nums[i] < 0) {
                continue;
            }

            //过滤掉相同的target
            if (i < length - 1 && nums[i] == target) {
                continue;
            }

            int slow = 0;
            int fast = i - 1;
            target = nums[i];
            while (slow < fast) {
                slowValue = nums[slow];
                fastValue = nums[fast];

                int sum = slowValue + fastValue;
                if (sum < -target) {
                    slow++;
                    continue;
                }

                if (sum > -target) {
                    fast--;
                    continue;
                }

                List<Integer> subList = new ArrayList<>();
                subList.add(slowValue);
                subList.add(fastValue);
                subList.add(target);
                targetList.add(subList);


                //过滤掉相同的slow对应的value
                while (slow < i) {
                    slow++;
                    if (slowValue != nums[slow]) {
                        break;
                    }
                }

                //过滤掉相同的fast对应的value
                while (fast > 0) {
                    fast--;
                    if (fastValue != nums[fast]) {
                        break;
                    }
                }
            }
        }
        return targetList;
    }
}
