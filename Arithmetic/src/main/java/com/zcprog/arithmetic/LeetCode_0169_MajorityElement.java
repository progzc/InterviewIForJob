package com.zcprog.arithmetic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 多数元素
 * @Author zhaochao
 * @Date 2020/12/15 16:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0169_MajorityElement {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/majority-element/
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于n/2的元素。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * <p>
     * 示例 1:
     * 输入: [3,2,3]
     * 输出: 3
     * <p>
     * 示例 2:
     * 输入: [2,2,1,1,1,2,2]
     * 输出: 2
     */
    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        System.out.println(apiSolve(nums));
        System.out.println(hashSolve(nums));
        System.out.println(voteSolve(nums));
    }

    /**
     * 排序法：先排序，再取中间值，但是会破坏原数组
     * 时间复杂度：O(n*log(n))
     * 空间复杂度：O(log(n))
     */
    private static int apiSolve(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * 哈希表法：
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int hashSolve(int[] nums) {
        HashMap<Integer, Integer> hashMap = new HashMap<>(16);
        for (int num : nums) {
            if (!hashMap.containsKey(num)) {
                hashMap.put(num, 1);
            } else {
                hashMap.put(num, hashMap.get(num) + 1);
            }
        }
        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            if (majorityEntry == null) {
                majorityEntry = entry;
            }
            if (entry.getValue() > majorityEntry.getValue()) {
                majorityEntry = entry;
            }
        }
        return majorityEntry.getKey();
    }

    /**
     * 投票法：
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int voteSolve(int[] nums) {
        int count = 0;
        Integer candidate = Integer.MIN_VALUE;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
}
