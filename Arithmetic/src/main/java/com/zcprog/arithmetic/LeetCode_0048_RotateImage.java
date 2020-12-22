package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 旋转图像
 * @Author zhaochao
 * @Date 2020/12/22 10:32
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0048_RotateImage {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/rotate-image/
     * 给定一个 n × n 的二维矩阵表示一个图像。
     * 将图像顺时针旋转 90 度。
     * <p>
     * 说明：
     * 你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
     * <p>
     * 示例 1:
     * 给定 matrix =
     * [
     * [1,2,3],
     * [4,5,6],
     * [7,8,9]
     * ],
     * 原地旋转输入矩阵，使其变为:
     * [
     * [7,4,1],
     * [8,5,2],
     * [9,6,3]
     * ]
     * <p>
     * 示例 2:
     * 给定 matrix =
     * [
     * [ 5, 1, 9,11],
     * [ 2, 4, 8,10],
     * [13, 3, 6, 7],
     * [15,14,12,16]
     * ],
     * 原地旋转输入矩阵，使其变为:
     * [
     * [15,13, 2, 5],
     * [14, 3, 4, 1],
     * [12, 6, 8, 9],
     * [16, 7,10,11]
     * ]
     */
    public static void main(String[] args) {
//        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrix = {{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
//        assistArraySlove(matrix);
//        spinAroundSlove(matrix);
        overturnSlove(matrix);
        for (int[] e : matrix) {
            System.out.println(Arrays.toString(e));
        }

    }

    /**
     * 翻转：
     * 1.先水平翻转：matrix[row,col]-->matrix[n-row-1][col]
     * 2.再对角线翻转：matrix[n-row-1][col]-->matrix[col][n-row-1]
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static void overturnSlove(int[][] matrix) {
        int n = matrix.length;
        // 水平翻转
        for (int i = 0; i < n / 2; ++i) {
            for (int j = 0; j < n; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - i - 1][j];
                matrix[n - i - 1][j] = temp;
            }
        }
        // 主对角线翻转
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    /**
     * 原地旋转：
     * 根据旋转的算法：
     * matrix[row,col]-->matrix[col][n-row-1]-->matrix[n-row-1][n-col-1]-->matrix[n-col-1][row]-->matrix[row,col]
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static void spinAroundSlove(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n / 2; ++i) {
            for (int j = 0; j < (n + 1) / 2; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }

    /**
     * 使用辅助数组：matrix[row][col] = matrix_new[col][n-row-1]
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n^2)
     */
    private static void assistArraySlove(int[][] matrix) {
        int n = matrix.length;
        int[][] matrix_new = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix_new[j][n - i - 1] = matrix[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = matrix_new[i][j];
            }
        }
    }


}
