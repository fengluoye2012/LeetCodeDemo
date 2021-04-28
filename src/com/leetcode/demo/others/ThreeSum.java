package com.leetcode.demo.others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 */
public class ThreeSum {

    public static void test() {
        int[] num = {-2, -2, -1, -1, 0, 0, 1, 1, 2, 2};
        int[] num1 = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> lists = threeSum(num1);
        for (List<Integer> list : lists) {
            System.out.println("" + list.get(0) + " " + list.get(1) + " " + list.get(2));
        }
    }

    /**
     * 排序，将三数相加转换成两数相加
     * a+b+c = 0   a+b = -c
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return list;
        }

        //快排 nlogn
        Arrays.sort(nums);

        //[-2，-2,-1,-1,0,0,1,1,2,2]
        //[-4,-1,-1,0,1,2]
        for (int i = 0; i < nums.length - 2; i++) {
            //排除重复的三元数组；排序后，nums[i] >0  只能从<0的部分中获取，就会导致数组重复（重要）
            if (nums[i] > 0) {
                break;
            }

            //排除相同c 的值
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int c = -nums[i];
            int start = i + 1;
            int end = nums.length - 1;

            while (start < end) {
                if (nums[start] + nums[end] < c) {
                    start++;
                } else if (nums[start] + nums[end] > c) {
                    end--;
                } else {
                    int a = nums[start];
                    int b = nums[end];
                    List<Integer> subList = new ArrayList<>();
                    subList.add(nums[i]);
                    subList.add(a);
                    subList.add(b);
                    list.add(subList);

                    start++;
                    end--;

                    //数组没有排重，排除相同的值
                    while (start < end && nums[start] == nums[start - 1]) {
                        start++;
                    }

                    while (start<end && nums[end] == nums[end+1]){
                        end--;
                    }
                }
            }
        }
        return list;
    }

}
