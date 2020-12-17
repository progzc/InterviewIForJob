package com.zcprog.arithmetic;

import com.zcprog.arithmetic.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 路径总和 III
 * @Author zhaochao
 * @Date 2020/12/16 22:46
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class LeetCode_0437_PathSum_iii {
    /**
     * LeetCode: https://leetcode-cn.com/problems/path-sum-iii/
     * 给定一个二叉树，它的每个结点都存放着一个整数值。
     * 找出路径和等于给定数值的路径总数。
     * 路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
     * 二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。
     */
    public static void main(String[] args) {
        Integer[] nums = {10, 5, -3, 3, 2, null, 11, 3, -2, null, 1};
        int sum = 8;
        TreeNode root = TreeNode.init(nums);
        System.out.println("-------------使用前缀和+哈希表------------");
        System.out.println(recursionSolve(root, sum));
        System.out.println("-------------使用双重递归------------");
        System.out.println(recursionSolve2(root, sum));
    }

    /**
     * 双重递归：一般解法
     * 时间复杂度：O(n*log(n))~O(n^2)
     * 空间复杂度：O(2n)
     */
    private static int recursionSolve2(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        // 以当前节点作为头结点的路径数量
        int result = recursionPathSum2(root, sum);
        // 当前节点的左子树中满足条件的路径数量
        int a = recursionSolve2(root.left, sum);
        // 当前节点的右子树中满足条件的路径数量
        int b = recursionSolve2(root.right, sum);
        return result + a + b;
    }

    private static int recursionPathSum2(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        sum = sum - root.val;
        int result = sum == 0 ? 1 : 0;
        return result + recursionPathSum2(root.left, sum) + recursionPathSum2(root.right, sum);
    }

    /**
     * 前缀和+哈希表
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     */
    private static int recursionSolve(TreeNode root, int sum) {
        // key是前缀和，value是大小为key的前缀和出现的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        // 前缀和为0的一条路径
        prefixSumCount.put(0, 1);
        // 前缀和的递归回溯思路
        return recursionPathSum(root, prefixSumCount, sum, 0);
    }

    /**
     * 前缀和的递归回溯思路
     * 从当前节点反推到根节点(反推比较好理解，正向其实也只有一条)，有且仅有一条路径，因为这是一棵树
     * 如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
     * 所以前缀和对于当前路径来说是唯一的，当前记录的前缀和，在回溯结束，回到本层时去除，保证其不影响其他分支的结果
     * @param node           树节点
     * @param prefixSumCount 前缀和Map
     * @param target         目标值
     * @param currSum        当前路径和
     * @return 满足题意的解
     */
    private static int recursionPathSum(TreeNode node, Map<Integer, Integer> prefixSumCount, int target, int currSum) {
        // 1.递归终止条件
        if (node == null) {
            return 0;
        }
        // 2.本层要做的事情
        int res = 0;
        // 当前路径上的和
        currSum += node.val;

        // 看看root到当前节点这条路上是否存在节点前缀和加target为currSum的路径
        // 当前节点->root节点反推，有且仅有一条路径，如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
        // currSum-target相当于找路径的起点，起点的sum+target=currSum，当前点到起点的距离就是target
        res += prefixSumCount.getOrDefault(currSum - target, 0);
        // 更新路径上当前节点前缀和的个数
        prefixSumCount.put(currSum, prefixSumCount.getOrDefault(currSum, 0) + 1);

        // 3.进入下一层
        res += recursionPathSum(node.left, prefixSumCount, target, currSum);
        res += recursionPathSum(node.right, prefixSumCount, target, currSum);

        // 4.回到本层，恢复状态，去除当前节点的前缀和数量
        prefixSumCount.put(currSum, prefixSumCount.get(currSum) - 1);
        return res;
    }
}
