package com.zcprog.arithmetic;

import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * @Description 实现Redis的Zset功能
 * @Author zhaochao
 * @Date 2021/3/10 18:48
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ZsetTest {

    @Test
    public void test1() {
        // 相同的元素会被添加进去
        PriorityQueue<Data> ans = new PriorityQueue<>(new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.score - o2.score;
            }
        });
        ans.add(new Data(5, "韩梅梅", "语文"));
        ans.add(new Data(2, "张三", "数学"));
        ans.add(new Data(2, "李四", "英语"));
        ans.add(new Data(3, "韩涛", "化学"));
        ans.add(new Data(1, "小明", "物理"));
        while (!ans.isEmpty()) {
            System.out.println(ans.poll());
        }
    }

    @Test
    public void test2() {
        // 相同的元素不会被添加进去
        TreeSet<Data> ans = new TreeSet<>(new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.score - o2.score;
            }
        });
        ans.add(new Data(5, "韩梅梅", "语文"));
        ans.add(new Data(2, "张三", "数学"));
        ans.add(new Data(2, "李四", "英语"));
        ans.add(new Data(3, "韩涛", "化学"));
        ans.add(new Data(1, "小明", "物理"));
        while (!ans.isEmpty()) {
            System.out.println(ans.pollFirst());
        }
    }
}

class Data {
    public int score;
    public String key;
    public String value;

    public Data(int score, String key, String value) {
        this.score = score;
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data{" +
                "score=" + score +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
