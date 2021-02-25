package com.zcprog.hw;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 组合
 * @Author zhaochao
 * @Date 2021/2/25 20:56
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0077_Combinations {
    public static void main(String[] args) {
        int n = 4;
        int k = 2;
        System.out.println(combine(4, 2));
    }

    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        if (n <= 0 || k <= 0 || k > n) return ans;
        int[] nums = new int[n];
        dfs(n, ans, list, 1, k);
        return ans;
    }

    public static void dfs(int n, List<List<Integer>> ans, List<Integer> list, int idx, int k) {
        if (list.size() == k) {
            ans.add(new ArrayList<Integer>(list));
            return;
        }
        for (int i = idx; i <= n; i++) {
            list.add(i);
            dfs(n, ans, list, i + 1, k);
            list.remove(list.size() - 1);
        }
    }
}
