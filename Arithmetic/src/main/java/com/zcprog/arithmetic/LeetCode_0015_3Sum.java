package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 三数之和
 * @Author zhaochao
 * @Date 2020/12/21 9:49
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0015_3Sum {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/3sum/
     * 给你一个包含n个整数的数组nums,判断nums中是否存在三个元素a,b,c,使得a+b+c=0？请你找出所有满足条件且不重复的三元组。
     * 注意：答案中不可以包含重复的三元组。
     * 示例：
     * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
     * 满足要求的三元组集合为：
     * [
     * [-1, 0, 1],
     * [-1, -1, 2]
     * ]
     */
    public static void main(String[] args) {
        int[][] heaps = {{-1, 0, 1, 2, -1, -4}, {0, 0, 0}, {-2, 0, 0, 2, 2}};
        for (int[] heap : heaps) {
            System.out.println(doublePointerSolve1(heap));
            System.out.println(doublePointerSolve2(heap));
        }
    }

    /**
     * 排序+双指针：细节优化
     * 优化点：
     * 1.第一个数大于0，则break
     * 2.将List添加到List先采用Arrays.asList将数组转换为List，再添加到List
     * 3.排除重复元素的技巧
     * 时间复杂度：O(n^2)
     * 空间复杂度：取决于排序算法
     */
    private static List<List<Integer>> doublePointerSolve2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return ans;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break; // 第一个数大于0，后面的数都比它大，肯定不成立了
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 去掉重复情况
            }
            int target = -nums[i];
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    ans.add(new ArrayList<>(Arrays.asList(nums[i], nums[left], nums[right])));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else if (nums[left] + nums[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return ans;
    }

    /**
     * 排序+双指针
     * 时间复杂度：O(n^2)
     * 空间复杂度：取决于排序算法
     */
    private static List<List<Integer>> doublePointerSolve1(int[] nums) {
        if (nums == null) {
            return null;
        }
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length < 3) {
            return ans;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; ) {
            int m = i + 1;
            int n = nums.length - 1;
            while (n > m) {
                if (nums[m] + nums[n] + nums[i] > 0) {
                    n--;
                } else if (nums[m] + nums[n] + nums[i] < 0) {
                    m++;
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[m]);
                    list.add(nums[n]);
                    ans.add(list);
                    int temp1 = nums[n];
                    n--;
                    while (n > m && nums[n] >= temp1) {
                        n--;
                    }
                    int temp2 = nums[m];
                    m++;
                    while (n > m && nums[m] <= temp2) {
                        m++;
                    }
                }
            }
            int value = nums[i];
            i++;
            while (i < nums.length && nums[i] <= value) {
                i++;
            }
        }
        return ans;
    }
}
