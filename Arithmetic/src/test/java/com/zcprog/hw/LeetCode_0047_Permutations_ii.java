package com.zcprog.hw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 全排列II
 * @Author zhaochao
 * @Date 2021/2/25 18:52
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0047_Permutations_ii {
    static boolean[] vis;

    public static void main(String[] args) {
//        int[] nums = {1, 1, 2};
        int[] nums = {1, 2, 3};
        System.out.println(permuteUnique(nums));
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> perm = new ArrayList<Integer>();
        vis = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums, ans, 0, perm);
        return ans;
    }

    public static void backtrack(int[] nums, List<List<Integer>> ans, int idx, List<Integer> perm) {
        if (idx == nums.length) {
            ans.add(new ArrayList<Integer>(perm));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (vis[i] || (i > 0 && nums[i] == nums[i - 1] && !vis[i - 1])) {
                continue;
            }
            perm.add(nums[i]);
            vis[i] = true;
            backtrack(nums, ans, idx + 1, perm);
            vis[i] = false;
            perm.remove(idx);
        }
    }
}
