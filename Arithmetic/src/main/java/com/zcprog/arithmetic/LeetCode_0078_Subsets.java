package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 子集
 * @Author zhaochao
 * @Date 2020/12/23 15:31
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0078_Subsets {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/subsets/
     * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     * 说明：解集不能包含重复的子集。
     * <p>
     * 示例:
     * 输入: nums = [1,2,3]
     * 输出:
     * [
     * [3],
     * [1],
     * [2],
     * [1,2,3],
     * [1,3],
     * [2,3],
     * [1,2],
     * []
     * ]
     */
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
//        System.out.println(backtrackSolve(nums));
        System.out.println(iterationSolve(nums));
    }

    /**
     * 回溯
     * 时间复杂度：O(n*2^n)
     * 空间复杂度：O(n)
     */
    private static List<List<Integer>> backtrackSolve(int[] nums) {
        List<Integer> t = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        dfs(nums, ans, t, 0);
        return ans;
    }

    private static void dfs(int[] nums, List<List<Integer>> ans, List<Integer> t, int cur) {
//        // 第一种回溯的写法
//        if (cur == nums.length) {
//            ans.add(new ArrayList<Integer>(t));
//            return;
//        }
//        t.add(nums[cur]);
//        dfs(nums, ans, t, cur + 1);
//        t.remove(t.size() - 1);
//        dfs(nums, ans, t, cur + 1);

        // 第二种回溯的写法
        ans.add(new ArrayList<Integer>(t));
        for (int i = cur; i < nums.length; i++) {
            t.add(nums[i]);
            dfs(nums, ans, t, i + 1);
            t.remove(t.size() - 1);
        }
    }

    /**
     * 迭代枚举
     * 时间复杂度：O(n*2^n)
     * 空间复杂度：O(n)
     */
    private static List<List<Integer>> iterationSolve(int[] nums) {
        List<Integer> t = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        for (int mask = 0; mask < (1 << n); mask++) {
            t.clear();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    t.add(nums[i]);
                }
            }
            ans.add(new ArrayList<Integer>(t));
        }
        return ans;
    }
}
