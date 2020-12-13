package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 对称二叉树
 * @Author zhaochao
 * @Date 2020/12/13 20:55
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0101_SymmetricTree {
    /**
     * LeetCode地址：https://leetcode-cn.com/problems/symmetric-tree/
     * 给定一个二叉树，检查它是否是镜像对称的。
     */
    public static void main(String[] args) {
        Integer[][] nums = {
                {1, 2, 2, 3, 4, 4, 3},
                {1, 2, 2, null, 3, null, 3},
                {1, 2, 2, 3, null, null, 3}
        };

        System.out.println("----------递归算法----------");
        for (int i = 0; i < nums.length; i++) {
            TreeNode treeNode = TreeNode.init(nums[i]);
            System.out.println(treeNode + ":" + recursionResolve(treeNode));
        }

        System.out.println("----------迭代算法----------");
        for (int i = 0; i < nums.length; i++) {
            TreeNode treeNode = TreeNode.init(nums[i]);
            System.out.println(treeNode + ":" + iterationResolve(treeNode, treeNode));
        }
    }

    /**
     * 迭代算法：使用队列，可以把二叉树的递归程序改写成迭代程序
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static boolean iterationResolve(TreeNode u, TreeNode v) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(u);
        q.offer(v);
        while (!q.isEmpty()) {
            u = q.poll();
            v = q.poll();
            if (u == null && v == null) {
                continue;
            }
            if ((u == null || v == null) || (u.val != v.val)) {
                return false;
            }

            q.offer(u.left);
            q.offer(v.right);

            q.offer(u.right);
            q.offer(v.left);
        }
        return true;
    }

    /**
     * 递归算法
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static boolean recursionResolve(TreeNode treeNode) {
        return check(treeNode, treeNode);
    }

    private static boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }

}
