package org.fenixsoft.jvm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 原子变量类测试
 * @Author zhaochao
 * @Date 2021/1/3 23:10
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */

public class AtomicIntegerDemo {
    /**
     * CAS:Compare and swap [比较并交换]
     */
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        //true 2019
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t" + atomicInteger.get());
        //false 2019
        System.out.println(atomicInteger.compareAndSet(5, 2222) + "\t" + atomicInteger.get());
    }
}
