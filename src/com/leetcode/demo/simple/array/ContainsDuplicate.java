package com.leetcode.demo.simple.array;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
 */
public class ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {
        if (nums == null){
            return false;
        }        //N+NlogN
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDuplicate2(int[] nums) {
        //N+NlogN
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length - 1; i++) {
            Integer integer = map.get(nums[i]);
            if (integer != null) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }
}
