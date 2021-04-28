package com.leetcode.demo.leetcode.simple.string;

//给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
//说明：本题中，我们将空字符串定义为有效的回文串。
public class IsPalindrome {
    public static void test(){
        String s = "A man, a plan, a canal: Panama";
        String s1 = "0P";
        boolean palindrome = isPalindrome(s1);
        System.out.println("palindrome:"+palindrome);
    }

    public static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }

        if (s.length() <= 1) {
            return true;
        }

        int start = 0;
        int end = s.length() - 1;
        while (start < end) {
            //'A'  65
            //'a' 97
            char startChar = s.charAt(start);
            char endChar = s.charAt(end);

            if (!isValid(startChar)) {
                start++;
                continue;
            }

            if (!isValid(endChar)) {
                end--;
                continue;
            }

            int startValue = isSmallLetter(startChar) ? startChar-'a' : startChar - 'A';
            int endValue = isSmallLetter(endChar) ? endChar-'a' : endChar - 'A';

            if (startValue != endValue) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static boolean isValid(char c) {
        return (c>='0' && c <= '9')|| isSmallLetter(c) || isBigLetter(c);
    }

    public static boolean isSmallLetter(char c) {
        return (c >= 'a' && c <= 'z');
    }

    public static boolean isBigLetter(char c) {
        return (c >= 'A' && c <= 'Z');
    }
}
