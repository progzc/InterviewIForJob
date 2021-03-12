package com.zcprog.arithmetic;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Description synchronized的锁升级过程
 * @Author zhaochao
 * @Date 2021/3/5 16:43
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LockUpdate {
    public static void main(String[] args) throws Exception {
        User userTemp = new User();
        System.out.println("无状态(001):" + ClassLayout.parseInstance(userTemp).toPrintable());
        // JVM默认延时4s自动开启偏向锁，可通过-XX:BiasedLockingStartupDelay=0取消延时；
        // 如果不要偏向锁，可通过-XX:UseBiasedLocking=false来设置
        Thread.sleep(5000);
        User user = new User();
        System.out.println("启用偏向锁(101):" + ClassLayout.parseInstance(user).toPrintable());
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int i = 0; i < 2; i++) {
            synchronized (user) {
                System.out.println("启用偏向锁(101)(带线程id):" + ClassLayout.parseInstance(user).toPrintable());
            }
            System.out.println("偏向锁释放(101)(带线程id):" + ClassLayout.parseInstance(user).toPrintable());
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        new Thread(() -> {
            synchronized (user) {
                System.out.println("轻量级锁(00):" + ClassLayout.parseInstance(user).toPrintable());
                try {
                    System.out.println("=================================睡眠3秒钟=================================");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("轻量-->重量(10):" + ClassLayout.parseInstance(user).toPrintable());
            }
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (user) {
                System.out.println("重量级锁(10):" + ClassLayout.parseInstance(user).toPrintable());
            }
        }).start();
    }

    static class User {

    }
}


