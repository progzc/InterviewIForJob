package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Description 验证二叉搜索树
 * @Author zhaochao
 * @Date 2020/12/24 12:51
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0098_ValidateBinarySearchTree {
    /**
     * LeetCode地址: https://leetcode-cn.com/problems/validate-binary-search-tree/
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * 假设一个二叉搜索树具有如下特征：
     * 节点的左子树只包含小于当前节点的数。
     * 节点的右子树只包含大于当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * <p>
     * 示例 1:
     * 输入:
     * 2
     * / \
     * 1   3
     * 输出: true
     * <p>
     * 示例 2:
     * 输入:
     * 5
     * / \
     * 1   4
     * / \
     * 3   6
     * 输出: false
     * 解释: 输入为: [5,1,4,null,null,3,6]。
     * 根节点的值为 5 ，但是其右子节点值为 4 。
     */
    public static void main(String[] args) {
        Integer[][] numss = {{2, 1, 3}, {5, 1, 4, null, null, 3, 6},
                {5, 4, 6, null, null, 3, 7}, {8, 4, 10, null, null, 9, 12}};
        for (Integer[] nums : numss) {
            TreeNode root = TreeNode.init(nums);
            System.out.println(recursionSolve(root));
            System.out.println(iterationSolve(root));
        }

    }

    /**
     * 迭代: 中序遍历
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     */
    private static boolean iterationSolve(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        double inorder = -Double.MAX_VALUE;

        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 如果中序遍历得到的节点的值小于等于前一个 inorder，说明不是二叉搜索树
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }


    private static boolean recursionSolve(TreeNode root) {
        return dfs(root.left, null, null);
    }

    /**
     * 递归
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     */
    private static boolean dfs(TreeNode root, Integer lower, Integer upper) {
        if (root == null) {
            return true;
        }
        int val = root.val;
        if (lower != null && val <= lower) {
            return false;
        }
        if (upper != null && val >= upper) {
            return false;
        }
        return dfs(root.right, val, upper) && dfs(root.left, lower, val);
    }

}
