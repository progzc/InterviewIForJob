package com.zcprog.arithmetic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 两数之和
 * @Author zhaochao
 * @Date 2020/12/13 16:24
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0001_TwoSum {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/two-sum
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * <p>
     * 示例:
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     */
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int[] result = hashSolve(nums, 9);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 哈希表
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int[] hashSolve(int[] nums, int target) {
        Map<Integer, Integer> hashtable = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }

}
