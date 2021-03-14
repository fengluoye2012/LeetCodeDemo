package com.leetcode.demo.simple.string;

//给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
//提示：你可以假定该字符串只包含小写字母。
public class FirstUniqChar {
    //根据
    public int firstUniqChar2(String s) {
        int index = -1;
        for (char i = 'a'; i <= 'z'; i++) {
            //不重复的字符
            if (s.indexOf(i) != -1 && s.indexOf(i) == s.lastIndexOf(i)) {
                //第一个不重复的字符
                if (s.indexOf(i) < index || index == -1) {
                    index = s.indexOf(i);
                }
            }
        }
        return index;
    }


    //暴力法 双循环
    //loveleetcode
    public int firstUniqChar(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        if (s.length() < 2) {
            return 0;
        }
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            boolean next = false;
            for (int j = 0; j < chars.length; j++) {
                if (chars[i] == chars[j] && i != j) {
                    next = true;
                    break;
                }
            }
            if (!next) {
                return i;
            }
        }
        return -1;
    }
}
