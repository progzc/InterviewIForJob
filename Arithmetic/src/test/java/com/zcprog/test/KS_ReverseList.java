package com.zcprog.test;

/**
 * @Description 快手-反转链表
 * @Author zhaochao
 * @Date 2021/4/2 17:27
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class KS_ReverseList {
    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(3);
        root.next.next.next = new ListNode(4);
        root.next.next.next.next = new ListNode(5);
        ListNode ans = reverse2(root);
        while (ans != null) {
            System.out.println(ans.val);
            ans = ans.next;
        }
    }

    /**
     * 迭代解法
     */
    public static ListNode reverse(ListNode root) {
        if (root == null) return root;
        ListNode pre = root;
        ListNode cur = root.next;
        ListNode dummy = null;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre.next = dummy;
            dummy = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    /**
     * 递归解法
     */
    public static ListNode reverse2(ListNode root) {
        if (root == null || root.next == null) return root;
        ListNode next = reverse2(root.next);
        root.next.next = root;
        root.next = null;
        return next;
    }

    static class ListNode {
        private int val;
        private ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
