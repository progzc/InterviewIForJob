package com.zcprog.arithmetic;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Description 对象头测试
 * @Author zhaochao
 * @Date 2021/3/3 16:50
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ObjectHeadTest {
    @Test
    public void test1() {
        System.out.println(VM.current().details());
        Object obj = new Object();
        System.out.println(obj + " 十六进制哈希：" + Integer.toHexString(obj.hashCode()));
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
