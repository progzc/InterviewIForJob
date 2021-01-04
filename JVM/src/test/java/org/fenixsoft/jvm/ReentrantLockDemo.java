package org.fenixsoft.jvm;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 可重入锁
 * @Author zhaochao
 * @Date 2021/1/4 10:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
//        syncTest(phone);
        reenTest(phone);
    }

    private static void reenTest(Phone phone) {
        Thread t3 = new Thread(phone, "t3");
        Thread t4 = new Thread(phone, "t4");
        t3.start();
        t4.start();
    }

    private static void syncTest(Phone phone) {
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

class Phone implements Runnable {
    //Synchronized TEST
    Lock lock = new ReentrantLock();

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "\t" + "sendSMS()");
        sendEmail();
    }

    //Reentrant TEST
    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t" + "sendEmail()");
    }

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "get()");
            set();
        } finally {
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "set()");
        } finally {
            lock.unlock();
        }
    }
}
