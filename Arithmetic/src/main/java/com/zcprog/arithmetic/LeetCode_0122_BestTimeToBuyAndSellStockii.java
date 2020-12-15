package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 买卖股票的最佳时机 II
 * @Author zhaochao
 * @Date 2020/12/15 10:35
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0122_BestTimeToBuyAndSellStockii {

    /**
     * LeetCode地址：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/description/
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * <p>
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     * <p>
     * 示例 2:
     * 输入: [1,2,3,4,5]
     * 输出: 4
     * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
     * 因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     * <p>
     * 示例 3:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     * <p>
     * 提示：
     * 1 <= prices.length <= 3 * 10 ^ 4
     * 0 <= prices[i] <= 10 ^ 4
     */
    public static void main(String[] args) {
        int[][] nums = {{7, 1, 5, 3, 6, 4}, {1, 2, 3, 4, 5}, {7, 6, 4, 3, 1}};
        System.out.println("---------贪心算法----------");
        Arrays.stream(nums).forEach(num -> {
            System.out.println(Arrays.toString(num));
            System.out.println(greedySolve(num));
        });

        System.out.println("---------动态规划----------");
        Arrays.stream(nums).forEach(num -> {
            System.out.println(Arrays.toString(num));
            System.out.println(dpSolve1(num));
        });

        System.out.println("---------动态规划优化----------");
        Arrays.stream(nums).forEach(num -> {
            System.out.println(Arrays.toString(num));
            System.out.println(dpSolve2(num));
        });
    }

    /**
     * 贪心算法：
     * 时间复杂度：O(n);
     * 空间复杂度：O(1);
     */
    private static int greedySolve(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] - prices[i + 1] < 0) {
                sum += prices[i + 1] - prices[i];
            }
        }
        return sum;
    }

    /**
     * 动态规划算法：
     * 定义状态：
     * (1) dp[i][0]表示第i天交易完后手里没有股票的最大利润;
     * (2) dp[i][1]表示第i天交易完后手里持有一支股票的最大利润（i从0开始）。
     * 状态转移方程：
     * dp[i][0]=max{dp[i−1][0],dp[i−1][1]+prices[i]}
     * dp[i][1]=max{dp[i−1][1],dp[i−1][0]−prices[i]}
     * 初始状态：
     * dp[0][0]=max{dp[i-1][1], dp[i-1][0]-prices[i]}
     *
     * <p>
     * 时间复杂度：O(n);
     * 空间复杂度：O(n);
     */
    private static int dpSolve1(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; ++i) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    /**
     * 动态规划算法：优化空间
     * 时间复杂度：O(n);
     * 空间复杂度：O(1);
     */
    private static int dpSolve2(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        int dp0 = 0, dp1 = -prices[0];
        for (int i = 1; i < n; ++i) {
            int newDp0 = Math.max(dp0, dp1 + prices[i]);
            int newDp1 = Math.max(dp1, dp0 - prices[i]);
            dp0 = newDp0;
            dp1 = newDp1;
        }
        return dp0;
    }
}
