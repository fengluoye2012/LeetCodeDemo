package com.leetcode.demo.middle.array;

//给你一个字符串 s，找到 s 中最长的回文子串。
//“回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
//1 <= s.length <= 1000
//s 仅由数字和英文字母（大写和/或小写）组成
public class LongestPalindromeStr {

    public static void test() {
        String palindrome = longestPalindrome3("babad");
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


    //abcba1
    //1bb1
    //11ms 击败93%的用户
    private static String longestPalindrome3(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        int length = s.length();

        int[] range = new int[2];
        char[] chars = s.toCharArray();
        //把回文看成中间的部分全是同一字符，左右部分相对称
        //找到下一个与当前字符不同的字符
        for (int i = 0; i < length; i++) {
            i = findLongest(chars, i, range);
        }
        return s.substring(range[0], range[1] + 1);
    }
        char[] chars = s.toCharArray();
        int[] range = new int[2];
        //回文串 中间相同 两边对称
        for (int i = 0; i < chars.length - 1; i++) {
            i = findLongest2(i, chars, range);
        }
        return s.substring(range[0], range[1] + 1);
    }


    //ababa
    //把回文看成中间的部分全是同一字符，左右部分相对称
    private static int findLongest(char[] chars, int low, int[] range) {
        int high = low;
        //找到中间部分
        while (high < chars.length - 1 && chars[high + 1] == chars[low]) {
            high++;
        }

        //定位中间部分的最后一个字符
        int ans = high;
        //从中间往左右扩散
        while (low > 0 && high < chars.length - 1 && chars[low - 1] == chars[high + 1]) {
            low--;
            high++;
        }

        //记录最大长度
        if (high - low > range[1] - range[0]) {
            range[0] = low;
            range[high] = high;
    private static int findLongest2(int low, char[] chars, int[] range) {
        int high = low;
        //找到中间相等部分
        while (high < chars.length - 1 && chars[low] == chars[high + 1]) {
            high++;
        }

        //定位中间部分的最后一个字符,可以将ans作为返回值返回，改变i;重复的元素可以跳过
        int ans = high;

        //两边对称
        while (low > 0 && high < chars.length - 1 && chars[low - 1] == chars[high + 1]) {
            low--;
            high++;
        }

        if (range[1] - range[0] < high - low) {
            range[0] = low;
            range[1] = high;
        }
        return ans;
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
