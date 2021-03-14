package com.leetcode.demo;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 */
public class Palindrome {

    public static void test() {
//        boolean palindrome = isPalindrome(101);
        boolean palindrome = isPalindrome2(11);
        if (palindrome) {
            System.out.println("palindrome:");
        }
    }

    /**
     * 不需要把数字全部反转
     * 效率和内存消耗最优
     */
    public static boolean isPalindrome2(int x) {
        if (x < 0 || (x > 0 && x % 10 == 0)) {
            return false;
        }

        int tem = x;//前半部分
        int result = 0;//后半部分
        while (tem > result) {
            result = result * 10 + tem % 10;
            tem = tem / 10;
        }

        //偶数和奇数判断
        return result == tem || result / 10 == tem;
    }

    //
    public static boolean isPalindromeNew(int x) {
        if (x < 0) {
            return false;
        }
        char[] chars = Integer.toString(x).toCharArray();
        int starIndex = 0;
        int endIndex = chars.length - 1;
        while (starIndex < endIndex) {
            if (chars[starIndex] != chars[endIndex]) {
                return false;
            }
            starIndex++;
            endIndex--;
        }

        return true;
    }

    //反转数字，判断是否相等：效率不高
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        //反转数字，判断反转前后是否相等；
        int tem = x;
        int result = 0;
        while (tem != 0) {
            result = result * 10 + tem % 10;
            tem = tem / 10;
        }
        return result == x;
    }
}
