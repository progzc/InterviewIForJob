package com.progzc.singleton;

/**
 * @Description TODO
 * @Author zhaochao
 * @Date 2020/10/17 0:24
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class EnumSingletonTest {
    public static void main(String[] args) {
        EnumSingleton instance = EnumSingleton.INSTANCE;
        EnumSingleton instance1 = EnumSingleton.INSTANCE;
        System.out.println(instance == instance1); // 输出true
    }
}

enum EnumSingleton{
    INSTANCE;
    public void print(){
        System.out.println(this.hashCode());
    }
}
