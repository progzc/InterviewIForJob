package com.zcprog.arithmetic.sort;

import java.util.Arrays;

/**
 * @Description 归并排序
 * @Author zhaochao
 * @Date 2021/3/9 0:28
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Merge_SimpleSort {
    public static void main(String[] args) {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 归并排序
     * 时间复杂度：O(n*log(n))
     * 空间复杂度：O(n)
     * 稳定
     */
    private static void sort(int[] nums) {
        int[] temp = new int[nums.length];
        sort(nums, 0, nums.length - 1, temp);
    }

    private static void sort(int[] nums, int left, int right, int[] temp) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            sort(nums, left, mid, temp); // 左边归并排序，使得左子序列有序
            sort(nums, mid + 1, right, temp); // 右边归并排序，使得右子序列有序
            merge(nums, left, mid, right, temp);

        }
    }

    private static void merge(int[] nums, int left, int mid, int right, int[] temp) {
        int i = left; // 左序列指针
        int j = mid + 1; // 右序列指针
        int t = 0; // 临时数组指针
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[t++] = nums[i++];
            } else {
                temp[t++] = nums[j++];
            }
        }

        // 将左边剩余元素填充进temp中
        while (i <= mid) {
            temp[t++] = nums[i++];
        }
        // 将右序列剩余元素填充进temp中
        while (j <= right) {
            temp[t++] = nums[j++];
        }
        t = 0;
        // 将temp中的元素全部拷贝到原数组中
        while (left <= right) {
            nums[left++] = temp[t++];
        }
    }
}
