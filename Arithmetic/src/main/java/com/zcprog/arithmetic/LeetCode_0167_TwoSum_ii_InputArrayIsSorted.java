package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 两数之和II输入有序数组
 * @Author zhaochao
 * @Date 2020/12/15 15:33
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0167_TwoSum_ii_InputArrayIsSorted {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/
     * 给定一个已按照升序排列的有序数组，找到两个数使得它们相加之和等于目标数。
     * 函数应该返回这两个下标值 index1和index2，其中 index1 必须小于 index2。
     * 说明:
     * 返回的下标值（index1 和 index2）不是从零开始的。
     * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
     * <p>
     * 示例:
     * 输入: numbers = [2, 7, 11, 15], target = 9
     * 输出: [1,2]
     * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
     */
    public static void main(String[] args) {
//        int[] numbers = {2, 3, 7, 15}; // target:9
//        int[] numbers = {2, 3, 4}; // target:6
        int[] numbers = {1, 2, 3, 4, 4, 9, 56, 90};

        System.out.println("-----------双指针----------");
        int[] result = doublePointerSolve(numbers, 8);
        System.out.println(Arrays.toString(result));
        System.out.println("-----------二分查找----------");
        int[] result2 = binarySearchSolve(numbers, 8);
        System.out.println(Arrays.toString(result2));
    }

    /**
     * 双指针
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int[] doublePointerSolve(int[] numbers, int target) {
        int low = 0;
        int high = numbers.length - 1;
        while (low < high) {
            int sum = numbers[low] + numbers[high];
            if (sum == target) {
                return new int[]{low + 1, high + 1};
            } else if (sum > target) {
                high--;
            } else {
                low++;
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * 二分查找法
     * 时间复杂度：O(n*log(n))
     * 空间复杂度：O(1)
     */
    private static int[] binarySearchSolve(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            // 注意:low = i+1; 否则不满足"不可以重复使用相同的元素"
            int low = i + 1;
            int high = numbers.length - 1;
            // 注意:low <= high，必须带等号，否则可能会错过和为target的数
            while (low <= high) {
                int mid = (high - low) / 2 + low;
                if (numbers[mid] == target - numbers[i]) {
                    return new int[]{i + 1, mid + 1};
                } else if (numbers[mid] > target - numbers[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return new int[]{-1, -1};
    }
}
