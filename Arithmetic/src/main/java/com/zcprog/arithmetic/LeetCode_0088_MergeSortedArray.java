package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 合并两个有序数组
 * @Author zhaochao
 * @Date 2020/12/13 0:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

public class LeetCode_0088_MergeSortedArray {
    /**
     * LeetCode地址: https://leetcode-cn.com/problems/merge-sorted-array/
     * 题目描述：给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
     * <p>
     * 说明:
     * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
     * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
     * <p>
     * 示例:
     * 输入:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     * <p>
     * 输出: [1,2,2,3,5,6]
     */

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};

        merge(nums1, 3, nums2, 3);
        System.out.println(Arrays.toString(nums1));
    }

    /**
     * 三指针法
     * 时间复杂度：O(m+n)
     * 空间复杂度：O(1)
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        // 合并
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        // 合并剩余的nums2
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }


}
