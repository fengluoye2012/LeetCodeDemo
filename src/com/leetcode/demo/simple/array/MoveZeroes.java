package com.leetcode.demo.simple.array;

import java.util.Arrays;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * 必须在原数组上操作，不能拷贝额外的数组。
 * 尽量减少操作次数。
 */
public class MoveZeroes {

    public static void test() {
        int[] arr = {0, 1, 0, 3, 12};
        moveZeroes2(arr);
    }

    //将非0元素往前移动；将index后的元素设置为0；
    public static void moveZeroes2(int[] nums) {
        //非0元素的下标；
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[index] = nums[i];
                index++;
            }
        }

        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
        System.out.println("nums = " + Arrays.toString(nums));
    }

    //8 ms 效率较低
    //[0,1,0,3,12]  [1，0，0,3,12]  [0,1,2,3,4]
    //[1,3,12,0,0]
    public static void moveZeroes(int[] nums) {
        int zeroIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            //调整位置
            if (zeroIndex > -1 && nums[i] != 0) {
                int tem = nums[zeroIndex];
                nums[zeroIndex] = nums[i];
                nums[i] = tem;

                //将i 移动到新的位置
                i = zeroIndex;
                zeroIndex = -1;
                continue;
            }

            //当前zeroIndex = -1，找到0的index;
            if (zeroIndex == -1 && nums[i] == 0) {
                zeroIndex = i;
            }
        }

        System.out.println("nums = " + Arrays.toString(nums));
    }
}
