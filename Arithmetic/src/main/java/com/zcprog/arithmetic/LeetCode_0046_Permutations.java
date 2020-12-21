package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description 全排列
 * @Author zhaochao
 * @Date 2020/12/21 23:50
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0046_Permutations {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/permutations/
     * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     * <p>
     * 示例:
     * 输入: [1,2,3]
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
        int[] nums = {1, 2, 3};
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
        // 拷贝
        for (int num : nums) {
            combine.add(num);
        }
        int n = nums.length;
        dfs(n, ans, combine, 0);
        return ans;
    }

    private static void dfs(int n, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == n) {
            ans.add((new ArrayList<Integer>(combine)));
            return;
        }
        for (int i = idx; i < n; i++) {
            Collections.swap(combine, idx, i);
            dfs(n, ans, combine, idx + 1);
            Collections.swap(combine, idx, i);
        }
    }
}
