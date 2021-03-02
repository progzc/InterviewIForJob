package com.zcprog.hw;

import java.util.Scanner;

/**
 * @Description 立方根
 * @Author zhaochao
 * @Date 2021/2/20 15:17
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class NewCoder_middle_CubeRoot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            double input = sc.nextDouble();
            double result = getCubeRoot(input);
            System.out.println(result);
            System.out.printf("%.1f\n", result);
        }
        sc.close();
    }

    private static Double getCubeRoot(double n) {
        double x = 1;
        while (Math.abs(x * x * x - n) > 1e-9) {
            x = x - (x * x * x - n) / (3 * x * x);
        }
        return x;
    }
}
