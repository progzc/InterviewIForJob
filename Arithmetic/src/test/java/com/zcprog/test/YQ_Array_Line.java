package com.zcprog.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 易趣机试题
 * @Author zhaochao
 * @Date 2021/3/20 20:18
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class YQ_Array_Line {

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(lineNums(nums));
    }

    private static List<List<Integer>> lineNums(int[][] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        int m = nums.length;
        int n = nums[0].length;
        dfs(nums, m, n, 0, 0, ans, list);
        return ans;
    }

    private static void dfs(int[][] nums, int m, int n, int i, int j, List<List<Integer>> ans, List<Integer> list) {
        if (j == n) {
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int k = i; k < m; k++) {
            list.add(nums[k][j]);
            dfs(nums, m, n, i, j + 1, ans, list);
            list.remove(list.size() - 1);
        }
    }
}
