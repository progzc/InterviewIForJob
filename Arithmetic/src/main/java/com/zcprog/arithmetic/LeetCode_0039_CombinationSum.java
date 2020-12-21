package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 组合总和
 * @Author zhaochao
 * @Date 2020/12/21 21:51
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0039_CombinationSum {
    /**
     * LeetCode：https://leetcode-cn.com/problems/combination-sum/
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     * candidates 中的数字可以无限制重复被选取。
     * <p>
     * 说明：
     * 所有数字（包括 target）都是正整数。
     * 解集不能包含重复的组合。
     * 示例 1：
     * 输入：candidates = [2,3,6,7], target = 7,
     * 所求解集为：
     * [
     * [7],
     * [2,2,3]
     * ]
     * <p>
     * 示例 2：
     * 输入：candidates = [2,3,5], target = 8,
     * 所求解集为：
     * [
     * [2,2,2,2],
     * [2,3,3],
     * [3,5]
     * ]
     * <p>
     * 提示：
     * 1 <= candidates.length <= 30
     * 1 <= candidates[i] <= 200
     * candidate 中的每个元素都是独一无二的。
     * 1 <= target <= 500
     */
    public static void main(String[] args) {
        int[][] heaps = {{2, 3, 6, 7}, {2, 3, 5}};
        int[] targets = {7, 8};

        for (int i = 0; i < heaps.length; i++) {
            System.out.println(backtrackSolve(heaps[i], targets[i]));
        }
    }

    /**
     * 回溯法
     * 时间复杂度：O(n*2^n)
     * 空间复杂度：O(n)
     */
    private static List<List<Integer>> backtrackSolve(int[] candidates, int target) {
        if (candidates == null) {
            return null;
        }
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combine = new ArrayList<Integer>();
        if (candidates.length < 1) {
            return ans;
        }
        backtrack1(candidates, target, ans, combine, 0);
        return ans;
    }

    /**
     * 可重复的回溯
     */
    private static void backtrack1(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == candidates.length || target < 0) {
            return;
        }
        if (target == 0) {
            ans.add(new ArrayList<Integer>(combine));
            return;
        }
        // 直接跳过
        backtrack1(candidates, target, ans, combine, idx + 1);
        // 选择当前数
        combine.add(candidates[idx]);
        backtrack1(candidates, target - candidates[idx], ans, combine, idx);
        combine.remove(combine.size() - 1);
    }

    /**
     * 不可重复的回溯
     */
    private static void backtrack2(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == candidates.length || target < 0) {
            return;
        }
        if (target == 0) {
            ans.add(new ArrayList<Integer>(combine));
            return;
        }
        // 直接跳过
        backtrack1(candidates, target, ans, combine, idx + 1);
        // 选择当前数
        combine.add(candidates[idx]);
        // 可重复与不可重复的回溯只在这里有一点区别
        backtrack2(candidates, target - candidates[idx], ans, combine, idx + 1);
        combine.remove(combine.size() - 1);
    }
}
