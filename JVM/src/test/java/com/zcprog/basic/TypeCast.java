package com.zcprog.basic;

import org.junit.Test;

/**
 * @Description 类型转换测试
 * @Author zhaochao
 * @Date 2021/1/28 14:29
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class TypeCast {
    @Test
    public void test1() {
        byte a = 127;
        byte b = 127;
//        b = a + b; // 报编译错误:cannot convert from int to byte
        b += a; // byte的数值范围为-128~127
        System.out.println(b); // -2
    }

    @Test
    public void test2() {
        short s1 = 1;
        s1 += s1;
    }
}
