package com.zcprog.hw;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 括号生成
 * @Author zhaochao
 * @Date 2021/3/20 23:13
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0022_GenerateParentheses {
    public static void main(String[] args) {
        int n = 3;
        System.out.println(bracketGenerate(n));
    }

    private static List<String> bracketGenerate(int n) {
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        dfs(n, ans, sb, 0, 0);
        return ans;
    }

    private static void dfs(int n, List<String> ans, StringBuilder sb, int left, int right) {
        if (sb.length() == n * 2) {
            ans.add(sb.toString());
            return;
        }
        if (left < n) {
            sb.append('(');
            dfs(n, ans, sb, left + 1, right);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (right < left) {
            sb.append(')');
            dfs(n, ans, sb, left, right + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

}
