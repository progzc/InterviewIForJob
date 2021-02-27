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
