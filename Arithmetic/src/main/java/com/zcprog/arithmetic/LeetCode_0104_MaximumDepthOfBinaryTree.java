package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 二叉树的最大深度
 * @Author zhaochao
 * @Date 2020/12/14 8:30
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0104_MaximumDepthOfBinaryTree {

    /**
     * LeetCode地址：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/description/
     * 给定一个二叉树，找出其最大深度。
     * 说明: 叶子节点是指没有子节点的节点。
     */
    public static void main(String[] args) {
//        Integer[] num = {3, 9, 20, null, null, 15, 7};
        Integer[] num = {1, null, 2, null, null, null, 3, null, null, null, null, null, null, null, 4};
        TreeNode treeNode = TreeNode.init(num);

        System.out.println(treeNode);

        System.out.println("------------递归算法---------");
        System.out.println("树的深度：" + maxDepthRecursionSolve(treeNode));

        System.out.println("------------BFS算法---------");
        System.out.println("树的深度：" + maxDepthIterationSolve(treeNode));
    }

    /**
     * 递归：DFS
     * 条件：max(l,r)+1
     * 时间复杂度：O(n)
     * 空间复杂度：O(height)
     */
    private static int maxDepthRecursionSolve(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = maxDepthRecursionSolve(root.left);
            int rightHeight = maxDepthRecursionSolve(root.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    /**
     * 迭代：BFS
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int maxDepthIterationSolve(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            // 当前层的节点数
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                size--;
            }
            ans++;
        }
        return ans;
    }
}
