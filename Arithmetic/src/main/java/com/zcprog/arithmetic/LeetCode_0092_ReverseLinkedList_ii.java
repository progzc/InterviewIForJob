package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

/**
 * @Description 反转链表
 * @Author zhaochao
 * @Date 2020/12/24 0:14
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0092_ReverseLinkedList_ii {
    // 后驱节点
    private static ListNode successor = null;

    /**
     * LeetCode地址：https://leetcode-cn.com/problems/reverse-linked-list-ii/
     * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
     * <p>
     * 说明:
     * 1 ≤ m ≤ n ≤ 链表长度。
     * <p>
     * 示例:
     * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
     * 输出: 1->4->3->2->5->NULL
     */
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        ListNode head = ListNode.init(nums);
//        System.out.println(reverseBetweenSolve(head, 2, 4));
        System.out.println(recursionSolve(head, 1, 5));
    }

    /**
     * 迭代法
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    private static ListNode recursionSolve(ListNode head, int m, int n) {
        // 设置哑节点的好处：在m=1时，我们也有前驱节点，也可以将cur的next节点依次插入到pre的后面
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy;
        // 找到m的前驱节点
        for (int i = 1; i < m; ++i) {
            pre = pre.next;
        }
        ListNode cur = pre.next;
        for (int i = m; i < n; ++i) {
            // 每次循环将next节点插入到pre的后面
            ListNode next = cur.next;
            // cur将next节点后面的链表连接起来
            cur.next = next.next;
            // 将next插入到pre后面
            next.next = pre.next;
            pre.next = next;
        }
        return dummy.next;
    }

    /**
     * 比官方解法更加优雅
     * 递归法：反转位置从M到N的链表(假设M>=N，且N不超过链表长度)
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static ListNode reverseBetweenSolve(ListNode head, int m, int n) {
        if (m == 1) {
            return reverseNSolve(head, n);
        }
        head.next = reverseBetweenSolve(head.next, m - 1, n - 1);
        return head;

    }

    /**
     * 递归法：反转前N个节点的链表（假设N不超过链表长度）
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static ListNode reverseNSolve(ListNode head, int n) {
        if (n == 1) {
            // 记录第n+1个节点
            successor = head.next;
            return head;
        }
        // 以head.next为起点，需要反转前n-1个节点
        ListNode last = reverseNSolve(head.next, n - 1);
        head.next.next = head;
        // 让反转之后的head节点和后面的节点连起来
        head.next = successor;
        return last;
    }

    /**
     * 递归法：反转整个链表
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static ListNode reverseSolve(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode last = reverseSolve(head.next);
        head.next.next = head;
        head.next = null;
        return last;
    }
}
