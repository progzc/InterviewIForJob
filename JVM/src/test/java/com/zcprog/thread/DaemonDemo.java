package com.zcprog.thread;

/**
 * @Description 守护线程的测试
 * @Author zhaochao
 * @Date 2021/1/29 22:28
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class DaemonDemo {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("i am alive");
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("守护线程结束时间：" + System.currentTimeMillis() + "ms");
                    }
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
        // 确保main线程结束前能给daemonThread能够分到时间片
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main线程结束时间：" + System.currentTimeMillis() + "ms");
    }
}
