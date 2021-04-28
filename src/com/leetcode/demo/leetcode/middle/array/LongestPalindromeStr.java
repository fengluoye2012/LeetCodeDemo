package com.leetcode.demo.leetcode.middle.array;

//给你一个字符串 s，找到 s 中最长的回文子串。
//“回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
//1 <= s.length <= 1000
//s 仅由数字和英文字母（大写和/或小写）组成
public class LongestPalindromeStr {

    public static void test() {
        String palindrome = longestPalindrome("babad");
        System.out.println("palindrome:" + palindrome);
    }

    //1970ms 耗时太长，如何优化呢
    //babad
    //如果当前是回文子串 那再往后缩小一圈  肯定也是回文子串
    private static String longestPalindrome(String s) {
        if (s == null || s.length() < 2 || isPalindrome(s)) {
            return s;
        }

        int length = s.length() + 1;
        String sub;
        String longest = "";

        //找到各种连续子串，判断是否为回文子串
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                sub = s.substring(i, j);
                if (isPalindrome(sub) && sub.length() > longest.length()) {
                    longest = sub;
                }
            }
        }
        return longest;
    }


    //abcba
    //1bb1
    private static String longestPalindrome2(String s) {
        if (s == null || s.length() < 2 || isPalindrome(s)) {
            return s;
        }

        int length = s.length() + 1;
        String sub;
        String longest = s.substring(0, 1);

        //找到各种连续子串，判断是否为回文子串
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                sub = s.substring(j, i + (i - j));
                if (isPalindrome(sub)) {
                    longest = sub;
                    break;
                }
            }
        }
        return longest;
    }

    //aa aba
    private static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        int i = 0;
        int j = str.length() - 1;

        while (i < j) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
