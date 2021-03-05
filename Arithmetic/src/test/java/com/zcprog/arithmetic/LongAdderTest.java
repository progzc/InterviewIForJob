package com.zcprog.arithmetic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description LongAdder测试
 * @Author zhaochao
 * @Date 2021/3/5 18:42
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LongAdderTest {
    int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger();
    LongAdder longAdder = new LongAdder();

    public static void main(String[] args) {
        LongAdderTest longAdderTest = new LongAdderTest();
//        longAdderTest.method1();
//        longAdderTest.method2();
//        longAdderTest.method3();
        longAdderTest.method4();
    }

    public void method1() {
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    increment1();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        long l2 = System.currentTimeMillis();
        System.out.println("计算结果,num=" + num);
        System.out.println("耗时:" + (l2 - l1) + "毫秒");
    }

    public void method2() {
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    increment2();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        long l2 = System.currentTimeMillis();
        System.out.println("计算结果,num=" + num);
        System.out.println("耗时:" + (l2 - l1) + "毫秒");
    }

    public void method3() {
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    increment3();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        long l2 = System.currentTimeMillis();
        System.out.println("计算结果,atomicInteger=" + atomicInteger.get());
        System.out.println("耗时:" + (l2 - l1) + "毫秒");
    }

    public void method4() {
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    increment4();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        long l2 = System.currentTimeMillis();
        System.out.println("计算结果,longAdder=" + longAdder.longValue());
        System.out.println("耗时:" + (l2 - l1) + "毫秒");
    }

    // 耗时156毫秒，但结果不准确
    public void increment1() {
        num++;
    }

    // 耗时1037毫秒（重量级锁）
    public void increment2() {
        synchronized (this) {
            num++;
        }
    }

    // 耗时237毫秒（轻量级锁CAS）
    public void increment3() {
        atomicInteger.incrementAndGet();
    }

    // 耗时86毫秒（分段CAS）
    public void increment4() {
        longAdder.increment();
    }
}
