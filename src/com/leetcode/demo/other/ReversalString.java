package com.leetcode.demo.other;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入：Android is powerful
 * 输出：powerful is Android
 * <p>
 * 不使用String StringBulder
 */
public class ReversalString {

    public static void test() {
        reversalString("Android is powerful");
    }

    private static void reversalString(String str) {
        char[] chars = str.toCharArray();

        List<List<Character>> list = new ArrayList<>();
        List<Character> sub = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == ' ') {
                list.add(sub);
                sub = new ArrayList<>();
                continue;
            }
            sub.add(c);
        }
        list.add(sub);

        char[] newChars = new char[str.length()];
        int i = 0;

        int size = list.size();
        while (size > 0) {
            List<Character> sub2 = list.get(size - 1);
            for (char c : sub2) {
                newChars[i] = c;
                i++;
            }

            if (size > 1) {
                newChars[i] = ' ';
                i++;
            }
            size--;
        }

        String target = new String(newChars);
        System.out.println("target = " + target);
    }
}
