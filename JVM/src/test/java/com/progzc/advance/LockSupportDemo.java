package com.progzc.advance;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description LockSupport的使用总结
 * @Author zhaochao
 * @Date 2021/1/7 12:15
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LockSupportDemo {
    static Object objectLock = new Object();

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
//        synchronizedWaitNotify();
//        conditionAwaitSignal();
        lockSupportPackUnpack();
    }

    private static void lockSupportPackUnpack() {
        Thread a = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
            LockSupport.park(); // 被阻塞，等待通知等待放行，需要通过需要许可证
            System.out.println(Thread.currentThread().getName() + "\t" + "-----被唤醒");
        }, "a");
        a.start();

        Thread b = new Thread(() -> {
            LockSupport.unpark(a);
            System.out.println(Thread.currentThread().getName() + "\t" + "-----通知了");
        }, "b");
        b.start();
    }

    private static void conditionAwaitSignal() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "-----被唤醒");
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t" + "-----唤醒动作");
            } finally {
                lock.unlock();
            }
        }, "B").start();
    }

    private static void synchronizedWaitNotify() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "-----被唤醒");
            }
        }, "A").start();
        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t" + "-----唤醒动作");
            }
        }, "B").start();
    }
}
