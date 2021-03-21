package com.zcprog.hw;

/**
 * @Description 最长连续递增序列
 * @Author zhaochao
 * @Date 2021/3/21 15:17
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0674_LongestContinuousIncreasingSubsequence {
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 4, 7};
//        int[] nums = {2, 2, 2, 2, 2};
        System.out.println(findLengthOfLCIS(nums));
    }

    private static int findLengthOfLCIS(int[] nums) {
        int len = nums.length;
        if (len <= 1) return len;
        int pre = 1;
        int cur;
        int ans = 1;
        for (int i = 1; i < len; i++) {
            cur = 1;
            if (nums[i - 1] < nums[i]) {
                cur = pre + 1;
                ans = Math.max(ans, cur);
            }
            pre = cur;
        }
        return ans;
    }
}
