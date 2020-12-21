package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 下一个排列
 * @Author zhaochao
 * @Date 2020/12/21 17:38
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0031_NextPermutation {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/next-permutation/
     * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
     * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
     * 必须原地修改，只允许使用额外常数空间。
     * <p>
     * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     */
    public static void main(String[] args) {
        int[][] heaps = {{1, 2, 3}, {3, 2, 1}, {1, 1, 5}, {1, 3, 2}, {4, 2, 0, 2, 3, 2, 0}};
        for (int[] heap : heaps) {
            solve(heap);
            System.out.println(Arrays.toString(heap));
        }

    }

    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static void solve(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        // 从后往前找到第一个不是升序的数，记为nums[i]
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        // 从后往前找到第一个大于nums[i]的数，并交换
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        // 将nums[i]后（不包含nums[i]）的数按照降序排列
        int left = i + 1;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
