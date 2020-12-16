package com.zcprog.arithmetic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 两个数组的交集
 * @Author zhaochao
 * @Date 2020/12/16 21:25
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0349_IntersectionOfTwoArrays {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/intersection-of-two-arrays/
     * 给定两个数组，编写一个函数来计算它们的交集。
     * <p>
     * 示例 1：
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2]
     * <p>
     * 示例 2：
     * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出：[9,4]
     * <p>
     * 说明：
     * 输出结果中的每个元素一定是唯一的。
     * 我们可以不考虑输出结果的顺序。
     */
    public static void main(String[] args) {
        int[][] nums = {{1, 2, 2, 1}, {2, 2}};
        System.out.println(Arrays.toString(hashSolve(nums[0], nums[1])));
    }

    /**
     * 哈希表
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int[] hashSolve(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return null;
        }
        if (nums1.length < 1 || nums2.length < 1) {
            return nums1.length < 1 ? nums1 : nums2;
        }
        Set<Integer> hashSet = new HashSet<>();

        for (int i = 0; i < nums1.length; i++) {
            if (!hashSet.contains(nums1[i])) {
                hashSet.add(nums1[i]);
            }
        }

        int[] result = new int[Math.min(nums1.length, nums2.length)];
        int count = 0;
        for (int i = 0; i < nums2.length; i++) {
            if (hashSet.contains(nums2[i])) {
                result[count++] = nums2[i];
                hashSet.remove(nums2[i]);
            }
        }
        return Arrays.copyOfRange(result, 0, count);
    }
}
