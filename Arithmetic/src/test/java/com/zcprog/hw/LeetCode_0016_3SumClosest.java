package com.zcprog.hw;

import java.util.Arrays;

/**
 * @Description 最接近的三数之和
 * @Author zhaochao
 * @Date 2021/3/14 0:51
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0016_3SumClosest {
    public static void main(String[] args) {
        int[] nums = {-1, 2, 1, -4};
        int target = 1;
        System.out.println(threeSumClosest(nums, target));
    }

    /**
     * 排序+双指针
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(log(n))
     */
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int min = Integer.MAX_VALUE;
        int ans = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int start = i + 1;
            int end = len - 1;
            while (start < end) {
                int value = nums[i] + nums[start] + nums[end];
                if (value == target) {
                    return value;
                }
                if (Math.abs(value - target) < min) {
                    min = Math.abs(value - target);
                    ans = value;
                }
                if (value > target) {
                    end--;
                } else {
                    start++;
                }
            }
        }
        return ans;
    }
}
