package com.leetcode.demo.simple.string;

/**
 * 反转字符串
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 */
public class ReverseString {

    public void reverseString(char[] s) {
        int start = 0;
        int end = s.length - 1;

        while (start < end) {
            char tem = s[start];
            s[start] = s[end];
            s[end] = tem;
            start++;
            end--;
        }
    }
}
