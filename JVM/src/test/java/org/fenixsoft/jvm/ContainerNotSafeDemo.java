package org.fenixsoft.jvm;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description 集合类不安全的问题
 * @Author zhaochao
 * @Date 2021/1/4 0:07
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        listNotSafe();
        setNoSafe();
//        mapNotSafe();
    }

    private static void mapNotSafe() {
        //Map<String,String> map=new HashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + map);
            }, String.valueOf(i)).start();
        }
    }

    private static void setNoSafe() {
        //Set<String> set=new HashSet<>();
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + set);
            }, String.valueOf(i)).start();
        }
    }

    private static void listNotSafe() {
//        List<String> list = new ArrayList<>();
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + "\t" + list);
            }, String.valueOf(i)).start();
        }
    }
}
