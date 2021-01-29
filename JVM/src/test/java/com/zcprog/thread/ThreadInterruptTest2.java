package com.zcprog.thread;

/**
 * @Description 使用interrupt() + InterruptedException中断线程
 * @Author zhaochao
 * @Date 2021/1/29 17:43
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ThreadInterruptTest2 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("线程启动了");
            try {
                Thread.sleep(1000 * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程结束了");
        });
        thread.start();

        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();//作用是：在线程阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态
    }
}
