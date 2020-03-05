package com.zcy.tree;

public class LowertCommonAncestorInBT {
    //最近公共祖先，递归来做
    public TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q){
            return root;
        }
        TreeNode left = commonAncestor(root.left,p,q);
        TreeNode right = commonAncestor(root.right,p,q);
        //如果都不为null那就是这个root
        if(left != null && right != null){
            return root;
        }
        return left == null ? right : left;
    }

    //通过一个数组来构造一个树
    public TreeNode buildTreeByArray(int[] arr, int index) {
        TreeNode newNode = null;
        if(index < arr.length) {
            //创建节点
            newNode = new TreeNode(arr[index]);
            newNode.left = buildTreeByArray(arr, index * 2 + 1);
            newNode.right = buildTreeByArray(arr, index * 2 + 2);
            return newNode;
        }
        return newNode;
    }
}

class TreeNode {
    int value ;
    TreeNode  left;
    TreeNode  right;
    public TreeNode(int value) {
        this.value =value;
    }

}
