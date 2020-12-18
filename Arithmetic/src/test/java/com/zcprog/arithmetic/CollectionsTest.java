package com.zcprog.arithmetic;

import org.junit.Test;

import java.util.*;

/**
 * @Description Collections工具类常用方法测试
 * @Author zhaochao
 * @Date 2020/12/18 15:17
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CollectionsTest {

    /**
     * Collections工具的排序操作
     */
    @Test
    public void test1() {
        List<Integer> arrayList = new ArrayList<Integer>() {{
            add(-1);
            add(3);
            add(3);
            add(-5);
            add(7);
            add(4);
            add(-9);
            add(-7);
        }};
        System.out.println(arrayList); // 原始的链表：[-1, 3, 3, -5, 7, 4, -9, -7]
        // void reverse(List list)：反转链表
        Collections.reverse(arrayList);
        System.out.println(arrayList); // 反转后的链表：[-7, -9, 4, 7, -5, 3, 3, -1]
        // void rotate(List list, int distance)：旋转链表
        // 当distance为正数时，将list后distance个元素整体移到前面。当distance为负数时，将 list的前distance个元素整体移到后面。
        Collections.rotate(arrayList, 4);
        System.out.println(arrayList); // 旋转后的链表；[-5, 3, 3, -1, -7, -9, 4, 7]
        // void sort(List list)：按自然排序的升序排序
        Collections.sort(arrayList);
        System.out.println(arrayList); // 自然排序后的的链表：[-9, -7, -5, -1, 3, 3, 4, 7]
        // void shuffle(List list)：随机排序
        Collections.shuffle(arrayList);
        System.out.println(arrayList); // 随机排序后的结果：[-9, -1, 3, 7, -5, 4, -7, 3]
        // void swap(List list, int i , int j)：交换两个索引位置的元素
        Collections.swap(arrayList, 2, 5);
        System.out.println(arrayList); // 交换两个索引位置的元素：[-9, -1, 4, 7, -5, 3, -7, 3]
        // void sort(List list, Comparator c)：定制排序
        Collections.sort(arrayList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        System.out.println(arrayList); // 定制排序：[7, 4, 3, 3, -1, -5, -7, -9]
    }

    /**
     * Collections工具类的查找和替换操作
     */
    @Test
    public void test2() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>() {{
            add(-1);
            add(3);
            add(3);
            add(-5);
            add(7);
            add(4);
            add(-9);
            add(-7);
        }};
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>() {{
            add(-3);
            add(-5);
            add(7);
        }};
        System.out.println(arrayList); // 原始arrayList链表：[-1, 3, 3, -5, 7, 4, -9, -7]
        System.out.println(Collections.max(arrayList)); // arrayList链表中的最大值：7
        System.out.println(Collections.min(arrayList)); // arrayList链表中的最小值：-9
        Collections.replaceAll(arrayList, 3, -3); // 将arrayList链表中的所有3替换为-3
        System.out.println(arrayList); // arrayList链表中元素替换后的结果：[-1, -3, -3, -5, 7, 4, -9, -7]
        System.out.println(Collections.frequency(arrayList, -3)); // arrayList链表中-3出现的频率：2
        System.out.println(Collections.indexOfSubList(arrayList, arrayList2)); // arrayList2链表在arrayList链表中第一次出现的索引：2
        Collections.sort(arrayList);
        System.out.println(Collections.binarySearch(arrayList, 7)); // 对arrayList链表（必须有序）进行二分查找，返回查找元素的索引：7
    }

    /**
     * Collections工具类用来设置不可变集合
     */
    @Test
    public void test3() {
        List<Integer> arrayList = new ArrayList<Integer>() {{
            add(-1);
            add(3);
            add(3);
            add(-5);
            add(7);
            add(4);
            add(-9);
            add(-7);
        }};
        Set<Integer> integers1 = new HashSet<Integer>() {{
            add(1);
            add(3);
            add(2);
        }};
        Map<String, Integer> scores = new HashMap<String, Integer>() {{
            put("语文", 80);
            put("Java", 82);
        }};
        // Collections.emptyXXX()：创建一个空的、不可改变的XXX对象
        List<Object> list = Collections.emptyList();
        System.out.println(list); // 创建一个空的、不可变的List对象：[]
        Set<Object> objects = Collections.emptySet();
        System.out.println(objects); // 创建一个空的、不可变的Set对象：[]
        Map<Object, Object> objectObjectMap = Collections.emptyMap();
        System.out.println(objectObjectMap);// 创建一个空的、不可变的Map对象：{}
        // Collections.singletonXXX()：返回一个只包含指定对象（只有一个或一个元素）的不可变的集合对象
        List<List<Integer>> arrayLists = Collections.singletonList(arrayList);
        System.out.println(arrayLists); // [[-1, 3, 3, -5, 7, 4, -9, -7]]
        Set<List<Integer>> singleton = Collections.singleton(arrayList);
        System.out.println(singleton); // [[-1, 3, 3, -5, 7, 4, -9, -7]]
        Map<String, String> map = Collections.singletonMap("1", "name");
        System.out.println(map); // {1=name}
        // unmodifiableXXX()：创建普通XXX对象对应的不可变版本
        List<Integer> integers = Collections.unmodifiableList(arrayList);
        System.out.println(integers); // [-1, 3, 3, -5, 7, 4, -9, -7]
        Set<Integer> integers2 = Collections.unmodifiableSet(integers1);
        System.out.println(integers2); // [1, 2, 3]
        Map<Object, Object> objectObjectMap2 = Collections.unmodifiableMap(scores);
        System.out.println(objectObjectMap2); // {Java=82, 语文=80}
//        // 添加出现异常：java.lang.UnsupportedOperationException
//        list.add(1);
//        arrayLists.add(arrayList);
//        integers.add(1);
    }
}
