package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 字符的最短距离
 * @Author zhaochao
 * @Date 2020/12/17 0:24
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0821_ShortestDistanceToACharacter {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/shortest-distance-to-a-character
     * 给定一个字符串 S 和一个字符 C。返回一个代表字符串 S 中每个字符到字符串 S 中的字符 C 的最短距离的数组。
     * <p>
     * 示例 1：
     * 输入: S = "loveleetcode", C = 'e'
     * 输出: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
     * <p>
     * 说明:
     * - 字符串 S 的长度范围为 [1, 10000]。
     * - C 是一个单字符，且保证是字符串 S 里的字符。
     * - S 和 C 中的所有字母均为小写字母。
     */
    public static void main(String[] args) {
        String S = "loveleetcode";
        char C = 'e';
        System.out.println(Arrays.toString(prosConsSolve(S, C)));
    }

    /**
     * 正遍历+反遍历
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int[] prosConsSolve(String S, char C) {
        int length = S.length();
        if (S == null || length < 1) {
            return null;
        }
        int N = S.length();
        int[] ans = new int[N];
        int prev = Integer.MIN_VALUE / 2;

        for (int i = 0; i < N; ++i) {
            if (S.charAt(i) == C) {
                prev = i;
            }
            ans[i] = i - prev;
        }

        prev = Integer.MAX_VALUE / 2;
        for (int i = N - 1; i >= 0; --i) {
            if (S.charAt(i) == C) {
                prev = i;
            }
            ans[i] = Math.min(ans[i], prev - i);
        }

        return ans;
    }
}
