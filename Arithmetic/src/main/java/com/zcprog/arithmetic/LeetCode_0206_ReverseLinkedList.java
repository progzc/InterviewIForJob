package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Description 反转链表
 * @Author zhaochao
 * @Date 2020/12/16 10:44
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0206_ReverseLinkedList {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/reverse-linked-list/
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     * 进阶:
     * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
     */
    public static void main(String[] args) {
        int[] num1 = {1, 2, 3, 4, 5};
        int[] num2 = {1, 2, 3, 4, 5};
        int[] num3 = {1, 2, 3, 4, 5};

        ListNode listNode1 = ListNode.init(num1);
        ListNode listNode2 = ListNode.init(num2);
        ListNode listNode3 = ListNode.init(num3);
        System.out.println(recursionResolve(listNode1));
        System.out.println(iterationResolve(listNode2));
        System.out.println(stackResolve(listNode3));

    }

    /**
     * 递归
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static ListNode recursionResolve(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode root = recursionResolve(head.next);
        head.next.next = head;
        head.next = null;
        return root;
    }

    /**
     * 迭代
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static ListNode iterationResolve(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * 栈
     * 时间复杂度：O(2n)
     * 空间复杂度：O(n)
     */
    private static ListNode stackResolve(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        Deque<ListNode> stack = new LinkedList<>();

        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        ListNode curr = null;
        ListNode root = null;
        while (stack.peek() != null) {
            ListNode node = stack.pop();
            if (curr == null) {
                curr = node;
                root = curr;
            } else {
                curr.next = node;
                curr = curr.next;
            }
        }
        curr.next = null;
        return root;
    }
}
