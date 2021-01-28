package com.zcprog.basic;

/**
 * @Description 静态内部类测试
 * @Author zhaochao
 * @Date 2021/1/28 15:10
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

/**
 * 静态内部类：与外部类相互独立
 * 1.创建静态内部类的对象：Out.Inner inner = new Out.Inner();
 * 2.静态内部类访问外部类：
 * --（1）可以访问直接访问外部类的静态变量和静态方法，即使是private修饰的
 * --（2）可以通过外部类的实例访问外部类的非静态变量和非静态方法，即使是private修饰的
 * 3.外部类访问静态内部类
 * --（1）可以通过静态内部类（不能直接）访问其静态变量和静态方法，即使是private修饰的
 * --（2）可以通过静态内部类的实例（不能直接）访问其非静态变量和非静态方法，即使是private修饰的
 */
public class Out {
    private static int a;
    private int b = Inner.c;
    private int e = new Inner().d;

    public static class Inner {
        private static int c;
        private int d;

        public void print() {
            System.out.println(a);
//            System.out.println(b); // 编译报错
            System.out.println(new Out().b);
        }
    }
}
