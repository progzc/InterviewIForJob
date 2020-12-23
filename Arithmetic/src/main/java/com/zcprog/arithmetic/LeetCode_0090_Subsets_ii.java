package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 子集 II
 * @Author zhaochao
 * @Date 2020/12/23 22:36
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0090_Subsets_ii {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/subsets-ii/
     * 给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     * 说明：解集不能包含重复的子集。
     * <p>
     * 示例:
     * 输入: [1,2,2]
     * 输出:
     * [
     * [2],
     * [1],
     * [1,2,2],
     * [2,2],
     * [1,2],
     * []
     * ]
     */
    public static void main(String[] args) {
        int[] nums = {1, 2, 2};
        System.out.println(backtrackSolve(nums));
    }

    /**
     * 回溯
     * 时间复杂度：O(n*2^n)
     * 空间复杂度：O(n)
     */
    private static List<List<Integer>> backtrackSolve(int[] nums) {
        // 一定要先排序
        Arrays.sort(nums);
        List<Integer> t = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        dfs(nums, ans, t, 0);
        return ans;
    }

    private static void dfs(int[] nums, List<List<Integer>> ans, List<Integer> t, int cur) {
        // 第二种回溯的写法
        ans.add(new ArrayList<Integer>(t));
        for (int i = cur; i < nums.length; i++) {
            if (i > cur && nums[i] == nums[i - 1]) {
                continue;
            }
            t.add(nums[i]);
            dfs(nums, ans, t, i + 1);
            t.remove(t.size() - 1);
        }
    }

}
