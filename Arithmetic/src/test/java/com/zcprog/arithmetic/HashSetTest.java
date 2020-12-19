package com.zcprog.arithmetic;

import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Description HashSet集合测试
 * @Author zhaochao
 * @Date 2020/12/19 16:12
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class HashSetTest {

    /**
     * HashSet集合测试
     */
    @Test
    public void test1() {
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(null);
        linkedHashSet.add(null);
        System.out.println(linkedHashSet);

        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(null);
        hashSet.add(null);
        System.out.println(hashSet);
    }
}
