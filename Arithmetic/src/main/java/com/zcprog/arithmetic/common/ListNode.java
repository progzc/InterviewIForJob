package com.zcprog.arithmetic.common;

import java.util.Objects;

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

    /**
     * 根据数组生成链表
     * @param nums
     * @return
     */
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

    /**
     * 扩展相交部分
     * @param listNode1
     * @param listNode2
     * @param nums
     * @return
     */
    public static ListNode[] extendCommon(ListNode listNode1, ListNode listNode2, int[] nums) {
        ListNode[] list = new ListNode[2];
        ListNode commonListNode = init(nums);
        if (commonListNode != null) {
            listNode1.next = commonListNode;
            listNode2.next = commonListNode;
        }
        list[0] = listNode1;
        list[1] = listNode2;
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListNode listNode = (ListNode) o;
        return val == listNode.val && Objects.equals(next, listNode.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, next);
    }

    @Override
    public String toString() {
        return val + (next != null ? ("->" + next.toString()) : "");
    }
}
