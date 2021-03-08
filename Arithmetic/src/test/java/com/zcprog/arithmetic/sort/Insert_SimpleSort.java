package com.zcprog.arithmetic.sort;

import java.util.Arrays;

/**
 * @Description 插入排序
 * @Author zhaochao
 * @Date 2021/3/8 23:11
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Insert_SimpleSort {
    public static void main(String[] args) {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 插入排序
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     * 稳定
     */
    private static void sort(int[] nums) {
        int len = nums.length;
        int preIdx, cur;
        for (int i = 1; i < len; i++) {
            preIdx = i - 1;
            cur = nums[i];
            while (preIdx >= 0 && nums[preIdx] > cur) {
                nums[preIdx + 1] = nums[preIdx];
                preIdx--;
            }
            nums[preIdx + 1] = cur;
        }
    }
}
