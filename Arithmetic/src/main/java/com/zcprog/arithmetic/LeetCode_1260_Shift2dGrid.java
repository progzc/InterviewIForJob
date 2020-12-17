package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 二维网格迁移
 * @Author zhaochao
 * @Date 2020/12/17 12:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_1260_Shift2dGrid {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/shift-2d-grid/description/
     * 给你一个 m 行 n 列的二维网格 grid 和一个整数 k。你需要将 grid 迁移 k 次。
     * <p>
     * 每次「迁移」操作将会引发下述活动：
     * 位于 grid[i][j] 的元素将会移动到 grid[i][j + 1]。
     * 位于 grid[i][m - 1] 的元素将会移动到 grid[i + 1][0]。
     * 位于 grid[n - 1][m - 1] 的元素将会移动到 grid[0][0]。
     * 请你返回 k 次迁移操作后最终得到的 二维网格。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * 输入：grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1
     * 输出：[[9,1,2],[3,4,5],[6,7,8]]
     * <p>
     * 示例 2：
     * 输入：grid = [[3,8,1,9],[19,7,2,5],[4,6,11,10],[12,0,21,13]], k = 4
     * 输出：[[12,0,21,13],[3,8,1,9],[19,7,2,5],[4,6,11,10]]
     * <p>
     * 示例 3：
     * 输入：grid = [[1,2,3],[4,5,6],[7,8,9]], k = 9
     * 输出：[[1,2,3],[4,5,6],[7,8,9]]
     * <p>
     * 提示：
     * 1 <= grid.length <= 50
     * 1 <= grid[i].length <= 50
     * -1000 <= grid[i][j] <= 1000
     * 0 <= k <= 100
     */
    public static void main(String[] args) {
        int[][] grid = {{3, 8, 1, 9}, {19, 7, 2, 5}, {4, 6, 11, 10}, {12, 0, 21, 13}};
        int k = 4;
        print2Array(grid);
        System.out.println(shiftGrid(grid, k));
    }

    /**
     * 取模
     * 时间复杂度：O(m*n)
     * 空间复杂度：O(m*n)
     */
    private static List<List<Integer>> shiftGrid(int[][] grid, int k) {
        if (grid == null || grid.length < 1 || grid[0].length < 1) {
            return null;
        }
        // 行数
        int m = grid.length;
        // 列数
        int n = grid[0].length;

        List<List<Integer>> newGrid = new ArrayList<>();
        for (int row = 0; row < m; row++) {
            List<Integer> newRow = new ArrayList<>();
            newGrid.add(newRow);
            for (int col = 0; col < n; col++) {
                newRow.add(0);
            }
        }

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                int newCol = (col + k) % n;
                int wrapAroundCount = (col + k) / n;
                int newRow = (row + wrapAroundCount) % m;
                newGrid.get(newRow).set(newCol, grid[row][col]);
            }
        }

        return newGrid;
    }

    public static void print2Array(int[][] nums) {
        if (nums == null || nums.length < 1 || nums[0].length < 1) {
            return;
        }
        Arrays.stream(nums).forEach(e -> {
            System.out.println(Arrays.toString(e));
        });
    }
}
