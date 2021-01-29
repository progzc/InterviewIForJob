package com.zcprog.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Description notify测试
 * @Author zhaochao
 * @Date 2021/1/29 19:00
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class WaitAndNotify {
    public static void main(String[] args) {
        Object co = new Object();
        System.out.println(co);

        for (int i = 0; i < 5; i++) {
            MyThread t = new MyThread("Thread" + i, co);
            t.start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("-----Main Thread notify-----");
            synchronized (co) {
                co.notify();
            }

            TimeUnit.SECONDS.sleep(2);
            System.out.println("Main Thread is end.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyThread extends Thread {
        private String name;
        private Object co;

        public MyThread(String name, Object o) {
            this.name = name;
            this.co = o;
        }

        @Override
        public void run() {
            System.out.println(name + " is waiting.");
            try {
                synchronized (co) {
                    co.wait();
                }
                System.out.println(name + " has been notified.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
