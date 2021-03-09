package com.zcprog.arithmetic.sort;

import java.util.Arrays;

/**
 * @Description 快速排序
 * @Author zhaochao
 * @Date 2021/3/9 9:55
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Exchange_QuickSort {
    public static void main(String[] args) {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 快速排序法
     * 时间复杂度：O(n*log(n))
     * 空间复杂度：O(n*log(n))
     * 不稳定
     */
    public static void sort(int[] nums) {
        sort(nums, 0, nums.length - 1);
    }

    public static void sort(int[] nums, int left, int right) {
        if (left >= right) return;
        int start = left;
        int end = right;
        int flag = left;
        while (left < right) {
            while ((left < right) && (nums[right] >= nums[flag])) {
                right--;
            }
            if (nums[right] < nums[flag]) {
                int tmp = nums[right];
                nums[right] = nums[flag];
                nums[flag] = tmp;
                flag = right;
            }
            while ((left < right) && (nums[left] <= nums[flag])) {
                left++;
            }
            if (nums[left] > nums[flag]) {
                int tmp = nums[left];
                nums[left] = nums[flag];
                nums[flag] = tmp;
                flag = left;
            }
        }
        sort(nums, start, left - 1);
        sort(nums, left + 1, end);
    }
}
