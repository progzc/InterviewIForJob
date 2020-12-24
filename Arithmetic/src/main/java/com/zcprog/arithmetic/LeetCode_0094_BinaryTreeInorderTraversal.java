package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description 二叉树的中序遍历
 * @Author zhaochao
 * @Date 2020/12/24 11:33
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0094_BinaryTreeInorderTraversal {
    /**
     * LeetCode地址: https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
     * 给定一个二叉树，返回它的中序 遍历。
     * <p>
     * 示例:
     * 输入: [1,null,2,3]
     * 1
     * \
     * 2
     * /
     * 3
     * 输出: [1,3,2]
     * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
     */
    public static void main(String[] args) {
        Integer[] nums = {1, null, 2, null, null, 3, null};
        TreeNode root = TreeNode.init(nums);
        System.out.println(recursionSolve(root));
        System.out.println(iterationSolve(root));
    }

    /**
     * 迭代
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     */
    private static List<Integer> iterationSolve(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            ans.add(root.val);
            root = root.right;
        }
        return ans;
    }

    /**
     * 递归
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     */
    private static List<Integer> recursionSolve(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        dfs(root, ans);
        return ans;
    }

    private static void dfs(TreeNode root, List<Integer> ans) {
        // root.val == Integer.MIN_VALUE是基于本项目中TreeNode的具体实现而来的
        if (root == null || root.val == Integer.MIN_VALUE) {
            return;
        }
        dfs(root.left, ans);
        ans.add(root.val);
        dfs(root.right, ans);
    }
}
