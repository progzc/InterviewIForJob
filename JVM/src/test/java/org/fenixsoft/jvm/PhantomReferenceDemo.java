package org.fenixsoft.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @Description 虚引用的使用
 * @Author zhaochao
 * @Date 2021/1/5 23:56
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference phantomReference = new PhantomReference(o1, referenceQueue);
        System.out.println(o1); // java.lang.Object@330bedb4
        System.out.println(phantomReference.get()); // null
        System.out.println(referenceQueue.poll()); // null

        System.out.println("===========");

        o1 = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(o1); // null
        System.out.println(phantomReference.get()); // null
        System.out.println(referenceQueue.poll()); // java.lang.ref.PhantomReference@2503dbd3
    }
}
