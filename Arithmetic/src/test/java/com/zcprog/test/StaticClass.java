package com.zcprog.test;

/**
 * @Description 静态内部类的加载时间
 * @Author zhaochao
 * @Date 2021/3/13 12:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class StaticClass {
    public static long OUTER_DATE = System.currentTimeMillis();

    static {
        System.out.println("外部类静态块加载时间：" + System.currentTimeMillis());
    }

    public StaticClass() {
        System.out.println("外部类构造函数时间：" + System.currentTimeMillis());
    }

    public static void main(String[] args) {
        // 静态内部类的懒加载机制：在使用时才会加载
//        StaticClass outer = new StaticClass();
//        System.out.println("外部类静态变量加载时间：" + outer.OUTER_DATE);
//        System.out.println("外部类静态变量加载时间：" + outer.new InnerClass().INNER_DATE);
        System.out.println("静态内部类加载时间：" + InnerStaticClass.INNER_STATIC_DATE);
    }

    static class InnerStaticClass {
        public static long INNER_STATIC_DATE = System.currentTimeMillis();

        static {
            System.out.println("静态内部类静态块加载时间：" + System.currentTimeMillis());
        }
    }

    class InnerClass {
        public long INNER_DATE = 0;

        public InnerClass() {
            INNER_DATE = System.currentTimeMillis();
        }
    }
}
