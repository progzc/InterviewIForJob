package org.fenixsoft.jvm;

import java.util.concurrent.CyclicBarrier;

/**
 * @Description 栅栏的循环使用测试
 * @Author zhaochao
 * @Date 2021/1/4 14:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7);
        for (int i = 1; i <= 7; i++) {
            final int tempInt = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() +
                        "\t 收集到第" + tempInt + "颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("下面是CyclicBarrier的重复使用");
        cyclicBarrier.reset();
        for (int i = 1; i <= 7; i++) {
            final int tempInt = i;
            new Thread(() -> {
                System.out.println(String.format("第%s位参会者到会", Thread.currentThread().getName()));
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("第%s位参会者开始发言", Thread.currentThread().getName()));
            }, String.valueOf(i)).start();
        }
    }
}
