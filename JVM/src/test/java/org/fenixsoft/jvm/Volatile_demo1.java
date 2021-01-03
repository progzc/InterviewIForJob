package org.fenixsoft.jvm;

import java.util.concurrent.TimeUnit;

/**
 * @Description volatile可见性测试
 * @Author zhaochao
 * @Date 2021/1/3 18:16
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */


public class Volatile_demo1 {
    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t coming ");
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.addNumber();
            System.out.println(Thread.currentThread().getName() + "\t update " + resource.number);
        }, "线程A").start();

        //如果主线程访问resource.number==0,那么就一直进行循环
        while (resource.number == 0) {

        }
        //如果执行到了这里,证明main现在通过resource.number的值为60
        System.out.println(Thread.currentThread().getName() + "\t" + resource.number);

    }
}

/**
 * 验证volatile的可见性:
 * 1.加入int number=0; number变量之前没有添加volatile关键字修饰,没有可见性
 * 2.添加了volatile,可以解决可见性问题
 */
class Resource {
    volatile int number = 0;
//    int number = 0;

    public void addNumber() {
        this.number = 60;
    }
}
