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

    @Test
    public void test4() {
        System.out.println(Integer.MAX_VALUE / 2);  // 1073741823
        System.out.println(Integer.MIN_VALUE / 2); // -1073741824
    }

    @Test
    public void test5() {
        System.out.println(Arrays.toString(" ".split("\\s+"))); // []
        System.out.println(" ".split("\\s+").length); // 0
    }

    @Test
    public void test6() {
        String str_1 = new String("ab");
        String str_2 = new String("ab");
        String str_3 = "ab";
        System.out.println(str_1 == str_2); // false
        System.out.println(str_1 == str_2.intern()); // false
        System.out.println(str_1.intern() == str_2.intern()); // true
        System.out.println(str_1 == str_3); // false
        System.out.println(str_1.intern() == str_3); // true
    }

    @Test
    public void test7() {
        StringBuilder sb = new StringBuilder("abcd"); // 构造器super(str.length() + 16);
        // sb的长度并非为数组初始化的长度
        // 获取sb的长度的时间复杂度为O(1)
        // 按照2n+2进行扩容: newCapacity = (value.length << 1) + 2;
        System.out.println(sb.length());
    }

}
