package com.leetcode.demo.leetcode.simple.dynamicPlanning;

/**
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
 * 注意：你不能在买入股票前卖出股票。
 */
public class MaxProfit2 {

    public static void test() {
//        int[] price = {7, 1, 5, 3, 6, 4};
        int[] price = {7, 1, 5, 3, 0, 6, 4};
        int maxProfit = maxProfit(price);
        System.out.println("maxProfit:" + maxProfit);
    }

    //curIndex 对应的Value 小于LastIndex对应的Value;则卖出；否则持有
    private static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int buyIndex = -1;
        int i = 0;
        int length = prices.length - 1;

        while (i < length) {
            int curPrice = prices[i];
            //获取最大收益
            if (buyIndex >= 0) {
                int buyPrice = prices[buyIndex];
                if (curPrice > buyPrice) {
                    maxProfit = Math.max(curPrice - buyPrice, maxProfit);
                } else {
                    //如果遇到更低的买入价格
                    buyIndex = i;
                }
                i++;
                continue;
            }

            //买入
            if (curPrice < prices[i + 1]) {
                buyIndex = i;
            }
            i++;
        }

        if (buyIndex >= 0) {
            maxProfit = Math.max(prices[length] - prices[buyIndex], maxProfit);
        }
        return maxProfit;
    }

    //{7, 1, 5, 3, 6, 4}
    //定义两个变量，分别记录最小价格和最大利润
    private static int maxProfit2(int[] prices) {
        int maxProfile = 0;
        if (prices == null || prices.length == 0) {
            return maxProfile;
        }

        int minPrice = prices[0];
        for (int curPrice : prices) {
            //当前价格小于最低价格，意味着最大利润将为负数；
            if (curPrice < minPrice) {
                minPrice = curPrice;
                continue;
            }

            //此时的最大利润
            int curMaxProfit = curPrice - minPrice;
            //之前的最大利润和此时的最大利润对比；
            if (curMaxProfit > maxProfile) {
                maxProfile = curMaxProfit;
            }
        }
        return maxProfile;
    }

    //你最多只允许完成一笔交易（即买入和卖出一支股票一次）
    private static int maxProfile3(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int maxProfile = 0;
        int minPrice = prices[0];

        //[7,1,5,3,6,4]
        //[7,1,5,3，-4,6,4]
        for (int price : prices) {
            //买入当天不允许卖出
            if (price < minPrice) {
                minPrice = price;
                continue;
            }
            maxProfile = Math.max(maxProfile, price - minPrice);
        }
        return maxProfile;
    }
}
