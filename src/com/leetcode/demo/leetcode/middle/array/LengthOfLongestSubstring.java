package com.leetcode.demo.leetcode.middle.array;

public class LengthOfLongestSubstring {

    public static void test() {
        String str = "aab";
        int length = lengthOfLongestSubstring(str);
        System.out.println("length:" + length);
    }

    //384ms 击败5%的用户  效率太差
    //给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
    //pwpwkew 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
    //请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
    private static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int maxLength = 0;
        int lastStartIndex = 0;
        StringBuilder sub = new StringBuilder();
        String cur;
        int index = 0;
        int length = s.length();
        while (index < length) {
            cur = s.substring(index, index + 1);
            //子串不包含当前字符
            if (!sub.toString().contains(cur)) {
                sub.append(cur);
                index++;
                continue;
            }

            maxLength = Math.max(maxLength, sub.toString().length());
            lastStartIndex++;
            index = lastStartIndex;
            sub = new StringBuilder();
        }
        maxLength = Math.max(maxLength, sub.toString().length());
        return maxLength;
    }

    //299ms
    private static int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] chars = s.toCharArray();
        int length = chars.length;
        int curIndex = 0;
        int startIndex = 0;
        int maxLength = 0;
        String substring;
        while (curIndex < length) {
            substring = s.substring(startIndex, curIndex);
            if (!substring.contains(chars[curIndex] + "")) {
                curIndex++;
                continue;
            }
            maxLength = Math.max(maxLength, curIndex - startIndex);
            startIndex++;
            curIndex = startIndex;
        }
        return Math.max(maxLength, curIndex - startIndex);
    }

    //161ms
    //s 由英文字母、数字、符号和空格组成
    private static int lengthOfLongestSubstring3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int max = 0;
        int startIndex = 0;
        int index = 0;
        int length = s.length();
        while (index < length) {
            //找到当前字符在[startIndex,length]的下标；
            int indexOf = s.indexOf(s.charAt(index), startIndex);
            //不在连续的子串中
            if (indexOf >= index) {
                index++;
                continue;
            }

            //在连续的子串中；
            //记录最大值
            max = Math.max(max, index - startIndex);

            //将下标改为indexOf；
            indexOf++;
            index = indexOf;
            startIndex = indexOf;
        }
        return Math.max(max, index - startIndex);
    }
}
