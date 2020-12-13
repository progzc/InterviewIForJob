package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description 相交链表
 * @Author zhaochao
 * @Date 2020/12/13 18:30
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0160_IntersectionOfTwoLinkedLists {
    /**
     * LeetCode地址: https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
     * 编写一个程序，找到两个单链表相交的起始节点。
     */
    public static void main(String[] args) {
        int[] common = new int[]{1, 8, 4, 5};
        ListNode listNode1 = ListNode.init(new int[]{4});
        ListNode listNode2 = ListNode.init(new int[]{5, 0});
        ListNode[] list = ListNode.extendCommon(listNode1, listNode2, common);
        listNode1 = list[0];
        listNode2 = list[1];
        System.out.println(listNode1);
        System.out.println(listNode2);

        System.out.println("-----------哈希表解法-----------");
        ListNode listNode = hashSolve(listNode1, listNode2);
        System.out.println(listNode);

        System.out.println("-----------双指针解法-----------");
        ListNode anotherListNode = doublePointerSolve(listNode1, listNode2);
        System.out.println(anotherListNode);

    }

    /**
     * 双指针法
     * 时间复杂度：O(m+n)
     * 空间复杂度：O(1)
     */
    private static ListNode doublePointerSolve(ListNode listNode1, ListNode listNode2) {
        ListNode tempListNode1 = listNode1;
        ListNode tempListNode2 = listNode2;
        // 注意是地址相等（与ListNode的equals的方法编写有关）
        // 若是值相等，则条件为while(!Objects.equals(tempListNode1, tempListNode2))
        while (tempListNode1 != tempListNode2) {
            tempListNode1 = tempListNode1 == null ? listNode2 : tempListNode1.next;
            tempListNode2 = tempListNode2 == null ? listNode1 : tempListNode2.next;
        }
        return tempListNode1;
    }

    /**
     * 哈希表
     * 时间复杂度：O(m+n)
     * 空间复杂度：O(m)
     */
    private static ListNode hashSolve(ListNode listNode1, ListNode listNode2) {
        Set<ListNode> set = new HashSet<>();
        while (listNode1 != null) {
            set.add(listNode1);
            listNode1 = listNode1.next;
        }

        while (listNode2 != null) {
            if (set.contains(listNode2)) {
                return listNode2;
            }
            listNode2 = listNode2.next;
        }
        return null;
    }

}
