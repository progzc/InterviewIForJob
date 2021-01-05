package org.fenixsoft.jvm;

import java.util.concurrent.TimeUnit;

/**
 * @Description 通过程序获得JVM堆的初始大小和最大大小
 * @Author zhaochao
 * @Date 2021/1/5 19:12
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        // java虚拟机中的内存总量
        long totalMemory = Runtime.getRuntime().totalMemory();
        // java虚拟机试图使用的最大内存量
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("TOTAL_MEMORY(-Xms)=" + totalMemory + "(字节)" + (totalMemory / 1024 / 2014) + "MB");
        System.out.println("MAX_MEMORY(-Xmx)=" + maxMemory + "(字节)" + (maxMemory / 1024 / 2014) + "MB");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
