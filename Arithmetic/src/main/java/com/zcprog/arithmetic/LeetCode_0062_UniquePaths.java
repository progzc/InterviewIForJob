package com.zcprog.arithmetic;

/**
 * @Description 不同路径
 * @Author zhaochao
 * @Date 2020/12/22 22:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0062_UniquePaths {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/unique-paths/
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     * 问总共有多少条不同的路径？
     * <p>
     * 示例 1:
     * 输入: m = 3, n = 2
     * 输出: 3
     * 解释:
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向右 -> 向下
     * 2. 向右 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向右
     * <p>
     * 示例 2:
     * 输入: m = 7, n = 3
     * 输出: 28
     * <p>
     * 提示：
     * 1 <= m, n <= 100
     * 题目数据保证答案小于等于 2 * 10 ^ 9
     */
    public static void main(String[] args) {
        int[] ms = {2, 7, 51};
        int[] ns = {2, 3, 9};
        for (int i = 0; i < ms.length; i++) {
            System.out.println(recursionSolve(ms[i], ns[i]));
            System.out.println(iterationSolve(ms[i], ns[i]));
        }
    }

    /**
     * 递归：会超时
     * 时间复杂度：O(2^m+2^n)
     * 空间复杂度: O(n)
     */
    private static int recursionSolve(int m, int n) {
        if (n == 1 || m == 1) {
            return 1;
        }
        return recursionSolve(m - 1, n) + recursionSolve(m, n - 1);
    }

    /**
     * 迭代
     * 时间复杂度：O(mn)
     * 空间复杂度：O(mn)
     */
    private static int iterationSolve(int m, int n) {
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            result[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            result[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                result[i][j] = result[i - 1][j] + result[i][j - 1];
            }
        }
        return result[m - 1][n - 1];
    }
}
