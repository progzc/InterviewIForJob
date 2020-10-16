package com.progzc.singleton;

/**
 * @Description TODO
 * @Author zhaochao
 * @Date 2020/10/16 23:28
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class HungrySingletonTest {
    public static void main(String[] args) {
        HungrySingleton instance = HungrySingleton.getInstance();
        HungrySingleton instance1 = HungrySingleton.getInstance();
        System.out.println(instance == instance1); // 输出true
    }
}
class HungrySingleton{
    private static HungrySingleton instance = new HungrySingleton();
    private HungrySingleton(){

    }
    public static HungrySingleton getInstance(){
        return instance;
    }
}
