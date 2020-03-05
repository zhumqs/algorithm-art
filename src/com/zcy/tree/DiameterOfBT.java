package com.zcy.tree;

public class DiameterOfBT {
    //二叉树的直径，一棵二叉树的直径长度是任意两个结点路径长度中的最大
    //值。这条路径可能穿过根结点。

    //递归解法：对于每个节点，它的最长路径等于左子树的最长路径+右子树的最长路径
    int path = 0;
    public int diameter(TreeNode root ) {
        diamaterHelper(root);
        return path;
    }

    private int diamaterHelper(TreeNode root) {
        if(root == null) {
            return 0;
        }
        //求一个节点左右节点最长路径
        int left = diamaterHelper(root.left);
        int right = diamaterHelper(root.right);
        //跟当前path比较较大则更新path,这里是求路径不是节点数，所以不用加1
        path = Math.max(path,left + right);
        return Math.max(left,right) + 1;

    }


}
