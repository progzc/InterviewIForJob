package com.zcprog.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Description 合并空间
 * @Author zhaochao
 * @Date 2020/12/22 16:40
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0056_MergeIntervals {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/merge-intervals/
     * 给出一个区间的集合，请合并所有重叠的区间。
     * <p>
     * 示例 1:
     * 输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出: [[1,6],[8,10],[15,18]]
     * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * <p>
     * 示例 2:
     * 输入: intervals = [[1,4],[4,5]]
     * 输出: [[1,5]]
     * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
     * 注意：输入类型已于2019年4月15日更改。 请重置默认代码定义以获取新方法签名。
     * <p>
     * 提示：
     * intervals[i][0] <= intervals[i][1]
     */
    public static void main(String[] args) {
//        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] intervals = {{1, 3}, {15, 18}, {8, 10}, {2, 6}};
//        int[][] intervals = {{1, 4}, {4, 5}};
        int[][] result = solve(intervals);
        for (int[] e : result) {
            System.out.println(Arrays.toString(e));
        }
    }

    private static int[][] solve(int[][] intervals) {
        if (intervals == null) {
            return null;
        }
        if (intervals.length == 0) {
            return new int[0][2];
        }
        // 1. 排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });
        // 2. 合并
        List<int[]> merged = new ArrayList<int[]>();
        for (int i = 0; i < intervals.length; ++i) {
            int left = intervals[i][0];
            int right = intervals[i][1];
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < left) {
                merged.add(new int[]{left, right});
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], right);
            }
        }
        // 3. 链表转化为数组
        return merged.toArray(new int[merged.size()][]);
    }
}
