package com.leetcode.demo.simple.array;

/**
 * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 */
public class RemoveDuplicates {

    public static void test() {
        int[] arr = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int[] arr3 = {3, 2, 1, 1};
        int[] arr2 = {1, 1, 2, 3};
        int duplicates = removeDuplicates(arr);
        System.out.println("duplicates:" + duplicates);
    }

    /**
     * 双指针方式，通过覆盖重复元素的方式，将不重复的放在数组的前length个长度；
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }

        //[1,1,2]
        //{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int length = 0;//表示不重复元素长度；
        for (int i = 1; i < nums.length; i++) {
            //当前元素小于等于上一个相等，
            if (nums[i] == nums[length]) {
                continue;
            }
            length++;
            //将后面不重复的元素往前移动；
            nums[length] = nums[i];
        }

        return length + 1;
    }
}
