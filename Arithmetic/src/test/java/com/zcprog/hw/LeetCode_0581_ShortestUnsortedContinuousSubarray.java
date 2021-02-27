package com.zcprog.hw;

/**
 * @Description 最短无序连续子数组（排序或单调栈）
 * @Author zhaochao
 * @Date 2021/2/27 16:07
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

/**
 * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * <p>
 * 请你找出符合题意的 最短 子数组，并输出它的长度。
 * <p>
 * 示例 1：
 * 输入：nums = [2,6,4,8,10,9,15]
 * 输出：5
 * 解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序
 * <p>
 * 示例 2：
 * 输入：nums = [1,2,3,4]
 * 输出：0
 * <p>
 * 示例 3：
 * 输入：nums = [1]
 * 输出：0
 * <p>
 * 提示：
 * 1 <= nums.length <= 104
 * -105 <= nums[i] <= 105
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LeetCode_0581_ShortestUnsortedContinuousSubarray {
    public static void main(String[] args) {
        int[] nums = {2, 6, 4, 8, 10, 9, 15};
//        int[] nums = {1, 2, 3, 4};
//        int[] nums = {1};
        System.out.println(findUnsortedSubarray(nums));
    }

    public static int findUnsortedSubarray(int[] nums) {
//        // 排序法
//        int[] copy = nums.clone();
//        Arrays.sort(copy);
//        int start = copy.length;
//        int end = 0;
//        for (int i = 0; i < nums.length; i++) {
//            if (copy[i] != nums[i]) {
//                start = Math.min(start, i);
//                end = Math.max(end, i);
//            }
//        }
//        return (end - start >= 0) ? end - start + 1 : 0;

//        // 单调栈
//        Stack<Integer> stack = new Stack<>();
//        int left = nums.length;
//        int right = 0;
//        for (int i = 0; i < nums.length; i++) {
//            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
//                left = Math.min(left, stack.pop());
//            }
//            stack.push(i);
//        }
//        stack.clear();
//        for (int i = nums.length - 1; i >= 0; i--) {
//            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
//                right = Math.max(right, stack.pop());
//            }
//            stack.push(i);
//        }
//        return right - left > 0 ? right - left + 1 : 0;

        // 单调栈：优化空间
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        boolean flag = false;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1])
                flag = true;
            if (flag)
                min = Math.min(min, nums[i]);
        }
        flag = false;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1])
                flag = true;
            if (flag)
                max = Math.max(max, nums[i]);
        }
        int left, right;
        for (left = 0; left < nums.length; left++) {
            if (min < nums[left])
                break;
        }
        for (right = nums.length - 1; right >= 0; right--) {
            if (max > nums[right])
                break;
        }
        return right - left < 0 ? 0 : right - left + 1;
    }
}
