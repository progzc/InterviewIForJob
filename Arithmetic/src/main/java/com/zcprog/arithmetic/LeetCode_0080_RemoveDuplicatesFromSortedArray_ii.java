package com.zcprog.arithmetic;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 删除排序数组中的重复项 II
 * @Author zhaochao
 * @Date 2020/12/23 16:49
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0080_RemoveDuplicatesFromSortedArray_ii {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii/
     * 给定一个增序排列的数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * 示例 1:
     * 给定 nums = [1,1,1,2,2,3],
     * 函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。
     * 你不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 示例 2:
     * 给定 nums = [0,0,1,1,1,1,2,3,3],
     * 函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为 0, 0, 1, 1, 2, 3, 3 。
     * 你不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 说明:
     * 为什么返回数值是整数，但输出的答案是数组呢?
     * 请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
     * 你可以想象内部操作如下:
     * // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
     * int len = removeDuplicates(nums);
     * // 在函数里修改输入数组对于调用者是可见的。
     * // 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
     * for (int i = 0; i < len; i++) {
     * print(nums[i]);
     * }
     */
    public static void main(String[] args) {
        int[][] numss = {{1, 1, 1, 2, 2, 3}, {0, 0, 1, 1, 1, 1, 2, 3, 3}, {1, 1, 1, 2, 2, 3}, {1, 1, 1, 2, 2, 3}};
        for (int i = 0; i < numss.length; i++) {
            int len = solve4(numss[i]);
            System.out.println(len);
            System.out.println(Arrays.toString(Arrays.copyOf(numss[i], len)));
        }
    }

    /**
     * 遍历：官解优化
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int solve4(int[] nums) {
        int i = 0;
        for (int n : nums) {
            if (i < 2 || n > nums[i - 2]) {
                nums[i++] = n;
            }
        }
        return i;
    }

    /**
     * 遍历：官解
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static int solve3(int[] nums) {
        int j = 1;
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                count++;
            } else {
                count = 1;
            }
            if (count <= 2) {
                nums[j++] = nums[i];
            }
        }
        return j;
    }


    /**
     * 遍历
     * 时间复杂度：O(n^2)
     * 空间复杂度：O(1)
     */
    private static int solve2(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; ) {
            int cur = i;
            while (i < n && nums[i] == nums[cur]) {
                i++;
            }
            cur = (i - cur) >= 2 ? cur + 2 : cur + 1;
            int temp = i - cur;
            i = cur;
            while (cur < n - temp) {
                nums[cur] = nums[cur + temp];
                cur++;
            }
            n = n - temp;
        }
        return n;
    }

    /**
     * 若数组未排序
     * 弊端：
     * 1.LinkedHashMap的查询性能很差
     * 2.containsKey是重量级的操作
     * 3.额外创造了许多变量(sb1,sb2)
     * 4.未原地修改数组
     */
    private static int solve(int[] nums) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < nums.length; i++) {
            String sb1 = new StringBuilder().append(nums[i]).append("_count_1").toString();
            String sb2 = new StringBuilder().append(nums[i]).append("_count_2").toString();
            if (!map.containsKey(sb1)) {
                map.put(sb1, nums[i]);
            } else {
                if (!map.containsKey(sb2)) {
                    map.put(sb2, nums[i]);
                }
            }
        }
        Collection<Integer> values = map.values();
        int size = 0;
        for (Integer value : values) {
            nums[size++] = value;
        }
        return size;
    }

}
