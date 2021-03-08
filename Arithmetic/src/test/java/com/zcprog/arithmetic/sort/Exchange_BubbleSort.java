package com.zcprog.arithmetic.sort;

import java.util.Arrays;

/**
 * @Description 冒泡排序
 * @Author zhaochao
 * @Date 2021/3/8 21:09
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Exchange_BubbleSort {
    public static void main(String[] args) {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 冒泡排序法
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    public static void sort(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }
}
