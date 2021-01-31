package com.zcprog.basic;

/**
 * @Description 系统参数测试
 * @Author zhaochao
 * @Date 2021/1/31 17:12
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class SystemTest {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.version"));
        System.out.println(System.getProperty("os.arch")); // 操作系统：amd64
        System.out.println(System.getProperty("sun.arch.data.model")); // JVM：64

        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB"); //
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB"); // 初始内存：1/64物理内存
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB"); // 最大堆内存：1/4物理内存
    }
}
