package com.zcprog.basic;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @Description 创建不可变对象
 * @Author zhaochao
 * @Date 2021/1/30 23:37
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public final class FinalClassDemo {
    private final int id;
    private final String name;
    private final HashMap<String, String> testMap;

    /**
     * 实现深拷贝(deep copy)的构造器
     */
    public FinalClassDemo(int i, String n, HashMap<String, String> hm) {
        System.out.println("Performing Deep Copy for Object initialization");
        this.id = i;
        this.name = n;
        HashMap<String, String> tempMap = new HashMap<>();
        String key;
        Iterator<String> it = hm.keySet().iterator();
        while (it.hasNext()) {
            key = it.next();
            tempMap.put(key, hm.get(key));
        }
        this.testMap = tempMap;
    }

//    /**
//     * 实现浅拷贝的构造器
//     */
//    public FinalClassDemo(int i, String n, HashMap<String, String> hm) {
//        System.out.println("Performing Shallow Copy for Object initialization");
//        this.id = i;
//        this.name = n;
//        this.testMap = hm;
//    }

    /**
     * 测试浅拷贝的结果
     * 为了创建不可变类，要使用深拷贝
     * @param args
     */
    public static void main(String[] args) {
        HashMap<String, String> h1 = new HashMap<>();
        h1.put("1", "first");
        h1.put("2", "second");
        String s = "original";
        int i = 10;
        FinalClassDemo ce = new FinalClassDemo(i, s, h1);

        //Lets see whether its copy by field or reference
        System.out.println(s == ce.getName());
        System.out.println(h1 == ce.getTestMap());
        //print the ce values
        System.out.println("ce id:" + ce.getId());
        System.out.println("ce name:" + ce.getName());
        System.out.println("ce testMap:" + ce.getTestMap());
        //change the local variable values
        i = 20;
        s = "modified";
        h1.put("3", "third");
        //print the values again
        System.out.println("ce id after local variable change:" + ce.getId());
        System.out.println("ce name after local variable change:" + ce.getName());
        System.out.println("ce testMap after local variable change:" + ce.getTestMap());

        HashMap<String, String> hmTest = ce.getTestMap();
        hmTest.put("4", "new");

        System.out.println("ce testMap after changing variable from accessor methods:" + ce.getTestMap());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, String> getTestMap() {
//        return testMap;
        return (HashMap<String, String>) testMap.clone();
    }
}
