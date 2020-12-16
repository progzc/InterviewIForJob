package com.zcprog.arithmetic;

/**
 * @Description 4的幂
 * @Author zhaochao
 * @Date 2020/12/16 20:20
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0342_PowerOfFour {
    /**
     * LeetCod地址：https://leetcode-cn.com/problems/power-of-four/
     * 给定一个整数 (32 位有符号整数)，请编写一个函数来判断它是否是 4 的幂次方。
     * <p>
     * 示例 1:
     * 输入: 16
     * 输出: true
     * <p>
     * 示例 2:
     * 输入: 5
     * 输出: false
     * <p>
     * 进阶：
     * 你能不使用循环或者递归来完成本题吗？
     */
    public static void main(String[] args) {
        int[] nums = {16, 5, -64};
        for (int num : nums) {
            // 掌握数学运算+位运算
            // 都建立在先确定是2的幂的情况下：x>0 and x & (x - 1) == 0
            System.out.println(bitMath(num));
            System.out.println(bitSolve(num));
            System.out.println(mathSolve(num));
            System.out.println(iterationSolve(num));
            System.out.println(recursionSolve(num));
        }

    }

    /**
     * 位运算+数学运算
     * <p>
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     */
    private static boolean bitMath(int n) {
        return (n > 0) && ((n & (n - 1)) == 0) && (n % 3 == 1);
    }

    /**
     * 位运算：首先检查num是否为2的幂：x>0 and x & (x - 1) == 0。
     * 1.判断是否是2的幂：(n > 0) && ((n & (n - 1)) == 0)
     * 2.判断是否是4的幂：(n & 0xaaaaaaaa) == 0
     * 第一种情况下（4的幂）,1 处于偶数位置：第0位、第2位、第4位等；在第二种情况下，1处于奇数位置。
     * <p>
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     */
    private static boolean bitSolve(int n) {
        return (n > 0) && ((n & (n - 1)) == 0) && ((n & 0xaaaaaaaa) == 0);
    }

    /**
     * 数学运算：数字为4的幂可以等价于检查log2(x)是否为偶数
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     */
    private static boolean mathSolve(int n) {
        // 对数的换底公式
        return (n > 0) && (Math.log(n) / Math.log(2) % 2 == 0);
    }

    /**
     * 递归
     * 时间复杂度：O(log(n))
     * 空间复杂度：O(log(n))
     */
    private static boolean recursionSolve(int n) {
        if (n <= 0) {
            return false;
        }
        if (n == 1) {
            return true;
        }
        if (n % 4 == 0) {
            n = n / 4;
        } else {
            return false;
        }

        return recursionSolve(n);
    }

    /**
     * 迭代
     * 时间复杂度：O(log(n))
     * 空间复杂度：O(1)
     */
    private static boolean iterationSolve(int n) {
        if (n <= 0) {
            return false;
        }
        while (n % 4 == 0) {
            n = n / 4;
        }
        return n == 1;
    }
}
