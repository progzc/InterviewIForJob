package com.zcprog.hw;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Description 最大数(比较法)
 * @Author zhaochao
 * @Date 2021/2/27 20:27
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

/**
 * 给定一组非负整数 nums，重新排列它们每个数字的顺序（每个数字不可拆分）使之组成一个最大的整数。
 * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
 * <p>
 * 示例 1：
 * 输入：nums = [10,2]
 * 输出："210"
 * <p>
 * 示例 2：
 * 输入：nums = [3,30,34,5,9]
 * 输出："9534330"
 * <p>
 * 示例 3：
 * 输入：nums = [1]
 * 输出："1"
 * <p>
 * 示例 4：
 * 输入：nums = [10]
 * 输出："10"
 * <p>
 * 提示：
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 109
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/largest-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LeetCode_0179_LargestNumber {
    public static void main(String[] args) {
//        int[] nums = {10, 2};
        int[] nums = {3, 30, 34, 5, 9};
//        int[] nums = {1};
//        int[] nums = {10};
        System.out.println(largestNumber(nums));
    }

    public static String largestNumber(int[] nums) {
        String[] asStrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            asStrs[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(asStrs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o2 + o1).compareTo(o1 + o2);
            }
        });
        if (asStrs[0].equals("0")) {
            return "0";
        }
        StringBuilder largestNumberStr = new StringBuilder();
        for (String numAsStr : asStrs) {
            largestNumberStr.append(numAsStr);
        }
        return largestNumberStr.toString();
    }
}
