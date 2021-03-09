package com.zcprog.arithmetic.sort;

import java.util.Arrays;

/**
 * @Description 堆排序
 * @Author zhaochao
 * @Date 2021/3/9 1:00
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class Select_HeapSort {
    public static void main(String[] args) {
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 堆排序
     * 时间复杂度：O(n*log(n))
     * 空间复杂度：O(1)
     * 不稳定
     */
    private static void sort(int[] nums) {
//        // 一种取巧的方法：使用JDK种的PriorityQueue结构
//        PriorityQueue<Integer> heap = new PriorityQueue<>();
//        for (int num : nums) {
//            heap.add(num);
//        }
//        int i = 0;
//        while (heap.size() > 0) {
//            nums[i++] = heap.poll();
//        }
        // 1.构建大顶堆
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            // 从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(nums, i, nums.length);
        }
        // 2.调整堆结构+交换堆顶元素与末尾元素
        for (int j = nums.length - 1; j > 0; j--) {
            swap(nums, 0, j); // 将堆顶元素与末尾元素进行交换
            adjustHeap(nums, 0, j);
        }
    }

    public static void adjustHeap(int[] nums, int i, int length) {
        int temp = nums[i]; // 先取出当前元素i
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) { // 从i结点的左子结点开始，也就是2i+1处开始
            if (k + 1 < length && nums[k] < nums[k + 1]) { // 如果左子结点小于右子结点，k指向右子结点
                k++;
            }
            if (nums[k] > temp) { // 如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
                nums[i] = nums[k];
                i = k;
            } else {
                break;
            }
        }
        nums[i] = temp; // 将temp值放到最终的位置
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
