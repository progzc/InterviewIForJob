package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 加一
 * @Author zhaochao
 * @Date 2020/12/13 9:42
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0066_PlusOne {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/plus-one
     * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * <p>
     * 示例 1:
     * 输入: [1,2,3]
     * 输出: [1,2,4]
     * 解释: 输入数组表示数字 123。
     * <p>
     * 示例 2:
     * 输入: [4,3,2,1]
     * 输出: [4,3,2,2]
     * 解释: 输入数组表示数字 4321。
     */
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3};
        int[] nums2 = {1, 8, 9, 9};
        int[] nums3 = {9, 9, 9, 9};
        int[] result1 = plusOne(nums1);
        int[] result2 = plusOne(nums2);
        int[] result3 = plusOne(nums3);
        System.out.println(Arrays.toString(result1));
        System.out.println(Arrays.toString(result2));
        System.out.println(Arrays.toString(result3));
    }

    /**
     * 数组的反向遍历
     * 时间复杂度： O(n)
     * 空间复杂度：O(1)
     */
    public static int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i]++;
            digits[i] = digits[i] % 10;
            if (digits[i] != 0) {
                return digits;
            }
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }
}
