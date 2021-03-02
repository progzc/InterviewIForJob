package com.zcprog.hw;

import java.util.Scanner;

/**
 * @Description Redraiment的走法
 * @Author zhaochao
 * @Date 2021/2/20 13:26
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class NewCoder_middle_Redraiment {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int num = in.nextInt();
            int array[] = new int[num];
            for (int i = 0; i < num; i++) {
                array[i] = in.nextInt();
            }
            int max = GetResult(num, array);
            System.out.println(max);
        }
    }

    public static int GetResult(int num, int[] pInput) {
        // 求不连续的最长递增子序列
        int result[] = new int[num];//存放当前位置的最长字串长度
        for (int i = 0; i < pInput.length; i++) {
            result[i] = 1;
            for (int j = 0; j < i; j++) {
                if (pInput[j] < pInput[i]) {
                    result[i] = Math.max(result[i], result[j] + 1);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < result.length; i++) {
            if (result[i] > max) {
                max = result[i];
            }
        }
        return max;
    }
}
