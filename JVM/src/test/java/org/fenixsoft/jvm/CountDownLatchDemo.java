package org.fenixsoft.jvm;

import java.util.concurrent.CountDownLatch;

/**
 * @Description 倒计时器
 * @Author zhaochao
 * @Date 2021/1/4 14:11
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        leaveClassroom();
        county();
    }

    private static void county() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国被灭");
                countDownLatch.countDown();
            }, CountryEnum.list(i).getRetMsg()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t ******秦国一统华夏");
    }

    private static void leaveClassroom() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t上完自习，离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t******班长最后关门走人");
    }
}
