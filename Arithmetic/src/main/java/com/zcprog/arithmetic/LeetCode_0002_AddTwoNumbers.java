package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

/**
 * @Description 两数相加
 * @Author zhaochao
 * @Date 2020/12/17 19:22
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0002_AddTwoNumbers {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/add-two-numbers/
     * 给出两个非空的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * 您可以假设除了数字0之外，这两个数都不会以0开头。
     * <p>
     * 示例：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */
    public static void main(String[] args) {
//        int[] num1 = {2, 4, 3};
        int[] num1 = {0, 8, 6, 5, 6, 8, 3, 5, 7};
//        int[] num2 = {5, 6, 4};
        int[] num2 = {6, 7, 8, 0, 8, 5, 8, 9, 7};
        ListNode l1 = ListNode.init(num1);
        ListNode l2 = ListNode.init(num2);
        // 优化前
        System.out.println(traverseSolve(l1, l2));
        // 优化后
        System.out.println(traverseSolve2(l1, l2));
    }

    /**
     * 链表的遍历：优化冗余代码
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static ListNode traverseSolve2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        int carry = 0;
        ListNode result = new ListNode(carry);
        ListNode temp = result;
        while (l1 != null || l2 != null) {
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            temp.val = (val1 + val2 + carry) % 10;
            carry = (val1 + val2 + carry) / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }

            if (l1 != null || l2 != null || carry > 0) {
                temp.next = new ListNode(carry);
                temp = temp.next;
            }
        }
        return result;
    }

    /**
     * 链表的遍历
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static ListNode traverseSolve(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        int carry = 0;
        ListNode result = new ListNode(0);
        ListNode temp = result;
        while (l1 != null && l2 != null) {
            temp.val = (l1.val + l2.val + carry) % 10;
            carry = (l1.val + l2.val + carry) / 10;
            l1 = l1.next;
            l2 = l2.next;
            if (l1 != null || l2 != null || carry > 0) {
                temp.next = new ListNode(carry);
                temp = temp.next;
            }
        }
        ListNode node = l1 != null ? l1 : l2;

        while (node != null) {
            temp.val = (node.val + carry) % 10;
            carry = (node.val + carry) / 10;
            node = node.next;
            if (node != null || carry > 0) {
                temp.next = new ListNode(carry);
                temp = temp.next;
            }
        }

        return result;
    }
}
