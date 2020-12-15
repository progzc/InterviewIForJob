package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 验证回文字符串
 * @Author zhaochao
 * @Date 2020/12/15 11:23
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0125_ValidPalindrome {

    /**
     * LeetCode地址：https://leetcode-cn.com/problems/valid-palindrome/description/
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     * 说明：本题中，我们将空字符串定义为有效的回文串。
     * <p>
     * 示例 1:
     * 输入: "A man, a plan, a canal: Panama"
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: "race a car"
     * 输出: false
     */
    public static void main(String[] args) {
        String[] strs = {"A man, a plan, a canal: Panama", "race a car", "0P"};
        System.out.println("----------api法----------");
        Arrays.stream(strs).forEach(str -> {
            System.out.println(str);
            System.out.println(apiSolve(str));
        });

        System.out.println("----------双指针----------");
        Arrays.stream(strs).forEach(str -> {
            System.out.println(str);
            System.out.println(doublePointerSolve(str));
        });

        System.out.println("----------双指针优化----------");
        Arrays.stream(strs).forEach(str -> {
            System.out.println(str);
            System.out.println(doublePointerSolve2(str));
        });

    }

    /**
     * 双指针法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static boolean doublePointerSolve2(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }

        return true;
    }

    /**
     * 双指针法
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static boolean doublePointerSolve(String s) {
        StringBuffer sb = new StringBuffer();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sb.append(Character.toLowerCase(ch));
            }
        }

        int n = sb.length();
        int left = 0;
        int right = n - 1;
        while (right >= left) {
            if (sb.charAt(left) != sb.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * api法
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static boolean apiSolve(String s) {
        StringBuffer sb = new StringBuffer();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sb.append(Character.toLowerCase(ch));
            }
        }
        StringBuffer sbReverse = new StringBuffer(sb).reverse();
        return sb.toString().equals(sbReverse.toString());
    }

}
