package com.zcprog.arithmetic;

/**
 * @Description 阶乘后的零
 * @Author zhaochao
 * @Date 2020/12/15 18:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0172_FactorialTrailingZeroes {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/factorial-trailing-zeroes/
     * 给定一个整数 n，返回 n! 结果尾数中零的数量。
     * <p>
     * 示例 1:
     * 输入: 3
     * 输出: 0
     * 解释: 3! = 6, 尾数中没有零。
     * <p>
     * 示例 2:
     * 输入: 5
     * 输出: 1
     * 解释: 5! = 120, 尾数中有 1 个零.
     * 说明: 你算法的时间复杂度应为 O(log n) 。
     */
    public static void main(String[] args) {
        int num = 120;
        System.out.println(iterationSolve(num));
    }

    /**
     * 迭代
     * 时间复杂度：O(log(n))
     * 空间复杂度：O(1)
     */
    private static int iterationSolve(int num) {
        int res = 0;
        while (num > 5) {
            num = num / 5;
            res += num;
        }
        return res;
    }
}
