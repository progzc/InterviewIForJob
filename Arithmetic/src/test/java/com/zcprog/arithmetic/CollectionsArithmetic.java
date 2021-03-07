package com.zcprog.arithmetic;

import org.junit.Test;

import java.util.*;

/**
 * @Description Collections工具类提供的算法
 * @Author zhaochao
 * @Date 2021/3/7 22:32
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CollectionsArithmetic {
    @Test
    public void sortPos() {
        List<String> list = Arrays.asList("7", "4", "8", "3", "9");
        // 升序
        Collections.sort(list);
        System.out.println(list); // [3, 4, 7, 8, 9]
    }

    @Test
    public void sortNeg() {
        List<String> list = Arrays.asList("7", "4", "8", "3", "9");
        // 降序
        Collections.sort(list, (String o1, String o2) -> {
            return o2.compareTo(o1);
        });
        // 降序的另一种写法
//        Collections.sort(list, Comparator.reverseOrder());
        System.out.println(list); // [9, 8, 7, 4, 3]
    }

    @Test
    public void sortRevs() {
        List<String> list = Arrays.asList("7", "4", "8", "3", "9");
        // 降序的另一种写法
        Collections.sort(list, Comparator.reverseOrder());
        System.out.println(list); // [9, 8, 7, 4, 3]
    }

    @Test
    public void sortBin() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 二分排序
        int index = Collections.binarySearch(list, "5");
        System.out.println(index);
    }

    @Test
    public void sortShuffle() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 洗牌算法
        Collections.shuffle(list);
        System.out.println(list);
    }

    @Test
    public void sortShuffle2() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 洗牌算法
        Collections.shuffle(list, new Random(100));
        System.out.println(list);
    }

    @Test
    public void sortShuffle3() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 洗牌算法(自己实现)
        Random random = new Random();
        for (int i = list.size(); i > 1; i--) {
            int ri = random.nextInt(i); // 随机位置
            int ji = i - 1;
            list.set(ji, list.set(ri, list.get(ji))); // 元素置换
        }
        System.out.println(list);
    }

    @Test
    public void rotate() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 顺时针旋转算法
        Collections.rotate(list, 2);
        System.out.println(list); // [8, 9, 1, 2, 3, 4, 5, 6, 7]
    }

    @Test
    public void rotate2() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 逆时针旋转算法
        Collections.rotate(list, -2);
        System.out.println(list); // [3, 4, 5, 6, 7, 8, 9, 1, 2]
    }

    @Test
    public void min() {
        // 最小值
        String min = Collections.min(Arrays.asList("5", "2", "1", "3"));
        System.out.println(min); // 1
    }

    @Test
    public void max() {
        // 最大值
        String max = Collections.max(Arrays.asList("5", "2", "1", "3"));
        System.out.println(max); // 5
    }

    @Test
    public void replace() {
        List<String> list = Arrays.asList("7", "4", "8", "8");
        // 元素替换
        Collections.replaceAll(list, "8", "9");
        System.out.println(list); // [7, 4, 9, 9]
    }

    @Test
    public void subOfIndex() {
        List<String> list = Arrays.asList("7", "4", "8", "3", "9");
        // 连续集合位置判断
        int idx = Collections.indexOfSubList(list, Arrays.asList("8", "3"));
        System.out.println(idx);
    }
}
