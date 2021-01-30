package com.zcprog.thread;

/**
 * @Description 活跃线程数测试
 * @Author zhaochao
 * @Date 2021/1/30 22:54
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ActiveCountTest {

    public static void main(String[] args) {
        /**
         * IDEA中执行main方法时，除了主线程外，还有Monitor Ctrl-Break线程
         * Eclipse中只有主线程
         */
        System.out.println(Thread.activeCount()); // IDEA中输出2，Eclipse中输出1
        Thread.currentThread().getThreadGroup().list();
    }
}
