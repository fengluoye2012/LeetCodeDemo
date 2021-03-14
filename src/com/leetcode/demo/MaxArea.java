package com.leetcode.demo;

/**
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，
 * 垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，
 * 使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * <p>
 * n = height.length
 * 2 <= n <= 3 * 104
 * 0 <= height[i] <= 3 * 104
 */
public class MaxArea {
    public static void test() {
        int[] arr = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int maxArea = maxArea(arr);
        System.out.printf("maxArea:" + maxArea);
    }

    /**
     * 和2的思路不变，优化过程
     * @param height
     * @return
     */
    public static int maxArea3(int[] height) {
        int maxArea = 0;
        int i = 0;
        int j = height.length - 1;
        while (i < j) {
            int h = Math.min(height[i], height[j]);
            maxArea = Math.max(maxArea, h * (j - i));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return maxArea;
    }

    /**
     * 动态规划，基本的表达式: area = min(height[i], height[j]) * (j - i) 使用两个指针，
     * 值小的指针向内移动，这样就减小了搜索空间 因为面积取决于指针的距离与值小的值乘积，
     * 如果值大的值向内移动，距离一定减小，而求面积的另外一个乘数一定小于等于值小的值，因此面积一定减小，
     * 而我们要求最大的面积，因此值大的指针不动，而值小的指针向内移动遍历
     */
    public static int maxArea2(int[] height) {
        int maxArea = 0;
        int i = 0;
        int j = height.length - 1;
        while (i < j) {
            int h = Math.min(height[i], height[j]);
            maxArea = Math.max(maxArea, h * (j - i));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return maxArea;
    }


    //暴力法  s = w*h = (j-i)*math
    public static int maxArea(int[] height) {
        int maxArea = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int area = (j - i) * (Math.min(height[j], height[i]));
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }
}
