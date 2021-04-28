package com.leetcode.demo.leetcode.simple.dynamicPlanning;

public class ClimbStairs {
    public static void test() {
        int climbStairs2 = climbStairs3(12);
        int climbStairs = climbStairs(12);
        System.out.println("climbStairs2:" + climbStairs2 + ",climbStairs:" + climbStairs);
    }

    //递归耗时较高；
    //f(4) = f(3)+f(2);
    public static int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int i = 1;
        int j = 2;
        int index = 2;
        int result = 0;
        while (n > index) {
            result = i + j;
            i = j;
            j = result;
            index++;
        }
        return result;
    }


    //输入45时 超出时间限制
    //假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
    //每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
    public static int climbStairs2(int n) {
        //1111 121 112 22 211
        if (n <= 2) {
            return n;
        }
        return climbStairs2(n - 2) + climbStairs2(n - 1);
    }

    //通过循环的方式实现 f(n)=f(n-1)+f(n-2),用变量i和j记录f(n-2)和f(n-1)的值
    public static int climbStairs3(int n) {
        if (n < 3) {
            return n;
        }
        int i = 1;
        int j = 2;
        int index = 2;
        int result = 0;

        while (n > index) {
            result = i + j;
            i = j;
            j = result;
            index++;
        }
        return result;
    }
}
