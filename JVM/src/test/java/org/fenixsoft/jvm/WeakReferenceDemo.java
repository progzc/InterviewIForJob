package org.fenixsoft.jvm;

import java.lang.ref.WeakReference;

/**
 * @Description 弱引用测试
 * @Author zhaochao
 * @Date 2021/1/5 21:40
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println("==========");
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(weakReference.get());
    }
}
