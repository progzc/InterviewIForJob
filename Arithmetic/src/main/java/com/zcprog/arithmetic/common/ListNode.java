package com.zcprog.arithmetic.common;

/**
 * @Description 链表节点
 * @Author zhaochao
 * @Date 2020/12/13 17:14
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode init(int[] nums) {
        if (nums == null || nums.length < 1) {
            return null;
        }
        ListNode listNode = new ListNode(nums[0]);
        ListNode listTemp = listNode;
        for (int i = 0; i < nums.length - 1; i++) {
            listTemp.next = new ListNode(nums[i + 1]);
            listTemp = listTemp.next;
        }
        return listNode;
    }

    @Override
    public String toString() {
        return val + (next != null ? ("->" + next.toString()) : "");
    }
}
