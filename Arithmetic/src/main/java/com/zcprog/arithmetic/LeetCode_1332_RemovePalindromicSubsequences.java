package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 删除回文子序列
 * @Author zhaochao
 * @Date 2020/12/17 9:35
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_1332_RemovePalindromicSubsequences {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/remove-palindromic-subsequences/
     * 给你一个字符串 s，它仅由字母 'a' 和 'b' 组成。每一次删除操作都可以从s中删除一个回文子序列。
     * 返回删除给定字符串中所有字符（字符串为空）的最小删除次数。
     * 「子序列」定义：如果一个字符串可以通过删除原字符串某些字符而不改变原字符顺序得到，那么这个字符串就是原字符串的一个子序列。
     * 「回文」定义：如果一个字符串向后和向前读是一致的，那么这个字符串就是一个回文。
     * <p>
     * 示例 1：
     * 输入：s = "ababa"
     * 输出：1
     * 解释：字符串本身就是回文序列，只需要删除一次。
     * <p>
     * 示例 2：
     * 输入：s = "abb"
     * 输出：2
     * 解释："abb" -> "bb" -> "".
     * 先删除回文子序列 "a"，然后再删除 "bb"。
     * <p>
     * 示例 3：
     * 输入：s = "baabb"
     * 输出：2
     * 解释："baabb" -> "b" -> "".
     * 先删除回文子序列 "baab"，然后再删除 "b"。
     * <p>
     * 示例 4：
     * 输入：s = ""
     * 输出：0
     * <p>
     * 提示：
     * 0 <= s.length <= 1000
     * s 仅包含字母 'a'  和 'b'
     * 在真实的面试中遇到过这道题？
     */
    public static void main(String[] args) {
        String[] ss = {"ababa", "abb", "baabb", ""};
        Arrays.stream(ss).forEach(s -> {
            System.out.println(removePalindromeSub(s));
        });

    }

    /**
     * 论审题的重要性：
     * 若将回文子序列改成回文子字符串则难度直接提升一个等级
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     */
    private static int removePalindromeSub(String s) {
        int length = s.length();
        if (s == null || length < 1) {
            return 0;
        }
        int left = 0;
        int right = length - 1;
        while (right > left) {
            if (s.charAt(right) != s.charAt(left)) {
                return 2;
            }
            left++;
            right--;
        }
        return 1;
    }
}
