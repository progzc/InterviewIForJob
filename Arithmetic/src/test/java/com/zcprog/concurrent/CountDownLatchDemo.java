package com.zcprog.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @Description 倒计时器（减法计数器）
 * @Author zhaochao
 * @Date 2020/12/24 22:19
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 总数是6，必须要执行任务的时候，再使用！
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " Go out");
                countDownLatch.countDown(); // 数量-1
            }, String.valueOf(i)).start();
        }

        countDownLatch.await(); // 等待计数器归零，然后再向下执行
        System.out.println("Close Door");
    }
}
