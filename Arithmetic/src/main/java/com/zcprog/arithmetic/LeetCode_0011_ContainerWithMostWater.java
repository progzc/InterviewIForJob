package com.zcprog.arithmetic;

/**
 * @Description 盛最多水得容器
 * @Author zhaochao
 * @Date 2020/12/20 23:47
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0011_ContainerWithMostWater {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/container-with-most-water/description/
     * 给你n个非负整数a1，a2，...，an,每个数代表坐标中的一个点(i, ai) 。
     * 在坐标内画n条垂直线，垂直线i的两个端点分别为(i, ai)和(i, 0)。找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。
     * 说明:你不能倾斜容器,且n的值至少为2。
     * 图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为49。
     * 示例：
     * 输入：[1,8,6,2,5,4,8,3,7]
     * 输出：49
     */
    public static void main(String[] args) {
        int[][] heights = {{1, 8, 6, 2, 5, 4, 8, 3, 7}, {1, 1}, {4, 3, 2, 1, 4}, {1, 2, 1}};
        for (int[] height : heights) {
            System.out.println(solve(height));
            System.out.println(doublePointerSolve1(height));
            System.out.println(doublePointerSolve2(height));
        }
    }

    /**
     * 暴力法
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static int solve(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        int area = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                area = Math.max(Math.min(height[i], height[j]) * (j - i), area);
            }
        }
        return area;
    }

    /**
     * 双指针法：优化
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int doublePointerSolve2(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int area = Math.min(height[left], height[right]) * (right - left);
        while (right > left) {
            if (height[right] >= height[left]) {
                int m = left + 1;
                while (m < right && height[m] <= height[left]) {
                    m++;
                }
                left = m;
            } else {
                int n = right - 1;
                while (n > left && height[n] <= height[right]) {
                    n--;
                }
                right = n;
            }
            area = Math.max(Math.min(height[left], height[right]) * (right - left), area);
        }
        return area;
    }

    /**
     * 双指针法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int doublePointerSolve1(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int ans = 0;
        while (right > left) {
            int area = Math.min(height[left], height[right]) * (right - left);
            ans = Math.max(ans, area);
            if (height[right] >= height[left]) {
                left++;
            } else {
                right--;
            }
        }
        return ans;
    }
}


