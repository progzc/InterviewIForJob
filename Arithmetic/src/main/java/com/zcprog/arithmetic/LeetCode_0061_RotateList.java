package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

/**
 * @Description 旋转链表
 * @Author zhaochao
 * @Date 2020/12/22 21:29
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0061_RotateList {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/rotate-list/
     * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
     * <p>
     * 示例1：
     * 输入: 1->2->3->4->5->NULL, k = 2
     * 输出: 4->5->1->2->3->NULL
     * 解释:
     * 向右旋转 1 步: 5->1->2->3->4->NULL
     * 向右旋转 2 步: 4->5->1->2->3->NULL
     * <p>
     * 示例2：
     * 输入: 0->1->2->NULL, k = 4
     * 输出: 2->0->1->NULL
     * 解释:
     * 向右旋转 1 步: 2->0->1->NULL
     * 向右旋转 2 步: 1->2->0->NULL
     * 向右旋转 3 步: 0->1->2->NULL
     * 向右旋转 4 步: 2->0->1->NULL
     */
    public static void main(String[] args) {
        int[][] numss = {{1, 2, 3, 4, 5}, {0, 1, 2}, {2, 1}};
        int[] ks = {2, 4, 2};

        for (int i = 0; i < numss.length; i++) {
            ListNode head = ListNode.init(numss[i]);
            System.out.println(traverseSolve(head, ks[i]));
        }
    }

    /**
     * 链表的遍历
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static ListNode traverseSolve(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node = new ListNode(0, head);
        ListNode temp = node;
        int length = 0;
        while (temp.next != null) {
            length++;
            temp = temp.next;
        }
        temp.next = head;
        int move = length - k % length;
        while (move > 0) {
            node = node.next;
            move--;
        }
        temp = node.next;
        node.next = null;
        return temp;
    }
}
