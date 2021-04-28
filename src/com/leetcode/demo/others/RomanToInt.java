package com.leetcode.demo.others;

/**
 * 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II
 */
public class RomanToInt {

    public static void test() {
        int value = romanToInt("DIII");//M+CM+XC+IV
        System.out.println("value:" + value);//M+M+CM
    }

    //6ms
    public static int romanToInt2(String s) {
        String[] arr = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] num = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            while (s.startsWith(arr[i])) {
                result += num[i];
                s = s.substring(arr[i].length());
            }
        }
        return result;
    }


    //9ms
    public static int romanToInt(String s) {
        String[] arr = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] num = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            int maxLength = 0;
            while (maxLength < 2) {//每个单独的罗马字最长只有两位；
                maxLength++;
                if (s.length() < maxLength) {
                    break;
                }
                String sub = s.substring(0, maxLength);
                if (sub.equals(arr[i])) {
                    s = s.substring(maxLength);
                    result += num[i];
                    maxLength = 0;
                }
            }
        }
        return result;
    }
}
