package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 只出现一次的数字
 * @Author zhaochao
 * @Date 2020/12/15 14:39
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0136_SingleNumber {

    /**
     * LeetCode地址：https://leetcode-cn.com/problems/single-number/
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 说明：
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     * <p>
     * 示例 1:
     * 输入: [2,2,1]
     * 输出: 1
     * 示例 2:
     * <p>
     * 输入: [4,1,2,1,2]
     * 输出: 4
     */
    public static void main(String[] args) {
        int[][] heaps = {{2, 2, 1}, {4, 1, 2, 1, 2}};
        Arrays.stream(heaps).forEach(heap -> {
            System.out.println(Arrays.toString(heap));
            System.out.println(bitSolve(heap));
        });
    }

    /**
     * 位运算
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int bitSolve(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res ^= nums[i];
        }
        return res;
    }
}
