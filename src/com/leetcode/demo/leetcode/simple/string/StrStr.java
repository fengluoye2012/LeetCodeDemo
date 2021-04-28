package com.leetcode.demo.leetcode.simple.string;

//给定一个haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回 -1。
public class StrStr {

    //abc  ab
    public int strStr2(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }

        int length = haystack.length();
        if (needle.length() > length) {
            return -1;
        }
        int needleLength = needle.length();
        char first = needle.charAt(0);
        for (int i = 0; i < length; i++) {
            //找到第一个相等的字符
            if (haystack.charAt(i) != first) {
                while (++i < length && haystack.charAt(i) != first) ;
            }

            if (i > needleLength) {
                break;
            }

            int start = i - 1;//第一个相等的index;

            //找到结尾index
            while (++start < length && start < needleLength && haystack.charAt(start) == needle.charAt(start - i)) ;

            if (start - i + 1 == needleLength) {
                return i - 1;
            }
        }
        return -1;
    }

    //4ms
    public int strStr(String haystack, String needle) {
        int targetLength = needle.length();
        if (targetLength == 0) {
            return 0;
        }

        int length = haystack.length();
        if (length < targetLength) {
            return -1;
        }
        int findIndex = -1;
        for (int i = 0; i < length; i++) {
            //没有找到起始index
            if (findIndex < 0) {
                if (haystack.charAt(i) == needle.charAt(0)) {
                    findIndex = i;
                }
                continue;
            }

            int range = i - findIndex;
            if (range == targetLength) {
                return findIndex;
            }

            //对应的字符不相等,重置；
            if (haystack.charAt(i) != needle.charAt(range)) {
                i = findIndex;//重新设置i；
                findIndex = -1;
            }
        }

        //处理needle的长度大于target长度
        if (findIndex >= 0 && length - findIndex < targetLength) {
            return -1;
        }
        return findIndex;
    }
}
