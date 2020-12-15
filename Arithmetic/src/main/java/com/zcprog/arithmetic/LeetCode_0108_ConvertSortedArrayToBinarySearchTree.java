package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.TreeNode;

import java.util.Random;

/**
 * @Description 将有序数组转换为二叉搜索树
 * @Author zhaochao
 * @Date 2020/12/14 22:36
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0108_ConvertSortedArrayToBinarySearchTree {

    public static final Random random = new Random();

    /**
     * LeetCode地址：https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     * 实例：
     * 给定有序数组: [-10,-3,0,5,9], 一个可能的答案是：[0,-3,9,-10,null,5]
     * <p>
     * AVL树: 一种高度平衡的(height balanced)二叉搜索树：对每一个结点x，x的左子树与右子树的高度差(平衡因子)至多为1。
     * (1) AVL树基本操作的最坏时间复杂度要比普通的二叉搜索树低—— 除去可能的插入操作外（我们将假设懒惰删除），它是O(log(n))。
     * (2) 插入操作隐含着困难的原因在于，插入一个节点可能破坏AVL树的性质.
     * 如果发生这种情况，就要在插入操作结束之前恢复平衡的性质。事实上，这总可以通过对树进行简单的修正来做到，我们称其为旋转。
     */
    public static void main(String[] args) {
        int[] nums = {-10, -3, 0, 5, 9};
        System.out.println("--------中序遍历（选择中间位置左边的数字作为根节点）-------");
        TreeNode root1 = ldrSolve(nums, 0, nums.length - 1);
        System.out.println(root1);

        System.out.println("--------中序遍历（选择中间位置右边的数字作为根节点）-------");
        TreeNode root2 = ldrSolve2(nums, 0, nums.length - 1);
        System.out.println(root2);

        System.out.println("--------中序遍历（选择中间位置右边的数字作为根节点）-------");
        TreeNode root3 = ldrSolve3(nums, 0, nums.length - 1);
        System.out.println(root3);
    }

    /**
     * LDR: 选择中间位置左边的数字作为根节点
     * 时间复杂度：O(n)
     * 空间复杂度：O(log(n))
     */
    private static TreeNode ldrSolve(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        // 总是选择中间位置左边的数字作为根节点
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = ldrSolve(nums, left, mid - 1);
        root.right = ldrSolve(nums, mid + 1, right);
        return root;
    }

    /**
     * LDR: 选择中间位置右边的数字作为根节点
     * 时间复杂度：O(n)
     * 空间复杂度：O(log(n))
     */
    private static TreeNode ldrSolve2(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        // 总是选择中间位置左边的数字作为根节点
        int mid = (left + right + 1) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = ldrSolve2(nums, left, mid - 1);
        root.right = ldrSolve2(nums, mid + 1, right);
        return root;
    }

    /**
     * LDR: 选择任意一个中间位置数字作为根节点
     * 时间复杂度：O(n)
     * 空间复杂度：O(log(n))
     */
    private static TreeNode ldrSolve3(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        // 总是选择中间位置左边的数字作为根节点
        int mid = (left + right + random.nextInt(2)) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = ldrSolve3(nums, left, mid - 1);
        root.right = ldrSolve3(nums, mid + 1, right);
        return root;
    }
}


