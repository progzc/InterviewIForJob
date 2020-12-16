package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 分发饼干
 * @Author zhaochao
 * @Date 2020/12/16 22:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0455_AssignCookies {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/assign-cookies/
     * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。
     * 但是，每个孩子最多只能给一块饼干。对每个孩子i，都有一个胃口值g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；
     * 并且每块饼干j，都有一个尺寸s[j]。如果s[j]>=g[i]，我们可以将这个饼干j分配给孩子i，这个孩子会得到满足。
     * 你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
     * <p>
     * 注意：
     * 你可以假设胃口值为正。一个小朋友最多只能拥有一块饼干。
     * <p>
     * 示例 1:
     * 输入: g=[1,2,3], s=[1,1]
     * 输出: 1
     * 解释:
     * 你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。
     * 虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。
     * 所以你应该输出1。
     * <p>
     * 示例 2:
     * 输入: g=[1,2], s=[1,2,3]
     * 输出: 2
     * 解释:
     * 你有两个孩子和三块小饼干，2个孩子的胃口值分别是1,2。
     * 你拥有的饼干数量和尺寸都足以让所有孩子满足。
     * 所以你应该输出2.
     */
    public static void main(String[] args) {
        int[] g1 = {1, 2, 3};
        int[] s1 = {1, 1};
        int[] g2 = {1, 2};
        int[] s2 = {1, 2, 3};
        System.out.println(iterationSolve(g1, s1));
        System.out.println(iterationSolve(g2, s2));
    }

    /**
     * 排序+贪心：
     * 时间复杂度：O(n*log(n))
     * 空间复杂度：O(1)
     */
    private static int iterationSolve(int[] g, int[] s) {
        if (g == null || s == null || g.length < 1 || s.length < 1) {
            return 0;
        }
        Arrays.sort(g);
        Arrays.sort(s);
        int count = 0;
        int i = 0;
        int j = 0;
        while (i < g.length) {
            while (j < s.length && g[i] > s[j]) {
                j++;
            }
            if (j < s.length) {
                count++;
                j++;
            }
            i++;
        }
        return count;
    }
}
