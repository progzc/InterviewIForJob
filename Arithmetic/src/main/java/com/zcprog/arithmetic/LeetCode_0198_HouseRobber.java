package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 打家劫舍
 * @Author zhaochao
 * @Date 2020/12/15 23:11
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0198_HouseRobber {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/house-robber/
     * 你是一个专业的小偷，计划偷窃沿街的房屋。
     * 每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     * <p>
     * 示例 1：
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     * <p>
     * 示例 2：
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     * <p>
     * 提示：
     * <p>
     * 0 <= nums.length <= 100
     * 0 <= nums[i] <= 400
     */
    public static void main(String[] args) {
        int[][] heaps = {{1, 2, 3, 1}, {2, 7, 9, 3, 1}, {2, 1, 1, 2}};
        Arrays.stream(heaps).forEach(heap -> {
            System.out.println(dpSolve(heap));
        });
        Arrays.stream(heaps).forEach(heap -> {
            System.out.println(dpSolve2(heap));
        });

    }

    /**
     * 动态规划
     * 状态转移方程：dp[i]=max(dp[i-2]+nums[i],dp[i-1])
     * 边界条件：
     * dp[0] = nums[0]
     * dp[1]=max(nums[0],nums[1])
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int dpSolve(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        int[] dp = new int[length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[length - 1];
    }

    /**
     * 动态规划优化
     * 状态转移方程：dp[i]=max(dp[i-2]+nums[i],dp[i-1])
     * 边界条件：
     * dp[0] = nums[0]
     * dp[1]=max(nums[0],nums[1])
     * <p>
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int dpSolve2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        int first = nums[0];
        int second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }
        return second;
    }
}
