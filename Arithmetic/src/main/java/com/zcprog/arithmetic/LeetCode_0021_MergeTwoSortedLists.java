package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

/**
 * @Description 合并两个有序链表
 * @Author zhaochao
 * @Date 2020/12/13 16:48
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0021_MergeTwoSortedLists {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/merge-two-sorted-lists
     * 将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * <p>
     * 示例：
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     */
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2, 4};
        int[] nums2 = new int[]{1, 3, 4};

        ListNode listNode1 = ListNode.init(nums1);
        ListNode listNode2 = ListNode.init(nums2);

        System.out.println(listNode1);
        System.out.println(listNode2);

        System.out.println("----------合并结果---------");
        ListNode listNode = recursionSolve(listNode1, listNode2);
        System.out.println(listNode);
    }

    /**
     * 递归
     * list1[0]+merge(list1[1:],list2) list1[0]<list2[0]
     * list2[0]+merge(list1,list2[1:]) otherwise
     * <p>
     * 时间复杂度：O(n+m)
     * 空间复杂度：O(n+m)
     */
    private static ListNode recursionSolve(ListNode listNode1, ListNode listNode2) {
        if (listNode1 == null) {
            return listNode2;
        } else if (listNode2 == null) {
            return listNode1;
        } else if (listNode1.val < listNode2.val) {
            listNode1.next = recursionSolve(listNode1.next, listNode2);
            return listNode1;
        } else {
            listNode2.next = recursionSolve(listNode1, listNode2.next);
            return listNode2;
        }
    }
}
