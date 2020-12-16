package com.zcprog.arithmetic;

/**
 * @Description 两整数之和
 * @Author zhaochao
 * @Date 2020/12/16 22:09
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0371_SumOfTwoIntegers {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/sum-of-two-integers/
     * 不使用运算符 + 和 - ​​​​​​​，计算两整数 ​​​​​​​a 、b ​​​​​​​之和。
     * <p>
     * 示例 1:
     * 输入: a = 1, b = 2
     * 输出: 3
     * <p>
     * 示例 2:
     * 输入: a = -2, b = 3
     * 输出: 1
     */
    public static void main(String[] args) {
        int a1 = 1;
        int b1 = 2;
        int a2 = -2;
        int b2 = 3;
        System.out.println(bitSolve(a1, b1));
        System.out.println(bitSolve(a2, b2));
    }

    /**
     * 位运算
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     */
    private static int bitSolve(int a, int b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        while (b != 0) {
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }
}
