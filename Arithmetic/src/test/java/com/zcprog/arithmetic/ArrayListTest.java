package com.zcprog.arithmetic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description ArrayList测试
 * @Author zhaochao
 * @Date 2020/12/18 19:48
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ArrayListTest {

    /**
     * 测试iterator迭代
     */
    @Test
    public void test1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int i = (int) it.next();
            // 在iterator迭代时，不能使用ArrayList集合本身进行添加或删除元素
            list.add(i); // java.util.ConcurrentModificationException
            list.remove(i); // java.util.ConcurrentModificationException
        }

    }

    /**
     * 测试forEach迭代
     */
    @Test
    public void test2() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        for (Integer i : list) {
            // 在forEach迭代时，不能使用ArrayList集合本身进行添加或删除元素
            list.add(i); // java.util.ConcurrentModificationException
            list.remove(i); // java.util.ConcurrentModificationException
        }
    }

    /**
     * 测试fori迭代
     */
    @Test
    public void test3() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        for (int i = 0; i < 5; i++) {
            // 在fori迭代时，可以使用ArrayList集合本身进行添加或删除元素
            list.add(i);
            list.remove(i);
        }
        System.out.println(list); // [1, 3, 5, 7, 9, 0, 1, 2, 3, 4]
    }

    /**
     * 测试使用迭代器删除元素
     */
    @Test
    public void test4() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            it.next();
            // 在forEach/iterator迭代时，可以使用迭代器可以进行删除元素，但仍然不能进行添加元素
            it.remove();
        }
        System.out.println(list); // 输出[]
    }

    /**
     * 测试使用CopyOnWriteArrayList
     */
    @Test
    public void test5() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int i = (int) it.next();
            // 在iterator迭代时，可以使用CopyOnWriteArrayList集合本身进行添加或删除元素
            list.add(i);
            list.remove(i);
        }
        System.out.println(list); // [1, 3, 5, 7, 9, 1, 3, 5, 7, 9]
    }
}
