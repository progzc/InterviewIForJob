package com.zcprog.basic;

/**
 * @Description 泛型方法的调用测试
 * @Author zhaochao
 * @Date 2021/1/29 21:20
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class GenericityMethod {
    public static void main(String[] args) {
        String result1 = GenericityMethod.<String>test1("调用静态泛型方法1");
        // 泛型方法的类型推断
        String result2 = GenericityMethod.test1("调用静态泛型方法2");
        System.out.println(result1);
        System.out.println(result2);

        GenericityMethod gm = new GenericityMethod();
        String result3 = gm.test2("调用非静态泛型方法1");
        // 泛型方法的类型推断
        String result4 = gm.<String>test2("调用非静态泛型方法2");
        System.out.println(result3);
        System.out.println(result4);
    }

    public static <T> T test1(T arg) {
        return arg;
    }

    public <T> T test2(T arg) {
        return arg;
    }
}
