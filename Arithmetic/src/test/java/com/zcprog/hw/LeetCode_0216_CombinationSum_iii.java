package com.zcprog.hw;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 组合总和三
 * @Author zhaochao
 * @Date 2021/2/25 23:44
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0216_CombinationSum_iii {
    public static void main(String[] args) {
        int k = 9;
        int n = 45;
        System.out.println(combinationSum3(k, n));
    }

    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        if (k > 9 || n < 6) return ans;
        List<Integer> list = new ArrayList<>();
        dfs(n, k, 1, ans, list);
        return ans;
    }

    public static void dfs(int n, int k, int m, List<List<Integer>> ans, List<Integer> list) {
        if (list.size() == k && n == 0) {
            ans.add(new ArrayList<>(list));
            return;
        }
        if (n < 0) return;
        for (int i = m; i <= 9; i++) {
            list.add(i);
            dfs(n - i, k, i + 1, ans, list);
            list.remove(list.size() - 1);
        }
    }
}
