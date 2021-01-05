package org.fenixsoft.jvm;

/**
 * @Description 强引用的使用
 * @Author zhaochao
 * @Date 2021/1/5 21:20
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        o1 = null;
        System.gc();
        System.out.println(o2);
    }
}
