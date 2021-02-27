package com.zcprog.hw;

/**
 * @Description 下一个更大元素i（哈希表+单调栈）
 * @Author zhaochao
 * @Date 2021/2/27 14:33
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

/**
 * 给你两个没有重复元素的数组nums1和nums2，其中nums1是nums2的子集。
 * <p>
 * 请你找出nums1中每个元素在nums2中的下一个比其大的值。
 * <p>
 * nums1中数字x的下一个更大元素是指x在nums2中对应位置的右边的第一个比x大的元素。如果不存在，对应位置输出-1。
 * <p>
 * 输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * 输出: [-1,3,-1]
 * 解释:
 * 对于 num1 中的数字 4 ，你无法在第二个数组中找到下一个更大的数字，因此输出 -1 。
 * 对于 num1 中的数字 1 ，第二个数组中数字1右边的下一个较大数字是 3 。
 * 对于 num1 中的数字 2 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/next-greater-element-i
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LeetCode_0496_NextGreaterElement_i {
    public static void main(String[] args) {
        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2};
        System.out.println(Arrays.toString(nextGreaterElement(nums1, nums2)));
    }

    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
//        // 常规解法：哈希表
//        int[] ans = new int[nums1.length];
//        HashMap<Integer, Integer> hashMap = new HashMap<>();
//        for (int i = 0; i < nums2.length; i++) {
//            hashMap.put(nums2[i], i);
//        }
//        for (int i = 0; i < nums1.length; i++) {
//            ans[i] = -1;
//            for (int j = hashMap.get(nums1[i]) + 1; j < nums2.length; j++) {
//                if (nums2[j] > nums1[i]) {
//                    ans[i] = nums2[j];
//                    break;
//                }
//            }
//        }
//        return ans;
        // 最优解：哈希表+单调栈
        int[] ans = new int[nums1.length];
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                hashMap.put(stack.pop(), nums2[i]);
            }
            stack.push(nums2[i]);
        }
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = hashMap.getOrDefault(nums1[i], -1);
        }
        return ans;
    }
}
