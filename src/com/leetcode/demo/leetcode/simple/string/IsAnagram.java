package com.leetcode.demo.leetcode.simple.string;

import java.util.Arrays;

//给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
//你可以假设字符串只包含小写字母。
//两个字符串的字符是否完全一致
public class IsAnagram {

    //4 ms
    //使用字符char -'a' 作为数组下标；记录每个char的次数；对比过程中减少次数；
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int length = s.length();
        //记录字符串s中的各个字符的数量
        int[] count = new int[26];
        for (int i = 0; i < length; i++) {
            count[s.charAt(i)-'a']++;
        }

        //字符串t中是否包含所有的s中的字符
        for (int j = 0; j < length; j++) {
            int value = --count[t.charAt(j) - 'a'];//前--是先减再使用；后--是先使用再减；
            if (value < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 执行用时：3 ms, 在所有 Java 提交中击败了84.26% 的用户
     * 内存消耗：38.7 MB, 在所有 Java 提交中击败了57.88%的用户
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] chars = s.toCharArray();
        char[] charArray = t.toCharArray();
        //NLogN+N
        Arrays.sort(chars);
        Arrays.sort(charArray);

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != charArray[i]) {
                return false;
            }
        }
        return true;
    }
}
