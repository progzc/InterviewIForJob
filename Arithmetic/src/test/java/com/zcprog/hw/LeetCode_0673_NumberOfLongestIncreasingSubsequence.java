package com.zcprog.hw;

import java.util.Arrays;

/**
 * @Description 最长递增子序列的个数
 * @Author zhaochao
 * @Date 2021/3/21 10:13
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0673_NumberOfLongestIncreasingSubsequence {
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 4, 7};
        System.out.println(findNumberOfLIS(nums));
    }

    private static int findNumberOfLIS(int[] nums) {
        int N = nums.length;
        if (N <= 1) return N;
        int[] lens = new int[N]; // 以nums[i]结尾的最长子序列的长度
        int[] counts = new int[N]; // 以nums[i]结尾的具有len[i]长度的序列的数目
        Arrays.fill(lens, 1);
        Arrays.fill(counts, 1);
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < j; i++) {
                if (nums[i] < nums[j]) {
                    if (lens[i] + 1 > lens[j]) {
                        lens[j] = lens[i] + 1;
                        counts[j] = counts[i];
                    } else if (lens[i] + 1 == lens[j]) {
                        counts[j] += counts[i];
                    }
                }
            }
        }

        int longest = 0;
        int ans = 0;
        for (int len : lens) {
            longest = Math.max(longest, len);
        }
        for (int i = 0; i < N; i++) {
            if (lens[i] == longest) {
                ans += counts[i];
            }
        }
        return ans;
    }
}
