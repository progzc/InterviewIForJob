package com.zcprog.hw;

/**
 * @Description 接雨水（单调栈）
 * @Author zhaochao
 * @Date 2021/2/27 17:12
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * <p>
 * 示例1
 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出：6
 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
 * <p>
 * 示例2
 * 输入：height = [4,2,0,3,2,5]
 * 输出：9
 */
public class LeetCode_0042_TrappingRainWater {
    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trap(height));
    }

    public static int trap(int[] height) {
//        // 方法一：常规方法
//        if (height == null || height.length == 0)
//            return 0;
//        int ans = 0;
//        int size = height.length;
//        int[] left_max = new int[size];
//        int[] right_max = new int[size];
//        left_max[0] = height[0];
//        for (int i = 1; i < size; i++) {
//            left_max[i] = Math.max(height[i], left_max[i - 1]);
//        }
//        right_max[size - 1] = height[size - 1];
//        for (int i = size - 2; i >= 0; i--) {
//            right_max[i] = Math.max(height[i], right_max[i + 1]);
//        }
//        for (int i = 1; i < size - 1; i++) {
//            ans += Math.min(left_max[i], right_max[i]) - height[i];
//        }
//        return ans;

        // 方法二：栈
        int ans = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        for (int current = 0; current < height.length; current++) {
            while (!stack.isEmpty() && height[current] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty())
                    break;
                int distance = current - stack.peek() - 1;
                int bounded_height = Math.min(height[current], height[stack.peek()]) - height[top];
                ans += distance * bounded_height;
            }
            stack.push(current);
        }
        return ans;
    }
}
