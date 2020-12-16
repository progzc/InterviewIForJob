package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Description 翻转二叉树
 * @Author zhaochao
 * @Date 2020/12/16 15:14
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0226_InvertBinaryTree {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/invert-binary-tree/
     * 翻转一棵二叉树。
     */
    public static void main(String[] args) {
        Integer[] nums1 = {4, 2, 7, 1, 3, 6, 9};
        Integer[] nums2 = {4, 2, 7, 1, 3, 6, 9};
        TreeNode root1 = TreeNode.init(nums1);
        TreeNode root2 = TreeNode.init(nums2);
        System.out.println(recursionSolve(root1));
        System.out.println(iterationSolve(root2));
    }

    /**
     * 迭代
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static TreeNode iterationSolve(TreeNode root) {
        if (root == null) {
            return null;
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            TreeNode node = deque.poll();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.right != null) {
                deque.offer(node.right);
            }
            if (node.left != null) {
                deque.offer(node.left);
            }

        }
        return root;
    }

    /**
     * 递归
     * 时间复杂度：O(n)
     * 空间复杂度：O(log(n));若树退化为链状，则为O(n)
     */
    private static TreeNode recursionSolve(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode leftNode = recursionSolve(root.left);
        TreeNode rightNode = recursionSolve(root.right);
        root.left = rightNode;
        root.right = leftNode;
        return root;
    }

}
