package com.zcprog.hw;

import java.util.Scanner;

/**
 * @Description 自首数
 * @Author zhaochao
 * @Date 2021/2/20 16:05
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class NewCoder_middle_AutomorphicNumber {
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int result = calcAutomorphicNumbers(n);
            System.out.println(result);
        }
    }

    private static int calcAutomorphicNumbers(int n) {
        int count = 0;
        for (int i = 0; i <= n; i++) {
            if (((int) Math.pow(i, 2) + "").matches("[0-9]*" + i + "$")) {
                count++;
            }
        }
        return count;
    }
}
