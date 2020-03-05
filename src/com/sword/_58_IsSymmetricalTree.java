package com.sword;

public class _58_IsSymmetricalTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    boolean isSymmetrical(TreeNode pRoot)
    {
        //递归做法
        if(pRoot == null) return true;
        return isSymmetrical(pRoot.left,pRoot.right);
    }

    private boolean isSymmetrical(TreeNode left, TreeNode right) {
        if(left == null && right == null) return true;
        if(left == null || right == null ) return false;
        if(left.val != right.val) return false;
        return isSymmetrical(left.left,right.right)&&isSymmetrical(left.right,right.left);
    }
}
