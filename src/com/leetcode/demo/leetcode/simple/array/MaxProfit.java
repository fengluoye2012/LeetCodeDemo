package com.leetcode.demo.leetcode.simple.array;

/**
 * 给定一个数组，它的第i个元素是一支给定股票第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 */
public class MaxProfit {
    //只要下一天的价格高于今天，就买入；否则就卖出
    public int maxProfit(int[] prices) {
        if (prices == null) {
            return 0;
        }

        //[1,2,3,4,5]
        //[1,2,4,7]
        int maxProfit = 0;
        int lastIndex = -1;
        boolean has = false;//是否持有
        for (int i = 0; i < prices.length - 1; i++) {
            //是否应该持有（你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。）
            if (!has && prices[i + 1] > prices[i]) {
                has = true;
                lastIndex = i;
                continue;
            }

            //判断是否要抛出；
            if (prices[i + 1] < prices[i] && has) {
                maxProfit += (prices[i] - prices[lastIndex]);
                has = false;
            }
        }

        if (has) {
            maxProfit += (prices[prices.length - 1] - prices[lastIndex]);
        }
        return maxProfit;
    }
}
