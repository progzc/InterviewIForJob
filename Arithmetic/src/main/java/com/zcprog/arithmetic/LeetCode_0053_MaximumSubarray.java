package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 最大连续子序和
 * @Author zhaochao
 * @Date 2020/12/13 10:04
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0053_MaximumSubarray {

    /**
     * LeetCode地址：https://leetcode-cn.com/problems/maximum-subarray/
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * 示例:
     * 输入: [-2,1,-3,4,-1,2,1,-5,4]
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * <p>
     * 进阶:
     * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
     */
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        System.out.println("------------动态规划-------------");
        int maxSum = maximumSubarray(nums);
        System.out.println(maxSum);
        int[] maxSumSubarray = maximumSubarray2(nums);
        System.out.println(Arrays.toString(maxSumSubarray));
        System.out.println("------------贪心算法-------------");
        maxSum = maxSubArrayGreed(nums);
        System.out.println(maxSum);

    }

    /**
     * 动态规划：返回最大连续子序列之和
     * dp[i] 表示到当前位置i的最大连续子序列和
     * 状态转移方程 dp[i] = max(dp[i-1] + nums[i], nums[i])
     * 初始化：dp[0] = nums[0]
     * 空间复杂度：O(n)
     * 时间复杂度：O(1)
     */
    public static int maximumSubarray(int[] nums) {
        int currMaxSum = nums[0];
        int maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currMaxSum = Math.max(currMaxSum + nums[i], nums[i]);
            maxSum = Math.max(currMaxSum, maxSum);
        }
        return maxSum;
    }

    /**
     * 动态规划：返回最大连续子序列
     */
    public static int[] maximumSubarray2(int[] nums) {
        int currMaxSum = nums[0];
        int maxSum = nums[0];
        int start = 0, end = 0;
        for (int i = 1; i < nums.length; i++) {
            if (currMaxSum + nums[i] > nums[i]) {
                currMaxSum = currMaxSum + nums[i];
            } else {
                currMaxSum = nums[i];
                start = i;
            }

            if (currMaxSum > maxSum) {
                maxSum = currMaxSum;
                end = i;
            }
        }
        return Arrays.copyOfRange(nums, start, end + 1);
    }

    /**
     * 贪心算法
     * 空间复杂度：O(n)
     * 时间复杂度：O(1)
     */
    public static int maxSubArrayGreed(int[] nums) {
        int result = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum = sum + nums[i];
            result = Math.max(result, sum);
            if (sum < 0) {
                sum = 0;
            }
        }
        return result;
    }
}
