package com.zcprog.hw;

/**
 * @Description 最长递增子序列
 * @Author zhaochao
 * @Date 2021/3/21 9:57
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0300_LongestIncreasingSubsequence {
    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println(findLengthOfLIS(nums));
    }

    private static int findLengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int ans = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
