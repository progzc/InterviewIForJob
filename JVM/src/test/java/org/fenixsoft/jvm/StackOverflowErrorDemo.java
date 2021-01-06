package org.fenixsoft.jvm;

/**
 * @Description 栈溢出的模拟
 * @Author zhaochao
 * @Date 2021/1/6 9:20
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        stackOverflowError();
    }

    private static void stackOverflowError() {
        stackOverflowError();
    }
}
