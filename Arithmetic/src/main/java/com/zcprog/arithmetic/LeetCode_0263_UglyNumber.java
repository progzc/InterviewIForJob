package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 丑数
 * @Author zhaochao
 * @Date 2020/12/16 17:49
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0263_UglyNumber {
    /**
     * LeetCode: https://leetcode-cn.com/problems/ugly-number/
     * 编写一个程序判断给定的数是否为丑数。
     * <p>
     * 丑数就是只包含质因数 2, 3, 5 的正整数。
     * 示例 1:
     * 输入: 6
     * 输出: true
     * 解释: 6 = 2 × 3
     * <p>
     * 示例 2:
     * 输入: 8
     * 输出: true
     * 解释: 8 = 2 × 2 × 2
     * <p>
     * 示例 3:
     * 输入: 14
     * 输出: false
     * 解释: 14 不是丑数，因为它包含了另外一个质因数 7。
     * <p>
     * 说明：
     * 1 是丑数。
     * 输入不会超过 32 位有符号整数的范围: [−2^31,  2^31 − 1]。
     */
    public static void main(String[] args) {
        int[] nums = {6, 8, 14};
        Arrays.stream(nums).forEach(num -> {
            System.out.println(iterationSolve(num));
        });


    }

    /**
     * 迭代
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static boolean iterationSolve(int num) {
        if (num == 0) {
            return false;
        }
        while (num % 2 == 0) {
            num = num / 2;
        }

        while (num % 3 == 0) {
            num = num / 3;
        }

        while (num % 5 == 0) {
            num = num / 5;
        }

        return num == 1;
    }
}
