package org.fenixsoft.jvm;

import java.util.Random;

/**
 * @Description 堆内存溢出异常
 * @Author zhaochao
 * @Date 2021/1/6 9:31
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
//-Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        String str = "adf";
        while (true) {
            str += str + new Random().nextInt(1111111) + new Random().nextInt(222222);
            str.intern();
        }
    }
}
