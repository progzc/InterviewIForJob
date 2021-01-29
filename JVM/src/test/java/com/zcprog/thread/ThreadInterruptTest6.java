package com.zcprog.thread;

/**
 * @Description 测试interrupted()方法
 * @Author zhaochao
 * @Date 2021/1/29 18:23
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ThreadInterruptTest6 {
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
        System.out.println("第一次调用返回值：" + Thread.interrupted()); // false
        System.out.println("第二次调用返回值：" + Thread.interrupted()); // false
    }
}
