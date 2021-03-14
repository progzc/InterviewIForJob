package com.zcprog.hw;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @Description 数组中的第K个最大元素
 * @Author zhaochao
 * @Date 2021/3/14 10:22
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0215_KthLargestElementInAnArray {
    public static void main(String[] args) {
//        int[] nums = {3, 2, 1, 5, 6, 4};
//        int k = 2;
        int[] nums = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k = 4;
        System.out.println(findKthLargest4(nums, k));
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 排序
     * 时间时间度：O(n*log(n))
     * 空间复杂度：O(log(n))
     */
    public static int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 自己实现堆
     * 时间复杂度: O(k*log(n))
     * 空间复杂度：O(n)
     */
    public static int findKthLargest4(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
            swap(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    // 创建一个大顶堆
    public static void buildMaxHeap(int[] a, int heapSize) {
        for (int i = heapSize / 2 - 1; i >= 0; --i) {
            maxHeapify(a, i, heapSize);
        }
    }

    private static void maxHeapify(int[] a, int i, int heapSize) {
        int l = i * 2 + 1;
        int r = i * 2 + 2;
        int largest = i;
        if (l < heapSize && a[l] > a[largest]) {
            largest = l;
        }
        if (r < heapSize && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(a, i, largest);
            maxHeapify(a, largest, heapSize);
        }
    }

    /**
     * 借助于PriorityQueue实现堆
     * 时间复杂度: O(n*log(n))
     * 空间复杂度：O(n)
     */
    public static int findKthLargest3(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(nums.length, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int num : nums) {
            heap.add(num);
        }
        int ans = 0;
        while (k > 0 && !heap.isEmpty()) {
            ans = heap.poll();
            k--;
        }
        return k > 0 ? Integer.MIN_VALUE : ans;
    }

    /**
     * 这种方法比较难
     * 排序优化（采用分治法）：O(n)
     * 时间时间度：O(n)
     * 空间复杂度：O(log(n))
     */
    public static int findKthLargest2(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public static int quickSelect(int[] a, int l, int r, int index) {
        int q = randomPartition(a, l, r);
        if (q == index) {
            return a[q];
        } else {
            return q < index ? quickSelect(a, q + 1, r, index) : quickSelect(a, l, q - 1, index);
        }
    }

    public static int randomPartition(int[] a, int l, int r) {
        int i = new Random().nextInt(r - l + 1) + l;
        swap(a, i, r);
        return partition(a, l, r);
    }

    private static int partition(int[] a, int l, int r) {
        int x = a[r], i = l - 1;
        for (int j = l; j < r; ++j) {
            if (a[j] <= x) {
                swap(a, ++i, j);
            }
        }
        swap(a, i + 1, r);
        return i + 1;
    }

    public static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
