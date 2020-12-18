package com.zcprog.arithmetic;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 数组工具类的使用测试
 * @Author zhaochao
 * @Date 2020/12/18 16:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ArraysTest {

    /**
     * Arrays的排序
     */
    @Test
    public void test1() {
        int[] a = {1, 3, 2, 7, 6, 5, 4, 9};
        // 排序
        Arrays.sort(a);
        System.out.println(Arrays.toString(a)); // [1, 2, 3, 4, 5, 6, 7, 9]

        int[] b = {1, 3, 2, 7, 6, 5, 4, 9};
        // 截取部分排序
        Arrays.sort(b, 2, 6);
        System.out.println(Arrays.toString(b)); // [1, 3, 2, 5, 6, 7, 4, 9]

        int[] c = {1, 3, 2, 7, 6, 5, 4, 9};
        // 并行排序
        Arrays.parallelSort(c);
        System.out.println(Arrays.toString(c)); // [1, 2, 3, 4, 5, 6, 7, 9]

        char[] d = {'a', 'f', 'b', 'c', 'e', 'A', 'C', 'B'};
        // 并行排序
        Arrays.parallelSort(d);
        System.out.println(Arrays.toString(d)); // [A, B, C, a, b, c, e, f]

        // 字符串按照字典顺序排序
        String[] strs = {"abcdehg", "abcdefg", "abcdeag"};
        Arrays.sort(strs);
        System.out.println(Arrays.toString(strs)); // [abcdeag, abcdefg, abcdehg]
    }

    /**
     * Arrays的查找：需要先排序
     */
    @Test
    public void test2() {
        char[] e = {'a', 'f', 'b', 'c', 'e', 'A', 'C', 'B'};
        // 排序
        Arrays.sort(e);
        System.out.println(Arrays.toString(e)); // [A, B, C, a, b, c, e, f]
        // 二分搜索
        int s = Arrays.binarySearch(e, 'c');
        System.out.println(s); // 5
    }

    /**
     * Arrays的比较
     */
    @Test
    public void test3() {
        char[] e = {'a', 'f', 'b', 'c', 'e', 'A', 'C', 'B'};
        char[] f = {'a', 'f', 'b', 'c', 'e', 'A', 'C', 'B'};
        System.out.println(Arrays.equals(e, f)); // true
    }

    /**
     * Arrays的填充
     */
    @Test
    public void test4() {
        int[] g = {1, 2, 3, 3, 3, 3, 6, 6, 6};
        int[] gg = new int[10];
        // 填充
        Arrays.fill(g, 3);
        Arrays.fill(gg, 2);
        System.out.println(Arrays.toString(g)); // [3, 3, 3, 3, 3, 3, 3, 3, 3]
        System.out.println(Arrays.toString(gg)); // [2, 2, 2, 2, 2, 2, 2, 2, 2, 2]

        int[] h = {1, 2, 3, 3, 3, 3, 6, 6, 6,};
        // 部分填充
        Arrays.fill(h, 0, 2, 9);
        System.out.println(Arrays.toString(h)); // [9, 9, 3, 3, 3, 3, 6, 6, 6]
    }

    /**
     * Arrays将数组转换为链表
     */
    @Test
    public void test5() {
        // 数组转换位列表
        List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
        System.out.println(stooges);

        // 列表转化位数组
        String[] strings = stooges.toArray(new String[0]);
        System.out.println(Arrays.toString(strings));
    }

    /**
     * Arrays打印数组
     */
    @Test
    public void test6() {
        char[] k = {'a', 'f', 'b', 'c', 'e', 'A', 'C', 'B'};
        System.out.println(Arrays.toString(k)); // [a, f, b, c, e, A, C, B]
    }

    /**
     * Arrays复制数组
     */
    @Test
    public void test7() {
        int[] h = {1, 2, 3, 3, 3, 3, 6, 6, 6,};
        // 复制一定的长度
        int[] i = Arrays.copyOf(h, 6);
        System.out.println(Arrays.toString(i)); // [1, 2, 3, 3, 3, 3]

        // 截取复制(超出部分补默认值)
        int[] j = Arrays.copyOfRange(h, 6, 11);
        System.out.println(Arrays.toString(j)); // [6, 6, 6, 0, 0]
    }
}
