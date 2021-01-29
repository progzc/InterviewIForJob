package com.zcprog.thread;

/**
 * @Description 使用interrupt() + isInterrupted()中断线程
 * @Author zhaochao
 * @Date 2021/1/29 17:48
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ThreadInterruptTest3 {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("线程启动了");
                // 或while (!Thread.currentThread().isInterrupted())
                while (!isInterrupted()) {
                    System.out.println(isInterrupted());//调用 interrupt 之后为true
                }
                System.out.println("线程结束了");
            }
        };
        thread.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        System.out.println("线程是否被中断：" + thread.isInterrupted());//true
    }
}

