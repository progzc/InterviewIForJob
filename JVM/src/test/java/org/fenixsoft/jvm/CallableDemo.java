package org.fenixsoft.jvm;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description Callable接口的使用
 * @Author zhaochao
 * @Date 2021/1/4 21:00
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        threadCreateTest();
        callableCreateTest();
    }

    /**
     * 创建Callable方式
     */
    private static void callableCreateTest() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask, "AA").start(); // 同一个FutureTask只会被执行一次
        int result01 = 100;
        int result02 = futureTask.get();
        System.out.println("result=" + (result01 + result02));
    }

    /**
     * 创建Thread的几种方式
     */
    private static void threadCreateTest() {
        // 创建方式一：直接new
        Thread thread1 = new Thread();

        // 创建方式二：通过匿名内部类创建
        Thread thread2 = new Thread("线程2") {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.println("使用匿名内部类创建" + name);
            }
        };
        // 创建方式三：使用Runnable+lambda表达式创建
        Thread thread3 = new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.println("使用Runnable+lambda表达式创建" + name);
        }, "线程3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class MyThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("callable come in ...");
        return 1024;
    }
}
