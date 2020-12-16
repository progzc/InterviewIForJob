package com.zcprog.arithmetic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 分糖果
 * @Author zhaochao
 * @Date 2020/12/16 23:48
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0575_DistributeCandies {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/distribute-candies/
     * 给定一个偶数长度的数组，其中不同的数字代表着不同种类的糖果，每一个数字代表一个糖果。
     * 你需要把这些糖果平均分给一个弟弟和一个妹妹。返回妹妹可以获得的最大糖果的种类数。
     * <p>
     * 示例 1:
     * 输入: candies = [1,1,2,2,3,3]
     * 输出: 3
     * 解析: 一共有三种种类的糖果，每一种都有两个。
     * 最优分配方案：妹妹获得[1,2,3],弟弟也获得[1,2,3]。这样使妹妹获得糖果的种类数最多。
     * <p>
     * 示例 2 :
     * 输入: candies = [1,1,2,3]
     * 输出: 2
     * 解析: 妹妹获得糖果[2,3],弟弟获得糖果[1,1]，妹妹有两种不同的糖果，弟弟只有一种。这样使得妹妹可以获得的糖果种类数最多。
     * <p>
     * 注意:
     * 数组的长度为[2, 10,000]，并且确定为偶数。
     * 数组中数字的大小在范围[-100,000, 100,000]内。
     */
    public static void main(String[] args) {
        int[][] candyTypes = {{1, 1, 2, 2, 3, 3}, {1, 1, 2, 3}};
        Arrays.stream(candyTypes).forEach(candyType -> {
            System.out.println(iterationSolve(candyType));
        });

    }

    /**
     * 散列表：
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int iterationSolve(int[] candyType) {
        if (candyType == null || candyType.length < 1 || candyType.length % 2 != 0) {
            return 0;
        }
        Set<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < candyType.length; i++) {
            hashSet.add(candyType[i]);
        }
        return Math.min(hashSet.size(), candyType.length / 2);
    }
}
