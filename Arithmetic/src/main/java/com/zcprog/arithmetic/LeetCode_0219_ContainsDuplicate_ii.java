package com.zcprog.arithmetic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description 存在重复元素II
 * @Author zhaochao
 * @Date 2020/12/16 13:06
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0219_ContainsDuplicate_ii {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/contains-duplicate-ii/
     * 给定一个整数数组和一个整数 k，判断数组中是否存在两个不同的索引 i 和 j，使得 nums [i] = nums [j]，并且 i 和 j 的差的 绝对值 至多为 k。
     * <p>
     * 示例 1:
     * 输入: nums = [1,2,3,1], k = 3
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: nums = [1,0,1,1], k = 1
     * 输出: true
     * <p>
     * 示例 3:
     * 输入: nums = [1,2,3,1,2,3], k = 2
     * 输出: false
     */
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 1};
        int[] nums2 = {1, 0, 1, 1};
        int[] nums3 = {1, 2, 3, 1, 2, 3};
        System.out.println("---------哈希表法------------");
        System.out.println(hashSolve(nums1, 3));
        System.out.println(hashSolve(nums2, 1));
        System.out.println(hashSolve(nums3, 2));
        System.out.println("---------哈希表法(优化)------------");
        System.out.println(hashSolve2(nums1, 3));
        System.out.println(hashSolve2(nums2, 1));
        System.out.println(hashSolve2(nums3, 2));
        System.out.println("---------暴力法------------");
        System.out.println(traverseSolve(nums1, 3));
        System.out.println(traverseSolve(nums2, 1));
        System.out.println(traverseSolve(nums3, 2));
    }

    /**
     * 哈希表法
     * 时间复杂度：O(n)
     * 空间复杂度：O(2n)
     */
    private static boolean hashSolve(int[] nums, int k) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashMap.containsKey(nums[i])) {
                if (i - hashMap.get(nums[i]) <= k) {
                    return true;
                } else {
                    hashMap.put(nums[i], i);
                }
            } else {
                hashMap.put(nums[i], i);
            }
        }
        return false;
    }

    /**
     * 散列表法：优化空间复杂度
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @return
     */
    private static boolean hashSolve2(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; ++i) {
            if (set.contains(nums[i])) {
                return true;
            }
            set.add(nums[i]);
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

    /**
     * 暴力法
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static boolean traverseSolve(int[] nums, int k) {
        for (int i = 0; i < nums.length; ++i) {
            for (int j = Math.max(i - k, 0); j < i; ++j) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
