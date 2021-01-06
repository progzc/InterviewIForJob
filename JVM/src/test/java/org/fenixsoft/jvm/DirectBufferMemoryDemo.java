package org.fenixsoft.jvm;

import java.nio.ByteBuffer;

/**
 * @Description 直接内存溢出异常
 * @Author zhaochao
 * @Date 2021/1/6 11:07
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
//-Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory: " + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
