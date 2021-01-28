package com.zcprog.basic;

/**
 * @Description 成员内部类
 * @Author zhaochao
 * @Date 2021/1/28 15:34
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

/**
 * 成员内部类：
 * 1.创建成员内部类的对象：Out2.Inner2 inner2 = new Out2().new Inner2();
 * 2.成员内部类不能定义静态方法和变量（final修饰的除外）
 * (因为成员内部类是非静态的，类初始化的时候先初始化静态成员;
 * 如果允许成员内部类定义静态变量，那么成员内部类的静态变量初始化顺序是有歧义的)
 * 3.成员内部类访问外部类：
 * --（1）可以直接访问外部类的静态变量和静态方法，即使是private修饰的
 * --（2）可以直接访问外部类的非静态变量和非静态方法，即使是private修饰的
 * 4.外部类访问成员内部类：
 * --（1）可以通过成员内部类的实例（不能直接）访问其非静态变量和非静态方法，即使是private修饰的
 */
public class Out2 {
    private static int a = new Out2().new Inner2().c;
    private int b = new Inner2().c;

    public class Inner2 {
        private int c;

        public void print() {
            System.out.println(a);
            System.out.println(b);
        }
    }
}
