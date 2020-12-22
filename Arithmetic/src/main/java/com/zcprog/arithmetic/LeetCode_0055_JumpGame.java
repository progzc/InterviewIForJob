package com.zcprog.arithmetic;

/**
 * @Description 跳跃游戏
 * @Author zhaochao
 * @Date 2020/12/22 15:23
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0055_JumpGame {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/jump-game/
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 判断你是否能够到达最后一个位置。
     * <p>
     * 示例 1:
     * 输入: [2,3,1,1,4]
     * 输出: true
     * 解释: 我们可以先跳 1 步，从位置 0 到达 位置 1, 然后再从位置 1 跳 3 步到达最后一个位置。
     * <p>
     * 示例 2:
     * 输入: [3,2,1,0,4]
     * 输出: false
     * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是0，所以你永远不可能到达最后一个位置。
     */
    public static void main(String[] args) {
        int[][] numss = {{2, 3, 1, 1, 4}, {3, 2, 1, 0, 4}};
        for (int[] nums : numss) {
            System.out.println(greedSolve(nums));
        }

    }

    /**
     * 贪心算法：官方解答（更加优雅）
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static boolean greedSolve2(int[] nums) {
        int n = nums.length;
        int rightmost = 0;
        for (int i = 0; i < n; ++i) {
            if (i <= rightmost) {
                rightmost = Math.max(rightmost, i + nums[i]);
                if (rightmost >= n - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 贪心算法：
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static boolean greedSolve(int[] nums) {
        if (nums == null || nums.length < 1) {
            return false;
        }
        if (nums.length == 1 && nums[0] == 0) {
            return true;
        }
        int start = 0;
        while (nums[start] != 0) {
            if (start + nums[start] >= nums.length - 1) {
                return true;
            }
            int next;
            for (next = start + 1; next <= start + nums[start]; next++) {
                if (nums[next] >= nums[start] || start + nums[start] <= next + nums[next]) {
                    break;
                }
            }
            start = next;
        }
        return false;
    }
}
