package org.fenixsoft.jvm;

import java.util.Arrays;

/**
 * @Description 方法的参数传递机制
 * @Author zhaochao
 * @Date 2021/1/3 10:06
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ParameterTransfer {
    public static void main(String[] args) {
        int i = 1;
        String str = "hello";
        Integer num = 200;
        int[] arr = {1, 2, 3, 4, 5};
        MyData my = new MyData();
        change(i, str, num, arr, my);
        System.out.println("i=" + i);
        System.out.println("str=" + str);
        System.out.println("num=" + num);
        System.out.println("arr=" + Arrays.toString(arr));
        System.out.println("my.a=" + my.a);
    }

    private static void change(int i, String str, Integer num, int[] arr, MyData my) {
        i += 1;
        str += "world";
        num += 1;
        arr[0] += 1;
        my.a += 1;
    }

    private static class MyData {
        int a = 10;
    }
}
