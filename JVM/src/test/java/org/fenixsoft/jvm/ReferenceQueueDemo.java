package org.fenixsoft.jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @Description 引用队列的使用测试
 * @Author zhaochao
 * @Date 2021/1/5 23:58
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1, referenceQueue);
        System.out.println(o1); // java.lang.Object@330bedb4
        System.out.println(weakReference.get()); // java.lang.Object@330bedb4
        System.out.println(referenceQueue.poll()); // null

        System.out.println("==========");
        o1 = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(o1); // null
        System.out.println(weakReference.get()); // null
        System.out.println(referenceQueue.poll()); // java.lang.ref.WeakReference@2503dbd3
    }
}
