package com.zcprog.arithmetic;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @Description Unsafe测试
 * @Author zhaochao
 * @Date 2021/3/1 20:54
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class UnsafeTest {
    @Test
    public void test1() throws NoSuchFieldException, IllegalAccessException {
        Integer[] arr = {2, 5, 1, 8, 10};

        //获取Unsafe对象
        Unsafe unsafe = getUnsafe();
        //获取Integer[]的基础偏移量
        int baseOffset = unsafe.arrayBaseOffset(Integer[].class);
        //获取Integer[]中元素的偏移间隔
        int indexScale = unsafe.arrayIndexScale(Integer[].class);

        //获取数组中索引为2的元素对象
        Object o = unsafe.getObjectVolatile(arr, (2 * indexScale) + baseOffset);
        System.out.println(o); //1

        //设置数组中索引为2的元素值为100
        unsafe.putOrderedObject(arr, (2 * indexScale) + baseOffset, 100);

        System.out.println(Arrays.toString(arr));//[2, 5, 100, 8, 10]
    }

    // 反射获取Unsafe对象
    private Unsafe getUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        return (Unsafe) theUnsafe.get(null);
    }
}
