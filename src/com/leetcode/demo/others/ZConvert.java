package com.leetcode.demo.others;

import java.util.*;

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 * <p>
 * 请你实现这个将字符串进行指定行数变换的函数：
 * <p>
 * string convert(string s, int numRows);
 */
public class ZConvert {

    //将每一行的字符串保存下来，再拼接
    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        int rowCount = Math.min(s.length(), numRows);
        for (int i = 0; i < rowCount; i++) {
            rows.add(new StringBuilder());
        }

        int curRow = 0;
        boolean goDown = false;
        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            //如果是第一行或者最后一行改变顺序
            if (curRow == 0 || curRow == numRows - 1) {
                goDown = !goDown;
            }
            curRow += (goDown ? 1 : -1);
        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder builder : rows) {
            ret.append(builder);
        }
        return ret.toString();
    }

}
