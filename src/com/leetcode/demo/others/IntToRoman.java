package com.leetcode.demo.others;

public class IntToRoman {

    /**
     * 罗列特殊情况，
     * 2222
     * I II III IV V VI VII VIII IX X
     */
    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] reps = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num>0){
                num -= values[i];
                stringBuilder.append(reps[i]);
            }
        }
        return stringBuilder.toString();
    }

}
