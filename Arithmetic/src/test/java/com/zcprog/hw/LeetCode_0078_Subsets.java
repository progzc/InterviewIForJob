package com.zcprog.hw;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 子集
 * @Author zhaochao
 * @Date 2021/3/14 15:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0078_Subsets {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        System.out.println(subsets2(nums));
    }

    /**
     * 迭代
     * 时间复杂度：O(n*2^n)
     * 空间复杂度：O(n)
     */
    public static List<List<Integer>> subsets2(int[] nums) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        for (int mask = 0; mask < (1 << n); mask++) {
            list.clear();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    list.add(nums[i]);
                }
            }
            ans.add(new ArrayList<Integer>(list));
        }
        return ans;
    }

    /**
     * 递归
     * 时间复杂度：O(n*2^n)
     * 空间复杂度：O(n)
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        dfs(nums, list, ans, 0);
        return ans;
    }

    private static void dfs(int[] nums, List<Integer> list, List<List<Integer>> ans, int cur) {
        if (cur == nums.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        list.add(nums[cur]);
        dfs(nums, list, ans, cur + 1);
        list.remove(list.size() - 1);
        dfs(nums, list, ans, cur + 1);
    }
}
