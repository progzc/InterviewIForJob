package com.zcprog.hw;

import java.util.*;

/**
 * @Description 滑动窗口的最大值
 * @Author zhaochao
 * @Date 2021/3/21 15:29
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0239_SlidingWindowMaximum {
    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        int[] ans = maxSlidingWindow2(nums, k);
        System.out.println(Arrays.toString(ans));
    }

    /**
     * 优先队列法
     */
    private static int[] maxSlidingWindow1(int[] nums, int k) {
        int len = nums.length;
        int N = len - k + 1;
        int[] ans = new int[N];
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] != o2[0] ? o2[0] - o1[0] : o2[1] - o1[1];
            }
        });
        for (int i = 0; i < k; i++) {
            queue.offer(new int[]{nums[i], i});
        }
        ans[0] = queue.peek()[0];
        for (int i = k; i < len; i++) {
            queue.offer(new int[]{nums[i], i});
            while (queue.peek()[1] <= i - k) {
                queue.poll();
            }
            ans[i - k + 1] = queue.peek()[0];
        }
        return ans;
    }

    /**
     * 单调栈法
     */
    private static int[] maxSlidingWindow2(int[] nums, int k) {
        int len = nums.length;
        int N = len - k + 1;
        int[] ans = new int[N];
        Deque<Integer> deque = new LinkedList<Integer>();
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        ans[0] = nums[deque.peekFirst()];
        for (int i = k; i < len; i++) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            ans[i - k + 1] = nums[deque.peekFirst()];
        }
        return ans;
    }
}
