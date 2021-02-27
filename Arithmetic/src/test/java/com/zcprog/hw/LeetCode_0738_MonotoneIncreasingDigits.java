package com.zcprog.hw;

/**
 * @Description 单调递增的数字(贪心算法)
 * @Author zhaochao
 * @Date 2021/2/27 17:00
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0738_MonotoneIncreasingDigits {
    public static void main(String[] args) {
//        int N = 10;
//        int N = 1234;
        int N = 332;
        System.out.println(monotoneIncreasingDigits(N));
    }

    public static int monotoneIncreasingDigits(int N) {
        char[] strN = Integer.toString(N).toCharArray();
        int i = 1;
        while (i < strN.length && strN[i - 1] <= strN[i]) {
            i++;
        }
        if (i < strN.length) {
            while (i > 0 && strN[i - 1] > strN[i]) {
                strN[i - 1] -= 1;
                i--;
            }
            for (i += 1; i < strN.length; i++) {
                strN[i] = '9';
            }
        }
        return Integer.parseInt(new String(strN));
    }
}
