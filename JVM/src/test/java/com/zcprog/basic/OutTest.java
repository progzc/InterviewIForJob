package com.zcprog.basic;

/**
 * @Description 测试内部类
 * @Author zhaochao
 * @Date 2021/1/28 15:27
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class OutTest {
    public static void main(String[] args) {
        Out.Inner inner = new Out.Inner();
        inner.print();

        Out2.Inner2 inner2 = new Out2().new Inner2();
    }
}
