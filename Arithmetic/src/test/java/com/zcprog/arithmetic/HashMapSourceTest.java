package com.zcprog.arithmetic;

import org.junit.Test;

/**
 * @Description HashMap源码测试
 * @Author zhaochao
 * @Date 2021/3/1 17:34
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class HashMapSourceTest {

    @Test
    public void test1() {
        int MAXIMUM_CAPACITY = 1 << 30;
        int cap = 10;// 10, 16, 17
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        int ans = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        System.out.println(ans);// 16, 16, 32
    }
}
