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

    public TreeNode(int x) {
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
                    node.left = nums[i + 1] != null ? new TreeNode(nums[i + 1]) : new TreeNode(Integer.MIN_VALUE);
                    queue.offer(node.left);
                    node.right = nums[i + 2] != null ? new TreeNode(nums[i + 2]) : new TreeNode(Integer.MIN_VALUE);
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
     * 层次遍历: BFS
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

        Queue<TreeNode> temp = new LinkedList<>();
        sb.append(treeNode.val).append(",");

        while (!queue.isEmpty()) {
            // 当前层的节点数
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                    temp.offer(node.left);
                } else {
                    temp.offer(null);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    temp.offer(node.right);
                } else {
                    temp.offer(null);
                }
                size--;
            }
            // 打印每一层的节点
            if (!queue.isEmpty()) {
                while (!temp.isEmpty()) {
                    TreeNode tempNode = temp.poll();
                    if (tempNode != null) {
                        sb.append(tempNode.val).append(",");
                    } else {
                        sb.append("null").append(",");
                    }
                }
            }
        }
        return sb.toString().replaceFirst("[,]$", "").replaceAll(Integer.MIN_VALUE + "", "null");
    }
}
