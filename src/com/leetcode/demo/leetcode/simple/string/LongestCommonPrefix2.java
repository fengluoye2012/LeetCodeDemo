package com.leetcode.demo.leetcode.simple.string;

//编写一个函数来查找字符串数组中的最长公共前缀。
//如果不存在公共前缀，返回空字符串 ""。
public class LongestCommonPrefix2 {

    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        //找到长度最短的字符串
        String target = strs[0];
        for (String str : strs) {
            if (str.length() < target.length()) {
                target = str;
            }
        }

        //判断Str是否以target开头；
        for (String str : strs) {
            //找到当前Str和target共同的前缀；
            while (!str.startsWith(target)) {
                target = target.substring(0, target.length() - 1);
            }
        }
        return target;
    }
}
