package org.fenixsoft.jvm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 验证volatile不保证原子性
 * @Author zhaochao
 * @Date 2021/1/3 18:30
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Volatile_demo2 {
    public static void main(String[] args) {
        /* System.out.println(Thread.activeCount());*/
        AutoResource autoResource = new AutoResource();
        //20个线程每个线程循环100次
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    autoResource.numberPlusPlus();
                    autoResource.addAtomicInteger();
                }
            }, String.valueOf(i)).start();
        }
        //需要等待上面20个线程都全部计算完后,再用main线程取得的最终的结果值是多少
        //默认有两个线程,一个main线程,二是后台gc线程
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t int type" + autoResource.number); // 13218/15602
        System.out.println(Thread.currentThread().getName() + "\t AutoInteger type" + autoResource.atomicInteger.get()); // 20000
    }
}

class AutoResource {
    volatile int number = 0;
    //使用AutoInteger保证原子性
    AtomicInteger atomicInteger = new AtomicInteger();

    public void numberPlusPlus() {
        number++;
    }

    public void addAtomicInteger() {
        atomicInteger.getAndIncrement();
    }
}
