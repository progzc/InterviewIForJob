package com.zcprog.arithmetic.common;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 二叉树节点
 * @Author zhaochao
 * @Date 2020/12/13 21:27
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode(int x) {
        this.val = x;
    }

    /**
     * 根据数组构建二叉树
     * @param nums
     * @return
     */
    public static TreeNode init(Integer[] nums) {
        if (nums == null || nums.length < 1 || nums[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(nums[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        for (int i = 0; i < nums.length - 2; ) {
            if (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node != null) {
                    node.left = nums[i + 1] != null ? new TreeNode(nums[i + 1]) : null;
                    queue.offer(node.left);
                    node.right = nums[i + 2] != null ? new TreeNode(nums[i + 2]) : null;
                    queue.offer(node.right);
                }
            }
            i += 2;
        }

        return root;
    }

    @Override
    public String toString() {
        return "[" + levelIterator(this) + "]";
    }

    /**
     * 层次遍历
     * @param treeNode
     * @return
     */
    private String levelIterator(TreeNode treeNode) {
        if (treeNode == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(treeNode);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                sb.append(node.val).append(",");
                if (node.left != null || node.right != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            } else {
                sb.append("null").append(",");
            }
        }
        return sb.toString().replaceFirst("[,]$", "");
    }

}
