package com.zcprog.arithmetic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 无重复字符的最长字串
 * @Author zhaochao
 * @Date 2020/12/17 20:54
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0003_LongestSubstringWithoutRepeatingCharacters {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * <p>
     * 示例 1:
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * <p>
     * 示例 2:
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * <p>
     * 示例 3:
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     */
    public static void main(String[] args) {
        String[] ss = {"abcabcbb", "bbbbb", "pwwkew"};
        Arrays.stream(ss).forEach(s -> {
            System.out.println(hashDpSolve(s));
            System.out.println(hashSolve(s));
        });
    }

    /**
     * 散列表：优化
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int hashSolve(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        // 右指针
        int rk = 0;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk < n && !occ.contains(s.charAt(rk))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i);
        }
        return ans;
    }

    /**
     * 哈希表+动态规划
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(n)
     */
    private static int hashDpSolve(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }
        if (s.length() < 2) {
            return 1;
        }
        int result = 1;
        int length = s.length();
        Set<Character> hashSet = new HashSet<>();
        for (int i = 1; i < length; i++) {
            int j = i;
            int count = 0;
            while (j >= 0 && !hashSet.contains(s.charAt(j))) {
                count++;
                hashSet.add(s.charAt(j));
                j--;
            }
            result = Math.max(result, count);
            hashSet.clear();
        }

        return result;
    }
}
