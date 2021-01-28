package com.zcprog.basic;

/**
 * @Description 移位运算符
 * @Author zhaochao
 * @Date 2021/1/28 16:10
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ShiftOperator {
    public static void main(String[] args) {
        // 源码：10000000 00000000 00000000 00000010
        // 取反：11111111 11111111 11111111 11111101
        // 补码：11111111 11111111 11111111 11111110
        System.out.println(Integer.toBinaryString(-2));
        // 右移：11111111 11111111 11111111 11111111
        System.out.println(-2 >> 20);
        System.out.println(Integer.toBinaryString(-2 >> 20));

        // 无符号右移：00000000 00000000 00001111 11111111
        System.out.println(-2 >>> 20);
        System.out.println(Integer.toBinaryString(-2 >>> 20));
    }
}
