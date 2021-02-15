package com.zcprog.arithmetic;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Description 自定义比较器
 * @Author zhaochao
 * @Date 2021/2/15 23:49
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ComparatorTest {
    public static void main(String[] args) {
        int[] nums = {3, 30, 34, 5, 9};
        System.out.println(minNumber(nums));
    }

    public static String minNumber(int[] nums) {
        // 先要将int[]转换为Integer[]
        Integer[] integers = Arrays.stream(nums).boxed().toArray(Integer[]::new);
        Arrays.sort(integers, new Comparator<Integer>() {
            @Override
            public int compare(Integer num1, Integer num2) {
                String s1 = Integer.toString(num1);
                String s2 = Integer.toString(num2);
                int length1 = s1.length();
                int length2 = s2.length();
                int minLength = Math.min(length1, length2);
                for (int i = 0; i < minLength; i++) {
                    char ch1 = s1.charAt(length1 - 1 - i);
                    char ch2 = s2.charAt(length2 - 1 - i);
                    if (ch1 == ch2) {
                        continue;
                    } else if (ch1 > ch2) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return s1.length() - s2.length();
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int num : integers) {
            sb.append(num);
        }
        return sb.toString();
    }
}
