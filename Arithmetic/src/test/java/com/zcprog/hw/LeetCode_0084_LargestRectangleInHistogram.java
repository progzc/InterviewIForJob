package com.zcprog.hw;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Description 柱状图中最大的矩形
 * @Author zhaochao
 * @Date 2021/2/27 11:19
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

/**
 * 给定n个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为1。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 */
public class LeetCode_0084_LargestRectangleInHistogram {
    public static void main(String[] args) {
        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println(largestRectangleArea(heights));
    }

    public static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);
        Stack<Integer> mono_stack = new Stack<Integer>();
        for (int i = 0; i < n; ++i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                right[mono_stack.peek()] = i;
                mono_stack.pop();
            }
            left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
            mono_stack.push(i);
        }
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }
}
