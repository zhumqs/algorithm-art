package com.zcy.tree;

public class LowestCommonAncestorInBST {
    //BT和BST的区别
    public TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //如果都小于root则在左子树上
        if(root.value > p.value && root.value > q.value) {
            return commonAncestor(root.left,p,q);
        } else if(root.value < p.value && root.value < q.value){
            //如果都大于root则在root的右子树上
            return commonAncestor(root.right,p,q);
        } else {
            //一个大于一个小于则共公就是在root上
            return root;
        }
    }
}
