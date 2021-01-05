package org.fenixsoft.jvm;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @Description WeakHashMap的使用测试
 * @Author zhaochao
 * @Date 2021/1/5 22:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class WeakHashMapDemo {
    public static void main(String[] args) {
        myHashMap();
        System.out.println("===============");
        myWeakHashMap();
    }

    private static void myHashMap() {
        HashMap<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";
        map.put(key, value);
        System.out.println(map); // {1=HashMap}

        key = null;
        System.out.println(map); // {1=HashMap}
        System.gc();
        System.out.println(map + "\t" + map.size()); // {1=HashMap}	1
    }

    private static void myWeakHashMap() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(1);
        Integer key2 = new Integer(2);
        String value = "WeakHashMap";
        String value2 = "WeakHashMap2";
        map.put(key, value);
        map.put(key2, value2);
        map.put(new Integer(3), "WeakHashMap3");
        System.out.println(map); // {3=WeakHashMap3, 2=WeakHashMap2, 1=WeakHashMap}

        key = null;
        System.out.println(map); // {3=WeakHashMap3, 2=WeakHashMap2, 1=WeakHashMap}

        System.gc();
        System.out.println(map + "\t" + map.size()); // {2=WeakHashMap2}	1
    }
}
