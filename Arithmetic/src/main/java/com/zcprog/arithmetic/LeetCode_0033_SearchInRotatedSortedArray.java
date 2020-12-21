package com.zcprog.arithmetic;

/**
 * @Description 搜索旋转排序数组
 * @Author zhaochao
 * @Date 2020/12/21 20:33
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0033_SearchInRotatedSortedArray {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
     * 给你一个升序排列的整数数组 nums ，和一个整数 target 。
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。（例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] ）。
     * 请你在数组中搜索 target ，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
     * <p>
     * 示例 1：
     * 输入：nums = [4,5,6,7,0,1,2], target = 0
     * 输出：4
     * <p>
     * 示例 2：
     * 输入：nums = [4,5,6,7,0,1,2], target = 3
     * 输出：-1
     * <p>
     * 示例 3：
     * 输入：nums = [1], target = 0
     * 输出：-1
     * <p>
     * 提示：
     * 1 <= nums.length <= 5000
     * -10^4 <= nums[i] <= 10^4
     * nums 中的每个值都 独一无二
     * nums 肯定会在某个点上旋转
     * -10^4 <= target <= 10^4
     */
    public static void main(String[] args) {
        int[][] heaps = {{4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2}, {1}};
        int[] targets = {0, 3, 0};
        for (int i = 0; i < heaps.length; i++) {
            System.out.println(binarySearchSolve(heaps[i], targets[i]));
        }

    }

    /**
     * 二分法：
     * 时间复杂度：O(log(n))
     * 空间复杂度：O(1)
     */
    private static int binarySearchSolve(int[] nums, int target) {
        if (nums == null || nums.length < 1) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] >= nums[left]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
