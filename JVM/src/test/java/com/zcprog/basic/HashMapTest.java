package com.zcprog.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description HashMap的死循环问题
 * @Author zhaochao
 * @Date 2021/1/28 18:50
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class HashMapTest extends Thread {
    private static Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    private static AtomicInteger at = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new HashMapTest();
            thread.start();
        }
    }

    @Override
    public void run() {
        while (at.get() < 1000000) {
            map.put(at.get(), at.get());
            at.incrementAndGet();
        }
    }

}
