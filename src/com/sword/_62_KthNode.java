package com.sword;

public class _62_KthNode {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    TreeNode result;
    int index = 0;
    TreeNode KthNode(TreeNode pRoot, int k) {
        //二叉搜索树的中序遍历(左根右)是非递减序列
        //中序遍历第k个节点就是第k小的元素
        inOrder(pRoot,k);
        return result;
    }

    private void inOrder(TreeNode pRoot, int k) {
        if(pRoot == null || index >= k){
            return;
        }
        inOrder(pRoot.left,k);
        index++;
        if(index == k){
            result = pRoot;
        }
        inOrder(pRoot.right,k);
    }
}
