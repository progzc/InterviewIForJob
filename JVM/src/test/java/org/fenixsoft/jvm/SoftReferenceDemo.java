package org.fenixsoft.jvm;

import java.lang.ref.SoftReference;

/**
 * @Description 软引用的测试
 * @Author zhaochao
 * @Date 2021/1/5 21:27
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {
        softRef_Memory_Enough();
        System.out.println("Not Enough");
        softRef_Memory_NotEnough();
    }

    private static void softRef_Memory_Enough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());
        System.out.println("===========");
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(softReference.get());
    }

    /**
     * 故意产生大对象并配置小内存，让它内存不够用了导致OOM，看软引用的回收情况
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    private static void softRef_Memory_NotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());
        System.out.println("===========");
        o1 = null;
        System.gc();

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }
    }
}
