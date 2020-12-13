package com.zcprog.arithmetic;

import java.util.Arrays;

/**
 * @Description 删除排序数组中的重复项
 * @Author zhaochao
 * @Date 2020/12/13 14:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0026_RemoveDuplicatesFromSortedArray {

    /**
     * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * 你不需要考虑数组中超出新长度后面的元素。
     * 示例 1:
     * 给定数组 nums = [1,1,2],
     * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。
     * <p>
     * 示例 2:
     * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
     * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
     * 你不需要考虑数组中超出新长度后面的元素。
     */
    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int count = doublePointerSolve(nums);
        System.out.println(count);
        int[] unDuplicatedSortedArray = Arrays.copyOfRange(nums, 0, count);
        System.out.println(Arrays.toString(unDuplicatedSortedArray));
    }

    /**
     * 快慢指针
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int doublePointerSolve(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // i慢指针;j快指针
        int i = 0, j = 0;
        while (j < nums.length) {
            if (nums[j] == nums[i]) {
                j++;
            } else {
                nums[++i] = nums[j++];
            }
        }
        return i + 1;
    }
}
