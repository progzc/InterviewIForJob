package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 买卖股票的最佳时机
 * @Author zhaochao
 * @Date 2020/12/15 9:57
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0121_BestTimeToBuyAndSellStock {

    /**
     * LeetCode地址：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/description/
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
     * 注意：你不能在买入股票前卖出股票。
     * <p>
     * 示例 1:
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     * <p>
     * 示例 2:
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     */
    public static void main(String[] args) {
        int[][] nums = {{7, 1, 5, 3, 6, 4}, {7, 6, 4, 3, 1}, {7, 7, 7, 7}};

        System.out.println("---------双指针法----------");
        Arrays.stream(nums).forEach(num -> {
            System.out.println(Arrays.toString(num));
            System.out.println(doublePointerSolve(num));
        });

        System.out.println("---------数组遍历法----------");
        Arrays.stream(nums).forEach(num -> {
            System.out.println(Arrays.toString(num));
            System.out.println(doublePointerSolve2(num));
        });

    }

    /**
     * 双指针法：
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int doublePointerSolve(int[] num) {
        if (num == null || num.length < 2) {
            return 0;
        }

        int left = 0;
        int right = num.length - 1;
        int max = 0;
        while (right > left) {
            if (num[left] - num[right] >= 0) {
                left++;
            } else {
                max = Math.max(max, num[right] - num[left]);
                right--;
            }
        }
        return max;
    }

    /**
     * 数组遍历：官网解法（比双指针法空间复杂度更小）
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int doublePointerSolve2(int[] num) {
        if (num == null || num.length < 2) {
            return 0;
        }

        int minprice = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < num.length; i++) {
            if (num[i] < minprice) {
                minprice = num[i];
            } else if (num[i] - minprice > max) {
                max = num[i] - minprice;
            }
        }
        return max;
    }

}
