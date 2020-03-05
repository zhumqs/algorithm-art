package com.sword;

public class _17_HasSubtree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        //判断是否一个为null
        if(root1==null || root2==null) return false;
        //先判断是否是同一个树，然后判断是否跟左子树一样或者跟右子树一样
        return isSameTree(root1,root2) || HasSubtree(root1.left,root2) || HasSubtree(root1.right,root2);
    }
    //同样定义递归函数判断两个树是否相等
    public static boolean isSameTree(TreeNode a,TreeNode b) {
        //这里为甚么是true;
        //当B树的子树为空时，不管A树有没有子树直接返回true，因为到此时为止，之前的比较都是依次数值相同的。
        //当B树不为空，而A树已经为空的情况下，直接返回false，很明显B树不可能为A的子结构了。
        if (b == null) return true;
        if (a == null) return false;
        if (a.val == b.val) {
            return isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
        }
        return false;
    }
}
