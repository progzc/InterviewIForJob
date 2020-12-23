package com.zcprog.arithmetic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 矩阵置零
 * @Author zhaochao
 * @Date 2020/12/22 22:47
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0073_SetMatrixZeroes {
    /**
     * LeetCode：https://leetcode-cn.com/problems/set-matrix-zeroes/
     * 给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。
     * <p>
     * 示例 1:
     * 输入:
     * [
     * [1,1,1],
     * [1,0,1],
     * [1,1,1]
     * ]
     * 输出:
     * [
     * [1,0,1],
     * [0,0,0],
     * [1,0,1]
     * ]
     * <p>
     * 示例 2:
     * 输入:
     * [
     * [0,1,2,0],
     * [3,4,5,2],
     * [1,3,1,5]
     * ]
     * 输出:
     * [
     * [0,0,0,0],
     * [0,4,5,0],
     * [0,3,1,0]
     * ]
     * <p>
     * 进阶:
     * 一个直接的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
     * 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
     * 你能想出一个常数空间的解决方案吗？
     */
    public static void main(String[] args) {
//        int[][] matrix = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        int[][] matrix = {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
//        extraSpaceSolve(matrix);
//        stateCompressSolve1(matrix);
        stateCompressSolve2(matrix);
        for (int[] e : matrix) {
            System.out.println(Arrays.toString(e));
        }
    }

    /**
     * 额外空间法：散列表+两步遍历
     * 时间复杂度：O(mn)
     * 空间复杂度：O(m+n)
     */
    private static void extraSpaceSolve(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        Set<Integer> rows = new HashSet<>();
        Set<Integer> cols = new HashSet<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    rows.add(i);
                    cols.add(j);
                }
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (rows.contains(i) || cols.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * 压缩状态法：优化
     * 时间复杂度：O(mn)
     * 空间复杂度：O(1)
     */
    private static void stateCompressSolve2(int[][] matrix) {
        Boolean isCol = false;
        int row = matrix.length;
        int col = matrix[0].length;

        for (int i = 0; i < row; i++) {
            // 1.检查第一列中是否有0
            if (matrix[i][0] == 0) {
                isCol = true;
            }
            // 2. 检查第一行及(1,1)-->(row-1,col-1)是否有0
            for (int j = 1; j < col; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        // 3. 将(1,1)-->(row-1,col-1)中需要置零的位置置零
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 4. 将第一行需要置零的位置置零
        if (matrix[0][0] == 0) {
            for (int j = 0; j < col; j++) {
                matrix[0][j] = 0;
            }
        }

        // 5. 将第一列中需要置零的地方置零
        if (isCol) {
            for (int i = 0; i < row; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    /**
     * 压缩状态法：
     * 时间复杂度：O(mn*(m+n))
     * 空间复杂度：O(1)
     */
    private static void stateCompressSolve1(int[][] matrix) {
        int modified = Integer.MIN_VALUE;
        int row = matrix.length;
        int col = matrix[0].length;

        for (int j = 0; j < row; j++) {
            for (int i = 0; i < col; i++) {
                if (matrix[j][i] == 0) {
                    for (int k = 0; k < col; k++) {
                        if (matrix[j][k] != 0) {
                            matrix[j][k] = modified;
                        }
                    }
                    for (int k = 0; k < row; k++) {
                        if (matrix[k][i] != 0) {
                            matrix[k][i] = modified;
                        }
                    }
                }
            }
        }

        for (int j = 0; j < row; j++) {
            for (int i = 0; i < col; i++) {
                if (matrix[j][i] == modified) {
                    matrix[j][i] = 0;
                }
            }
        }
    }
}
