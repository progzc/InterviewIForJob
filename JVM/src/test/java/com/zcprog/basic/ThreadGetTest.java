package com.zcprog.basic;

import java.lang.reflect.Method;

/**
 * @Description 通过反射获取所有的线程
 * @Author zhaochao
 * @Date 2021/1/30 19:55
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ThreadGetTest {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("java.lang.Thread");
        Thread thread = (Thread) clazz.newInstance();
        Method method = clazz.getDeclaredMethod("getThreads");
        method.setAccessible(true);
        Thread[] threads = (Thread[]) method.invoke(thread);
        for (Thread thread1 : threads) {
            System.out.printf("%-40s", thread1);
            System.out.println(thread1.isDaemon());
        }
    }
}
