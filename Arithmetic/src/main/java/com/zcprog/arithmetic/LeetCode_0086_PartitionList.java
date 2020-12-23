package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

/**
 * @Description 分隔链表
 * @Author zhaochao
 * @Date 2020/12/23 18:53
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0086_PartitionList {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/partition-list/
     * 给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
     * 你应当保留两个分区中每个节点的初始相对位置。
     * <p>
     * 示例:
     * 输入: head = 1->4->3->2->5->2, x = 3
     * 输出: 1->2->2->4->3->5
     */
    public static void main(String[] args) {
//        int[] nums = {4, 3, 2, 5, 2};
        int[] nums = {1, 4, 3, 2, 5, 2};
        int x = 3;
        ListNode head = ListNode.init(nums);
//        System.out.println(traverseSolve(head, x));
        System.out.println(traverseSolve2(head, x));

    }

    /**
     * 哑节点：官方解法的思路更加优雅（拆分成两个链表，再合并）
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static ListNode traverseSolve2(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        // 哑节点1
        ListNode before_head = new ListNode(0);
        ListNode before = before_head;
        // 哑节点2
        ListNode after_head = new ListNode(0);
        ListNode after = after_head;
        // 遍历head链表
        while (head != null) {
            if (head.val < x) {
                before.next = head;
                before = before.next;
            } else {
                after.next = head;
                after = after.next;
            }
            head = head.next;
        }
        after.next = null;
        before.next = after_head.next;

        return before_head.next;
    }

    /**
     * 哑节点
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static ListNode traverseSolve(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummyNode = new ListNode(Integer.MIN_VALUE, head);
        ListNode prevMin = dummyNode;
        ListNode prev = dummyNode;
        ListNode node = head;

        while (node != null) {
            while (node != null && node.val >= x) {
                node = node.next;
                prev = prev.next;
            }
            if (node != null) {
                if (prevMin.next != node) {
                    prev.next = node.next;
                    node.next = prevMin.next;
                    prevMin.next = node;
                    prevMin = node;
                    node = prev.next;
                } else {
                    prevMin = node;
                    prev = node;
                    node = node.next;
                }
            }
        }
        return dummyNode.next;
    }
}
