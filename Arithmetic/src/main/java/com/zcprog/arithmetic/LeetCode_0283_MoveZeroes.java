package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 移动零
 * @Author zhaochao
 * @Date 2020/12/16 18:11
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0283_MoveZeroes {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/move-zeroes/
     * 给定一个数组nums，编写一个函数将所有0移动到数组的末尾，同时保持非零元素的相对顺序。
     * <p>
     * 示例:
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * <p>
     * 说明:
     * 必须在原数组上操作，不能拷贝额外的数组。
     * 尽量减少操作次数。
     */
    public static void main(String[] args) {
        int[] nums1 = {0, 1, 0, 3, 12};
        int[] nums2 = {0, 1, 0, 3, 12};
        System.out.println("------------遍历---------");
        System.out.println(Arrays.toString(traverseSolve(nums1)));
        System.out.println("------------双指针---------");
        System.out.println(Arrays.toString(doublePointerSolve(nums2)));

    }

    /**
     * 双指针：会有很多不必要的交换（但还是比遍历方法要快很多）
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int[] doublePointerSolve(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }
        int n = nums.length;
        int left = 0;
        int right = 0;
        while (right < n) {
            if (nums[right] != 0) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            }
            right++;
        }
        return nums;
    }

    /**
     * 遍历
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int[] traverseSolve(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }

        int n = 0;
        for (int i = 0; i < nums.length; i++) {
            while (i < nums.length && nums[i] != 0) {
                i++;
            }
            n = Math.max(i + 1, n);
            while (n < nums.length && nums[n] == 0) {
                n++;
            }
            if (n < nums.length) {
                int temp = nums[i];
                nums[i] = nums[n];
                nums[n] = temp;
                n++;
            }
        }
        return nums;
    }
}
