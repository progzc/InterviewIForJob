package com.zcprog.arithmetic.local;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @Description 斐波那契散列法
 * @Author zhaochao
 * @Date 2021/3/8 14:22
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class FibonacciHash {

    @Test
    public void test1() {
        // 黄金分割数
        int HASH_INCREMENT = 0x61c88647;
        int hashCode = 0;
        ArrayList<Integer> ans = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            int idx = hashCode & 15;
            ans.add(idx);
            System.out.println("斐波那契散列：" + idx + " 普通散列：" + (String.valueOf(i).hashCode() & 15));
        }
        System.out.println(ans); // [7, 14, 5, 12, 3, 10, 1, 8, 15, 6, 13, 4, 11, 2, 9, 0]
    }

    @Test
    public void test2() {
        int HASH_INCREMENT = 0x61c88647;
        int hashCode = 0;
        ArrayList<Integer> ans = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            // ThreadLocal里的斐波那契散列法
            hashCode = i + HASH_INCREMENT;
            int idx = hashCode & 15;
            ans.add(idx);
            System.out.println("斐波那契散列：" + idx + " 普通散列：" + (String.valueOf(i).hashCode() & 15));
        }
        System.out.println(ans); // [7, 8, 9, 10, 11, 12, 13, 14, 15, 0, 1, 2, 3, 4, 5, 6]
    }

    @Test
    public void test3() throws Exception {
        for (int i = 0; i < 5; i++) {
            ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
            Field threadLocalHashCode = objectThreadLocal.getClass().getDeclaredField("threadLocalHashCode");
            threadLocalHashCode.setAccessible(true);
            System.out.println("objectThreadLocal：" + threadLocalHashCode.get(objectThreadLocal));
        }
    }
}
