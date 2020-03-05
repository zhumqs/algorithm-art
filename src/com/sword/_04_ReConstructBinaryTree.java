package com.sword;


public class _04_ReConstructBinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //根据中序和前序重建二叉树
    public TreeNode reConstructBinaryTree(int [] pre, int [] in) {
        return reBuilder(pre,0,pre.length-1,in,0,in.length-1);
    }

    public static TreeNode reBuilder(int pre[],int pleft,int pright,int in[],int ileft,int iright){
        if(pleft>pright||ileft>iright) return null;
        //先序列的第一个位置就是根节点
        TreeNode cur = new TreeNode(pre[pleft]);
        int i=0;
        //寻找根节点在中序的位置
        for(i = ileft;i<=in.length;i++){
            if(pre[pleft] == in[i])
                break;
        }
        //递归左子树进行恢复,由上面可以知道左子树的长度是i-ileft,则开始index=pleft+1,结束index=pleft+1+(i-ileft)-1=pleft+i-ileft
        cur.left = reBuilder(pre,pleft+1,pleft+i-ileft,in,ileft,i-1);
        //递归右子树进行恢复，显然在pre中右子树的开始索引就是前面左子树结束索引的下一个位置，所以就是pleft+i-ileft+1
        cur.right = reBuilder(pre,pleft+i-ileft+1,pright,in,i+1,iright);
        return cur;
    }
}
