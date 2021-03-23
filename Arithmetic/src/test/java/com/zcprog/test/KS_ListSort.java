package com.zcprog.test;

/**
 * @Description 链表排序
 * @Author zhaochao
 * @Date 2021/3/23 15:22
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class KS_ListSort {
    public static void main(String[] args) {

        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(2);
        node1.next.next = new ListNode(2);
        node1.next.next.next = new ListNode(3);
        node1.next.next.next.next = new ListNode(5);
        node1.next.next.next.next.next = new ListNode(7);

        ListNode node2 = new ListNode(2);
        node2.next = new ListNode(4);
        node2.next.next = new ListNode(4);
        node2.next.next.next = new ListNode(5);
        node2.next.next.next.next = new ListNode(7);
        node2.next.next.next.next.next = new ListNode(9);
        ListNode ans = solve(node1, node2);
        while (ans != null) {
            System.out.print(ans.value + ">");
            ans = ans.next;
        }
    }

    public static ListNode solve(ListNode s1, ListNode s2) {
        ListNode ans;
        if (s1 == null) {
            ans = s2;
        } else if (s2 == null) {
            ans = s1;
        } else {
            ans = s1.value <= s2.value ? s1 : s2;
        }

        ListNode dummy = ans;
        while (s1 != null && s2 != null) {
            if (s1.value <= s2.value) {
                if (s1.value > ans.value) {
                    ans.next = s1;
                    ans = ans.next;
                }
                s1 = s1.next;
            } else {
                if (s2.value > ans.value) {
                    ans.next = s2;
                    ans = ans.next;
                }
                s2 = s2.next;
            }
        }
        while (s1 != null) {
            if (s1.value > ans.value) {
                ans.next = s1;
                ans = ans.next;
            }
            s1 = s1.next;
        }
        while (s2 != null) {
            if (s2.value > ans.value) {
                ans.next = s2;
                ans = ans.next;
            }
            s2 = s2.next;
        }
        return dummy;
    }

    static class ListNode {
        public int value;
        public ListNode next;

        public ListNode(int value) {
            this.value = value;
        }
    }
}
