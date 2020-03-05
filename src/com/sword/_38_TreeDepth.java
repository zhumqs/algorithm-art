package com.sword;

public class _38_TreeDepth {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public int TreeDepth(TreeNode root) {
        if(root == null) return 0;
        return 1+Math.max(TreeDepth(root.left),TreeDepth(root.right));
    }
}
