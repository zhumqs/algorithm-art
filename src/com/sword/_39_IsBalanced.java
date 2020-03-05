package com.sword;

public class _39_IsBalanced {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean IsBalanced_Solution(TreeNode root) {
        if(root == null) return true;
        return isBalance(root);
    }

    public static boolean isBalance(TreeNode root) {
        if(root == null) return true;
        return Math.abs(height(root.left)-height(root.right)) <= 1 && isBalance(root.left) && isBalance(root.right);
    }

    public static int height(TreeNode root) {
        if(root == null) return 0;
        return Math.max(height(root.left),height(root.right)) + 1;
    }
}
