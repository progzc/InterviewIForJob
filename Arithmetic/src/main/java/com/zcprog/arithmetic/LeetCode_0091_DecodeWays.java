package com.zcprog.arithmetic;

/**
 * @Description 解码方法
 * @Author zhaochao
 * @Date 2020/12/23 22:55
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0091_DecodeWays {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/decode-ways/
     * 一条包含字母 A-Z 的消息通过以下方式进行了编码：
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
     * 题目数据保证答案肯定是一个 32 位的整数。
     * <p>
     * 示例 1：
     * 输入："12"
     * 输出：2
     * 解释：它可以解码为 "AB"（1 2）或者 "L"（12）。
     * <p>
     * 示例 2：
     * 输入："226"
     * 输出：3
     * 解释：它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
     * <p>
     * 示例 3：
     * 输入：s = "0"
     * 输出：0
     * <p>
     * 示例 4：
     * 输入：s = "1"
     * 输出：1
     * <p>
     * 示例 5：
     * 输入：s = "2"
     * 输出：1
     * <p>
     * 提示：
     * 1 <= s.length <= 100
     * s 只包含数字，并且可以包含前导零。
     */
    public static void main(String[] args) {
        String[] strs = {"12", "226", "0", "1", "2"};
        for (String str : strs) {
            System.out.println(dpSolve(str));
        }
    }

    /**
     * 动态规划
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * 状态转移方程需要分情况讨论：
     * 1.若str[i]='0'：
     * --1.1 若str[i-1]='1' or str[i-1]='2'，则dp[i]=dp[i-2]
     * --1.2 否则return 0
     * 2.若str[i]!='0'：
     * --2.1 若str[i-1]='1',则dp[i]=dp[i-1]+dp[i-2]
     * --2.2 若str[i-1]='2'并且'1'<=str[i]<='6',则dp[i]=dp[i-1]+dp[i-2]
     * --2.3 若不属于2.1和2.2的情况，则，dp[i]=dp[i-1]
     * 边界条件：
     * 1. 若str[0]='0',return 0
     * 2. 若str[0]!='0',dp[0]=1,dp[-1]=1
     */
    private static int dpSolve(String str) {
        if (str == null || str.length() < 1 || str.charAt(0) == '0') {
            return 0;
        }

        // dp[-1]=dp[0]=1
        int pre = 1;
        int curr = 1;
        // 动态规划
        for (int i = 1; i < str.length(); i++) {
            int temp = curr;
            if (str.charAt(i) == '0') {
                if (str.charAt(i - 1) == '1' || str.charAt(i - 1) == '2') {
                    curr = pre;
                } else {
                    return 0;
                }
            } else if (str.charAt(i - 1) == '1'
                    || (str.charAt(i - 1) == '2' && str.charAt(i) >= '1' && str.charAt(i) <= '6')) {
                curr = curr + pre;
            }
            pre = temp;
        }
        return curr;
    }
}
