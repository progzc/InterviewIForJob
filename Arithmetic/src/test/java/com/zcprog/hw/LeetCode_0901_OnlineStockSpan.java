package com.zcprog.hw;

import java.util.Scanner;
import java.util.Stack;

/**
 * @Description 股票价格跨度
 * @Author zhaochao
 * @Date 2021/2/27 14:27
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

/**
 * 编写一个 StockSpanner 类，它收集某些股票的每日报价，并返回该股票当日价格的跨度。
 * <p>
 * 今天股票价格的跨度被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
 * <p>
 * 例如，如果未来7天股票的价格是 [100, 80, 60, 70, 60, 75, 85]，那么股票跨度将是 [1, 1, 1, 2, 1, 4, 6]。
 * <p>
 */
public class LeetCode_0901_OnlineStockSpan {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        StockSpanner stockSpanner = new StockSpanner();
        while (in.hasNextInt()) {
            int num = in.nextInt();
            System.out.println(stockSpanner.next(num));
        }
    }

    static class StockSpanner {
        private Stack<Integer> prices;
        private Stack<Integer> weights;

        public StockSpanner() {
            this.prices = new Stack();
            this.weights = new Stack();
        }

        public int next(int price) {
            int w = 1;
            while (!prices.isEmpty() && prices.peek() <= price) {
                prices.pop();
                w += weights.pop();
            }
            prices.push(price);
            weights.push(w);
            return w;
        }
    }

}
