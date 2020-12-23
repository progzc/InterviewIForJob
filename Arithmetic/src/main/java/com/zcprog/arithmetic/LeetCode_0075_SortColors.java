package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 颜色分类
 * @Author zhaochao
 * @Date 2020/12/23 11:49
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0075_SortColors {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/sort-colors/
     * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     * 注意:
     * 不能使用代码库中的排序函数来解决这道题。
     * <p>
     * 示例:
     * 输入: [2,0,2,1,1,0]
     * 输出: [0,0,1,1,2,2]
     * <p>
     * 进阶：
     * 一个直观的解决方案是使用计数排序的两趟扫描算法。
     * 首先，迭代计算出0、1 和 2 元素的个数，然后按照0、1、2的排序，重写当前数组。
     * 你能想出一个仅使用常数空间的一趟扫描算法吗？
     */
    public static void main(String[] args) {
//        int[] nums = {2, 0, 2, 1, 1, 0};
//        int[] nums = {0, 0};
        int[] nums = {1, 2, 0};
//        countSortSolve(nums);
        doublePointerSolve(nums);
        System.out.println(Arrays.toString(nums));

    }

    /**
     * 双指针：一遍扫描
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static void doublePointerSolve(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        int m = 0;
        int n = nums.length - 1;
        int i = 0;
        while (i <= n) {
            while (i <= n && nums[i] == 2) {
                swap(nums, i, n);
                n--;
            }
            if (nums[i] == 0) {
                swap(nums, i, m);
                m++;
            }
            i++;
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 计数排序法：两边扫描
     * 时间复杂度：O(2n)
     * 空间复杂度：O(1)
     */
    private static void countSortSolve(int[] nums) {
        int red = 0;
        int white = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                red++;
            } else if (nums[i] == 1) {
                white++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (i < red) {
                nums[i] = 0;
            } else if (i < red + white) {
                nums[i] = 1;
            } else {
                nums[i] = 2;
            }
        }
    }
}
