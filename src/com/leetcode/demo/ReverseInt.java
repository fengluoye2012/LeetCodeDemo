package com.leetcode.demo;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * <p>
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−2^31,  2^31 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 * <p>
 * 123456789
 * <p>
 * 9
 */
public class ReverseInt {

    public static void test() {
//        int reverse = reverse();
        int reverse = reverseNew(-1263847419);
        System.out.println("reverse:" + reverse);
    }

    /**
     * 反转
     * 1234567
     *
     * @param x
     * @return
     */
    public static int reverseNew(int x) {
        int result = 0;
        while (x != 0) {
            int tem = result;
            result = result * 10 + x % 10;
            x = x / 10;

            //处理溢出,溢出了就不满足下列条件,溢出也不会报错，只是值变化了；
            if (result / 10 != tem) {
                return 0;
            }
        }
        return result;
    }

    //int 最小值
    public static int reverse(int x) {
        if (x == Integer.MIN_VALUE) {
            return 0;
        }

        String str = Math.abs(x) + "";
        char[] chars = str.toCharArray();
        int startIndex = 0;
        int endIndex = chars.length - 1;

        while (startIndex < endIndex) {
            char start = chars[startIndex];
            char end = chars[endIndex];
            chars[startIndex] = end;
            chars[endIndex] = start;

            startIndex++;
            endIndex--;
        }

        //非0的数字字符串
        StringBuilder reverseStr = new StringBuilder();
        boolean zero = true;
        //找到首位非0 下标
        for (char aChar : chars) {
            if (aChar == '0' && zero) {
                continue;
            }
            zero = false;
            reverseStr.append(aChar);
        }

        long valueOf = 0L;
        if (reverseStr.toString().length() > 0) {
            valueOf = x > 0 ? Long.parseLong(reverseStr.toString()) : -Long.parseLong(reverseStr.toString());
            if ((x > 0 && valueOf > Integer.MAX_VALUE) || (x < 0 && valueOf < Integer.MIN_VALUE)) {
                return 0;
            }
        }

        return (int) valueOf;
    }

}
