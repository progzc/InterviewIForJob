package com.zcprog.arithmetic.sort;

import java.util.Arrays;

/**
 * @Description 选择排序
 * @Author zhaochao
 * @Date 2021/3/8 22:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Select_SimpleSort {
    public static void main(String[] args) {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 选择排序
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     * 不稳定：例如：80(A)、80(B)、70(C)；选择排序后是CBA,不稳定
     */
    private static void sort(int[] nums) {
        int len = nums.length;
        int minIdx, temp;
        for (int i = 0; i < len - 1; i++) {
            minIdx = i;
            for (int j = i + 1; j < len; j++) {
                if (nums[j] < nums[minIdx]) {
                    minIdx = j;
                }
            }
            temp = nums[i];
            nums[i] = nums[minIdx];
            nums[minIdx] = temp;
        }
    }
}
