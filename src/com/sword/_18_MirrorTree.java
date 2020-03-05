package com.sword;

public class _18_MirrorTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public void Mirror(TreeNode root) {
        if(root == null )
            return;
        convert(root);
    }

    public TreeNode convert(TreeNode root) {
        if(root == null ){
            return root;
        }

        TreeNode temp = root.left;
        root.left = convert(root.right);
        root.right = convert(temp);

        return root;

    }
}
