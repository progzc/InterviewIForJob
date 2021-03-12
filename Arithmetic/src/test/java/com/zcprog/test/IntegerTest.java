package com.zcprog.test;

/**
 * @Description 包装类的缓存原理思考（字节码）
 * @Author zhaochao
 * @Date 2021/3/11 16:48
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class IntegerTest {
    public static void main(String[] args) {

        // ----------------------Integer的缓存--------------------
        // 查看字节码可知，
        // 基本类型会进行自动装箱，调用Integer.valueOf方法
        // 包装类型会进行自动拆箱，调用Integer.intValue方法
        Integer num7 = 100;
        Integer num8 = 100;
        System.out.println(num7 == num8); // true

        Integer num11 = Integer.valueOf(100);
        Integer num12 = Integer.valueOf(100);
        System.out.println(num11 == num12); // true

        Integer num1 = new Integer(100);
        int num2 = 100;
        System.out.println(num1 == num2); // true

        // ------------------Integer的缓存失效------------------
        Integer num3 = new Integer(100);
        Integer num4 = new Integer(100);
        System.out.println(num3 == num4); // false

        Integer num9 = 128;
        Integer num10 = 128;
        System.out.println(num9 == num10); // false

        Integer num5 = new Integer(100);
        Integer num6 = 100;
        System.out.println(num5 == num6); // false

        // -----------------其他包装类的缓存机制----------------------
        // Byte/Short/Integer/Long缓存范围为-128到127
        // Character缓存范围为0到127
        Byte byte1 = 1;
        Byte byte2 = 1;
        System.out.println(byte1 == byte2); // true

        Short short1 = 128;
        Short short2 = 128;
        System.out.println(short1 == short2); // false

        Long long1 = -128L;
        Long long2 = -128L;
        System.out.println(long1 == long2); // true

    }
}
