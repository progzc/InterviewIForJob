package com.zcprog.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * @Description 字符串测试
 * @Author zhaochao
 * @Date 2020/12/13 22:54
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class StringTest {
    @Test
    public void test() {
        System.out.println("1,2,2,null,3,null,3,".replaceFirst("[,]$", ""));
    }

    @Test
    public void test2() {
        Integer[] nums2 = {1, 2, 2, null, 3, null, 3};
        System.out.println(Arrays.toString(nums2));

        // 若包装类为null，则直接拆箱会报错
//        Integer i = null;
//        int j = i;
//        System.out.println("j:" + j);
    }

    @Test
    public void test3() {
        System.out.println('0' - 'P'); // 输出-32
    }
}
