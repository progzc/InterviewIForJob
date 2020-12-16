package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

/**
 * @Description 移除链表元素
 * @Author zhaochao
 * @Date 2020/12/16 9:18
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0203_RemoveLinkedListElements {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/remove-linked-list-elements/
     * 删除链表中等于给定值 val 的所有节点。
     * <p>
     * 示例:
     * 输入: 1->2->6->3->4->5->6, val = 6
     * 输出: 1->2->3->4->5
     */
    public static void main(String[] args) {
        int[] num = {1, 2, 6, 3, 4, 5, 6};
        ListNode listNode = ListNode.init(num);
        System.out.println(removeElements(listNode, 6));
        System.out.println(guardResolve(listNode, 6));
    }

    /**
     * 链表遍历
     * 思路：
     * 1.先找到第一个不等于6的节点，此即为返回的头节点
     * 2.找到下一个不等于6的节点，将当前节点的下一个节点指向该节点
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     */
    private static ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        while (head.val == val) {
            if (head.next != null) {
                head = head.next;
            } else {
                return null;
            }
        }
        ListNode root = head;
        while (head != null && head.next != null) {
            ListNode temp = head.next;
            while (temp != null && temp.val == val) {
                if (temp.next != null) {
                    temp = temp.next;
                } else {
                    temp = null;
                }
            }
            head.next = temp;
            head = head.next;
        }
        return root;
    }

    /**
     * 哨兵节点
     * 时间复杂度O(n)
     * 空间复杂度O(1)
     */
    private static ListNode guardResolve(ListNode head, int val) {
        ListNode sentinel = new ListNode(Integer.MIN_VALUE);
        sentinel.next = head;

        ListNode prev = sentinel;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == val) {
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }
        return sentinel.next;
    }
}
