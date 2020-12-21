package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 括号生成
 * @Author zhaochao
 * @Date 2020/12/21 15:41
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0022_GenerateParentheses {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/generate-parentheses
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且有效的括号组合。
     * 示例：
     * 输入：n = 3
     * 输出：[
     * ((())),
     * (()()),
     * (())(),
     * ()(()),
     * ()()()
     * ]
     */
    public static void main(String[] args) {
        int n = 3;
        System.out.println(dfsSolve(3));
    }

    /**
     * 回溯法
     * 时间复杂度：O(4^n/n^(1/2))
     * 空间复杂度：O(n)
     */
    private static List<String> dfsSolve(int n) {
        List<String> ans = new ArrayList<>();
        dfs(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    private static void dfs(List<String> ans, StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur.toString());
            return;
        }
        if (open < max) {
            cur.append('(');
            dfs(ans, cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
        }
        if (close < open) {
            cur.append(')');
            dfs(ans, cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }
}
