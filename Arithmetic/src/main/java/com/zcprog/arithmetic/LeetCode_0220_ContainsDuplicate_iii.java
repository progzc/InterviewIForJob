package com.zcprog.arithmetic;

import java.util.TreeSet;

/**
 * @Description 存在重复元素iii
 * @Author zhaochao
 * @Date 2021/3/13 21:48
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0220_ContainsDuplicate_iii {

    public static void main(String[] args) {
//        int[] nums = {1, 2, 3, 1};
//        int k = 3;
//        int t = 0;
//        int[] nums = {1, 0, 1, 1};
//        int k = 1;
//        int t = 2;
//        int[] nums = {1, 5, 9, 1, 5, 9};
//        int k = 2;
//        int t = 3;
        int[] nums = {2147483646, 2147483647};
        int k = 3;
        int t = 3;
        System.out.println(isExist(nums, k, t));

    }

    // 线性搜索：超时
    public static boolean isExist(int[] nums, int k, int t) {
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = Math.max(i - k, 0); j < i; j++) {
                // 避免int越界
                if (Math.abs((long) nums[i] - nums[j]) <= t) {
                    return true;
                }
            }
        }
        return false;
    }

    // 二叉搜索树：TreeSet
    public static boolean isExist2(int[] nums, int k, int t) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            // 查找大于或等于给定元素的元素
            Integer s = set.ceiling(nums[i]);
            if (s != null && (long) s - nums[i] <= t) {
                return true;
            }
            // 查找小于或等于给定元素的元素
            Integer g = set.floor(nums[i]);
            if (g != null && (long) nums[i] - g <= t) {
                return true;
            }

            // 添加
            set.add(nums[i]);
            if (set.size() > k) {
                // 删除
                set.remove(nums[i - k]);
            }
        }
        return false;
    }
}
