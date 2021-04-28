package com.leetcode.demo.others;

public class MyAtoi {
    public static void test() {
        //21474836460
        int myAtoi = myAtoi("123abc");
        System.out.println("myAtoi:" + myAtoi);
    }

    //请你来实现一个 atoi 函数，使其能将字符串转换成整数。
    public static int myAtoi(String s) {
        //获取数字
        char[] chars = s.toCharArray();
        int result = 0;
        boolean positive = true;
        boolean first = false;

        for (char c : chars) {
            if (c == ' ' && !first) {
                continue;
            }

            if (c == '-' && !first) {
                first = true;
                positive = false;
                continue;
            }

            if (c == '+' && !first) {
                first = true;
                positive = true;
                continue;
            }

            //char 0-9 对应的ASCII 码 为48-57
            //int b = (int) c;
            if (c >= '0' && c <= '9') {
                first = true;
                int temp = result;
                result = result * 10 + (c - '0');
                //超过int最大值范围,值会变化，直接返回Integer.MIN_VALUE;
                if (result / 10 != temp) {
                    return positive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
                continue;
            }
            break;
        }
        //再转换成int
        return positive ? result : -result;
    }
}
