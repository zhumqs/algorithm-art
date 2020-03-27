package com.leetcode.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 跟聚会人气有区别： 这里不一定是偷一层然后上下两层都不可以偷，比如可以同时偷root的左右节点和root的兄弟节点
 */
public class _337_HouseRobberIII {
    // 1. recursive
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int val = 0;

        if (root.left != null) {
            val += rob(root.left.left) + rob(root.left.right);
        }
        if (root.right != null) {
            val += rob(root.right.left) + rob(root.right.right);
        }

        return Math.max(val + root.val, rob(root.left) + rob(root.right));
    }

    // 2. recursive+memo
    public int rob2(TreeNode root) {
        return robSub(root, new HashMap<>());
    }

    private int robSub(TreeNode root, Map<TreeNode, Integer> map) {
        if (root == null){
            return 0;
        }
        if (map.containsKey(root)){
            return map.get(root);
        }

        int val = 0;
        if (root.left != null) {
            val += robSub(root.left.left, map) + robSub(root.left.right, map);
        }

        if (root.right != null) {
            val += robSub(root.right.left, map) + robSub(root.right.right, map);
        }

        val = Math.max(val + root.val, robSub(root.left, map) + robSub(root.right, map));
        map.put(root, val);

        return val;
    }

    //3. Iterative 跟聚会人气有区别： 这里不一定是偷一层然后上下两层都不可以偷，比如可以同时偷root的左右节点和root的兄弟节点
    //跟聚会人气有区别： 这里不一定是偷一层然后上下两层都不可以偷，比如可以同时偷root的左右节点和root的兄弟节点
    public int rob3(TreeNode root) {
        int[] res = robSub(root);
        return Math.max(res[0], res[1]);
    }

    private int[] robSub(TreeNode root) {
        if (root == null){
            return new int[2];
        }

        int[] left = robSub(root.left);
        int[] right = robSub(root.right);
        int[] res = new int[2];

        // 不抢当前层 = 左右子树能抢到的最大值
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        // 抢当前层 = 当前层的值 + 左右子树在不抢左右孩子节点情况下能抢到的最大值
        res[1] = root.val + left[0] + right[0];

        return res;
    }



    public static class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
    }
}
