package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

/**
 * @Description 删除链表的倒数第N个节点
 * @Author zhaochao
 * @Date 2020/12/21 14:47
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0019_RemoveNthNodeFromEndOfList {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
     * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
     * 示例：
     * 给定一个链表: 1->2->3->4->5, 和 n = 2.
     * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
     * 说明：
     * 给定的 n 保证是有效的。
     * 进阶：
     * 你能尝试使用一趟扫描实现吗？
     */
    public static void main(String[] args) {
//        int[] nums = {1, 2, 3, 4, 5};
//        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] nums = {1, 2};
        ListNode head = ListNode.init(nums);
        System.out.println(pointerSolve(head, 1));

    }

    /**
     * 快慢指针：相比官方解答，考虑了n无效的情况
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static ListNode pointerSolve(ListNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }
        ListNode node1 = head;
        ListNode node2 = head;
        while (n != 0 && node1 != null) {
            node1 = node1.next;
            n--;
        }
        if (n > 0) {
            return head;
        }
        if (node1 == null) {
            return head.next;
        }
        while (node1.next != null) {
            node1 = node1.next;
            node2 = node2.next;
        }
        node2.next = node2.next.next;
        return head;
    }
}
