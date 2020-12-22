package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 全排列 II
 * @Author zhaochao
 * @Date 2020/12/22 0:58
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0047_Permutations_ii {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/permutations-ii/
     * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
     * <p>
     * 示例:
     * 输入: [1,1,2]
     * 输出:
     * [
     * [1,2,3],
     * [1,3,2],
     * [2,1,3],
     * [2,3,1],
     * [3,1,2],
     * [3,2,1]
     * ]
     */
    public static void main(String[] args) {
        int[] nums = {1, 1, 2};
        System.out.println(backtrackSolve(nums));
    }

    /**
     * 回溯法
     * 时间复杂度：O(n*n!)
     * 空间复杂度：O(n)
     */
    private static List<List<Integer>> backtrackSolve(int[] nums) {
        if (nums == null) {
            return null;
        }
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        if (nums.length < 1) {
            return ans;
        }
        boolean[] vis = new boolean[nums.length];
        Arrays.sort(nums);
        dfs(nums, ans, combine, 0, vis);
        return ans;
    }

    private static void dfs(int[] nums, List<List<Integer>> ans, List<Integer> combine, int idx, boolean[] vis) {
        if (idx == nums.length) {
            ans.add((new ArrayList<Integer>(combine)));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // 注意这里的剪枝
            if (vis[i] || (i > 0 && nums[i] == nums[i - 1] && !vis[i - 1])) {
                continue;
            }
            combine.add(nums[i]);
            vis[i] = true;
            dfs(nums, ans, combine, idx + 1, vis);
            vis[i] = false;
            combine.remove(idx);
        }
    }
}
