package org.fenixsoft.jvm;

/**
 * @Description 传统版本的生产者消费者模式：使用Lock+Condition实现精准通知
 * @Author zhaochao
 * @Date 2021/1/4 16:54
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 初始值为0的变量，两个线程交替操作，一个+1，一个-1，执行五轮
 * 1 线程  操作  资源类
 * 2 判断  干活  通知
 * 3 防止虚假唤醒机制
 */
public class ProdConsTradiDemo2 {
    public static void main(String[] args) {
        ShareData2 shareData = new ShareData2();

        // 实现多个生产者和消费者对资源类的消费和生产，资源类的最大生产数量为1
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    try {
                        shareData.increment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "Producer" + j).start();
        }

        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    try {
                        shareData.decrement();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "Consumer" + j).start();
        }
    }
}

class ShareData2 {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            //1 判断
            while (number == 1) {
                //等待，不能生产
                condition.await();
            }
            //2 干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //3 通知唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            //1 判断
            while (number == 0) {
                //等待，不能生产
                condition.await();
            }
            //2 干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //3 通知唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
