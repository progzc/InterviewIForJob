package com.zcprog.jvm;

import org.junit.Test;

/**
 * @Description 自增指令
 * @Author zhaochao
 * @Date 2021/1/24 12:59
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

public class ArithmeticTest {

    @Test
    public void test1() {
        int i = 10;
        i = i++;
        System.out.println(i); // 10
    }

    @Test
    public void test2() {
        int i = 10;
        i = ++i;
        System.out.println(i); // 11
    }
}
