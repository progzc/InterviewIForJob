package com.zcprog.thread;

/**
 * @Description 线程中断测试
 * @Author zhaochao
 * @Date 2021/1/29 18:17
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ThreadInterruptTest5 {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("线程启动了");
                while (true) {//对于这种情况，即使线程调用了intentrupt()方法并且isInterrupted()，但线程还是会继续运行，根本停不下来！
                    System.out.println(isInterrupted());//调用interrupt之后为true
                }
            }
        };
        thread.start();
        thread.interrupt();//注意，此方法不会中断一个正在运行的线程，它的作用是：在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态
        while (true) {
            System.out.println("是否isInterrupted：" + thread.isInterrupted());//true
        }
    }
}
