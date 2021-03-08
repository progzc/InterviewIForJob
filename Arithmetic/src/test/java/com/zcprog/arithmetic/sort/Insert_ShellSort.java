package com.zcprog.arithmetic.sort;

import java.util.Arrays;

/**
 * @Description 希尔排序
 * @Author zhaochao
 * @Date 2021/3/8 23:34
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Insert_ShellSort {
    public static void main(String[] args) {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 希尔排序
     * 时间复杂度：O(n^1.3)
     * 空间复杂度：O(1)
     * 不稳定
     */
    private static void sort(int[] nums) {
        int len = nums.length;
        for (int gap = len / 2; gap > 0; gap = gap / 2) {
            for (int i = gap; i < len; i++) {
                int j = i;
                int cur = nums[i];
                while (j - gap >= 0 && cur < nums[j - gap]) {
                    nums[j] = nums[j - gap];
                    j = j - gap;
                }
                nums[j] = cur;
            }
        }
    }
}
