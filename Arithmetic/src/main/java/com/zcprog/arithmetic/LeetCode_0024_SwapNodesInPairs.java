package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

/**
 * @Description 两两交换链表中的节点
 * @Author zhaochao
 * @Date 2020/12/21 16:31
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0024_SwapNodesInPairs {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/swap-nodes-in-pairs/
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 示例 1：
     * 输入：head = [1,2,3,4]
     * 输出：[2,1,4,3]
     * <p>
     * 示例 2：
     * 输入：head = []
     * 输出：[]
     * <p>
     * 示例 3：
     * 输入：head = [1]
     * 输出：[1]
     * <p>
     * 提示：
     * 链表中节点的数目在范围 [0, 100] 内
     * 0 <= Node.val <= 100
     */
    public static void main(String[] args) {
        int[][] heaps = {{1, 2, 3, 4}, {}, {1}};
        for (int[] heap : heaps) {
            ListNode node = ListNode.init(heap);
            System.out.println(recursionSolve(node));
        }
    }

    /**
     * 递归
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static ListNode recursionSolve(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node1 = head.next;
        head.next = recursionSolve(node1.next);
        node1.next = head;
        return node1;
    }

    /**
     * 迭代
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static ListNode iterationSolve(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode temp = dummyHead;
        while (temp.next != null && temp.next.next != null) {
            ListNode node1 = temp.next;
            ListNode node2 = temp.next.next;
            temp.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            temp = node1;
        }
        return dummyHead.next;
    }
}
