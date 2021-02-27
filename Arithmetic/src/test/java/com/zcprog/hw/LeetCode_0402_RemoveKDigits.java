package com.zcprog.hw;

/**
 * @Description 移掉K位数字(贪心 + 单调栈)
 * @Author zhaochao
 * @Date 2021/2/27 15:35
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。
 * <p>
 * 注意:
 * <p>
 * num 的长度小于 10002 且 ≥ k。
 * num 不会包含任何前导零。
 * <p>
 * 示例1 :
 * 输入: num = "1432219", k = 3
 * 输出: "1219"
 * 解释: 移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219。
 * <p>
 * 示例2 :
 * 输入: num = "10200", k = 1
 * 输出: "200"
 * 解释: 移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
 * <p>
 * 示例 3 :
 * 输入: num = "10", k = 2
 * 输出: "0"
 * 解释: 从原数字移除所有的数字，剩余为空就是0。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-k-digits
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LeetCode_0402_RemoveKDigits {
    public static void main(String[] args) {
        String num = "1432219"; // "10200", "10"
        int k = 3; // 1, 2
        System.out.println(removeKdigits(num, k));
    }

    public static String removeKdigits(String num, int k) {
        Deque<Character> deque = new LinkedList<>();
        int length = num.length();
        for (int i = 0; i < length; i++) {
            char ch = num.charAt(i);
            while (!deque.isEmpty() && k > 0 && deque.peekLast() > ch) {
                deque.pollLast();
                k--;
            }
            deque.offerLast(ch);
        }
        for (int i = 0; i < k; i++) {
            deque.pollLast();
        }
        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        while (!deque.isEmpty()) {
            char ch = deque.pollFirst();
            if (leadingZero && ch == '0') {
                continue;
            }
            leadingZero = false;
            sb.append(ch);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
