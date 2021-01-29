package com.zcprog.thread;

/**
 * @Description 测试interrupted()方法
 * @Author zhaochao
 * @Date 2021/1/29 18:36
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ThreadInterruptTest8 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.interrupt();
        System.out.println("第一次调用返回值：" + thread.isInterrupted()); // true
        System.out.println("第二次调用返回值：" + thread.isInterrupted()); // true?为啥输出是false
    }
}
