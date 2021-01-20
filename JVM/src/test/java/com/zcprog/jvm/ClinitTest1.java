package com.zcprog.jvm;

/**
 * @Description 类方法
 * @Author zhaochao
 * @Date 2021/1/20 9:45
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ClinitTest1 {
    public static void main(String[] args) {
        System.out.println(Son.b);
    }

    static class Father {
        public static int A = 1;

        static {
            A = 2;
        }
    }

    static class Son extends Father {
        public static int b = A;
    }
}
