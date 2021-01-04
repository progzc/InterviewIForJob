package org.fenixsoft.jvm;

/**
 * @Description 传统版本的生产者消费者模式：使用synchronized+wait+notifyAll实现
 * @Author zhaochao
 * @Date 2021/1/4 16:54
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

/**
 * 初始值为0的变量，两个线程交替操作，一个+1，一个-1，执行五轮
 * 1 线程  操作  资源类
 * 2 判断  干活  通知
 * 3 防止虚假唤醒机制
 */
public class ProdConsTradiDemo1 {
    public static void main(String[] args) {
        ShareData1 shareData = new ShareData1();
        // 实现多个生产者和消费者对资源类的消费和生产，资源类的最大生产数量为5
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

class ShareData1 {
    private int number = 0;

    public synchronized void increment() {
        //1 判断
        while (number == 5) {
            // 等待不能生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //2 干活
        number++;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        //3 通知唤醒
        this.notifyAll();
    }

    public synchronized void decrement() {
        //1 判断
        while (number == 0) {
            //等待，不能消费
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //2 干活
        number--;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        //3 通知唤醒
        this.notifyAll();
    }
}
