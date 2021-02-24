package com.zcprog.hw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Description 根据身高重建队列
 * @Author zhaochao
 * @Date 2021/2/24 19:33
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0406_QueueReconstructionByHeight {
    public static void main(String[] args) {
        int[][] people = {{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}};
        int[][] results = reconstructQueue(people);
        for (int i = 0; i < results.length; i++) {
            System.out.println(Arrays.toString(results[i]));
        }
    }

    public static int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] person1, int[] person2) {
                if (person1[0] != person2[0]) {
                    return person2[0] - person1[0];
                } else {
                    return person1[1] - person2[1];
                }
            }
        });
        List<int[]> ans = new ArrayList<int[]>();
        for (int[] person : people) {
            ans.add(person[1], person);
        }
        return ans.toArray(new int[ans.size()][0]);
    }
}
