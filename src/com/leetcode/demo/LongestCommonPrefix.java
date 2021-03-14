package com.leetcode.demo;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""
 */
public class LongestCommonPrefix {

    /**
     * 0ms
     * 找到最小的长度的字符串，依次往后寻找公共的前缀，事件复杂度 m*n
     * ["flower","flow","fiight"]
     */
    public String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        //找到长度最小的字符串
        String target = strs[0];
        for (String str : strs) {
            if (str.length() < target.length()) {
                target = str;
            }
        }

        for (String str : strs) {
            while (!str.startsWith(target)) {
                target = target.substring(0, target.length() - 1);
            }
        }
        return target;
    }

    //5ms
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int index = 0;
        boolean prefix = true;
        String target = strs[0];//任选一个
        for (int i = 0; i < target.length(); i++) {
            for (int j = 1; j < strs.length; j++) {
                //不是公共前缀
                if (!strs[j].startsWith(target.substring(0, i + 1))) {
                    prefix = false;
                    break;
                }
            }
            if (!prefix) {
                break;
            }
            index++;
        }
        return target.substring(0, index);
    }
}
