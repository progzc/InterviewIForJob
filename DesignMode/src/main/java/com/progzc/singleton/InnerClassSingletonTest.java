package com.progzc.singleton;

/**
 * @Description TODO
 * @Author zhaochao
 * @Date 2020/10/16 23:56
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class InnerClassSingletonTest {
    public static void main(String[] args) {
        // 单线程情况
        /*InnerClassSingleton instance = InnerClassSingleton.getInstance();
        InnerClassSingleton instance1 = InnerClassSingleton.getInstance();
        System.out.println(instance == instance1);*/
        // 多线程情况
        new Thread( () -> {
            InnerClassSingleton instance = InnerClassSingleton.getInstance();
            System.out.println(instance); // 输出InnerClassSingleton@51a22f1d
        }).start();
        new Thread( () -> {
            InnerClassSingleton instance = InnerClassSingleton.getInstance();
            System.out.println(instance); // 输出InnerClassSingleton@51a22f1d
        }).start();
    }
}
class InnerClassSingleton{
    private static class InnerClassHolder{
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }
    private InnerClassSingleton(){
        if(InnerClassHolder.instance!=null){
            throw new RuntimeException("单例不允许多个实例");
        }

    }
    public static InnerClassSingleton getInstance(){
        return InnerClassHolder.instance;
    }
}
