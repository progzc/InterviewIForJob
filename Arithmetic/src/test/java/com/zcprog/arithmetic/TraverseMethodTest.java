package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description ArrayList和LinkedList不同遍历方式的耗时
 * @Author zhaochao
 * @Date 2020/12/18 20:58
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class TraverseMethodTest {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new LinkedList<>();
        for (int i = 0; i < 200000; i++) {
            list1.add(i);
            list2.add(i);
        }

        // ArrayList使用fori遍历，耗时：5ms
        int size = list1.size();
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list1.get(i);
        }
        long end1 = System.currentTimeMillis();
        System.out.println("ArrayList使用fori遍历，耗时：" + (end1 - start1) + "ms");

        // ArrayList使用iterator遍历，耗时：25ms
        long start2 = System.currentTimeMillis();
        Iterator<Integer> it1 = list1.iterator();
        while (it1.hasNext()) {
            Integer next = it1.next();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("ArrayList使用iterator遍历，耗时：" + (end2 - start2) + "ms");

        // LinkedList使用fori遍历，耗时：ms
        int size2 = list2.size();
        long start3 = System.currentTimeMillis();
        for (int i = 0; i < size2; i++) {
            list2.get(i);
        }
        long end3 = System.currentTimeMillis();
        System.out.println("LinkedList使用fori遍历，耗时：" + (end3 - start3) + "ms");

        // LinkedList使用iterator遍历，耗时：ms
        long start4 = System.currentTimeMillis();
        Iterator<Integer> it2 = list2.iterator();
        while (it2.hasNext()) {
            Integer next = it2.next();
        }
        long end4 = System.currentTimeMillis();
        System.out.println("LinkedList使用iterator遍历，耗时：" + (end4 - start4) + "ms");

    }
}
