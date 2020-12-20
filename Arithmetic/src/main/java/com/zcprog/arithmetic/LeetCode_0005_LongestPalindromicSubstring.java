package com.zcprog.arithmetic;

/**
 * @Description 最长回文字串
 * @Author zhaochao
 * @Date 2020/12/17 23:12
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0005_LongestPalindromicSubstring {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/longest-palindromic-substring/
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * <p>
     * 示例 1：
     * 输入: "babad" 输出: "bab" 注意: "aba" 也是一个有效答案。
     * <p>
     * 示例 2：
     * 输入: "cbbd" 输出: "bb"
     * <p>
     * 还剩一种比较复杂的方法：Manacher算法（时间和空间复杂度都是O(n)）
     */
    public static void main(String[] args) {
        String[] ss = {"babad", "cbbd"};
        for (String s : ss) {
            System.out.println(dpSolve(s));
            System.out.println(dpSolve2(s));
        }
    }

    /**
     * 动态规划：
     * 状态转移方程：P(i,j) = P(i+1,j-1) && (S(i)==S(j))
     * 边界条件：P(i,i)=true;P(i,i+1)=(S(i)==S(i+1))
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n^2)
     */
    private static String dpSolve(String s) {
        if (s == null || s.length() < 1) {
            return null;
        }
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        String ans = "";
        for (int m = 0; m < n; m++) {
            for (int i = 0; i + m < n; i++) {
                int j = i + m;
                if (m == 0) {
                    dp[i][j] = true;
                } else if (m == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
                }
                if (dp[i][j] && j > ans.length()) {
                    ans = s.substring(i, j + 1);
                }
            }
        }
        return ans;
    }

    /**
     * 动态规划：从中心扩展
     * 状态转移方程：P(i,j) = P(i+1,j-1) && (S(i)==S(j))
     * 边界条件：P(i,i)=true;P(i,i+1)=(S(i)==S(i+1))
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static String dpSolve2(String s) {
        if (s == null || s.length() < 1) {
            return null;
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return right - left - 1;
    }
}
