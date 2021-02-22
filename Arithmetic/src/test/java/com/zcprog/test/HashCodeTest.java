package com.zcprog.test;

import org.junit.Test;

/**
 * @Description hashcode测试
 * @Author zhaochao
 * @Date 2021/2/22 9:26
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class HashCodeTest {
    @Test
    public void test1() {
        System.out.println(Integer.hashCode(-1));
    }

    @Test
    public void test2() {
        // 假设存到HashMap的key是-2
        int h = Integer.hashCode(-2);
        // JDK 1.8中HashMap中对计算得到的h值进行一次扰动
        int hashcode = h ^ (h >>> 16);
        System.out.println(hashcode); // -65535
        // 数组长度
        int length = 16;
        // 决定放到HashMap数组结构中的哪个槽上
        // 当hashcode>=0时，才满足hashcode & (length - 1)= hashcode % length
        // 当hashcode<0时，hashcode % length会出现负值的情况，所以源码中采用的是hashcode & (length - 1)
        // hashcode & (length - 1)取值不会出现负数的现象
        int slot1 = hashcode & (length - 1); // 当length是2的幂次方时，等价于hashCode%length ？
        int slot2 = hashcode % length;
        System.out.println(slot1); // 1
        System.out.println(slot2); // -15
    }
}
