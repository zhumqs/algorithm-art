package com.sword;

public class _23_VerifySquenceOfBST {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence.length == 0 || sequence==null) return false;
        return verifySeq(sequence,0,sequence.length-1);
    }
    public static boolean verifySeq(int seq[],int first,int last){
        if(last-first<=1) return true;
        int root = seq[last];
        int currentIndex = first;
        while(currentIndex<last && seq[currentIndex]<=root)
            currentIndex++;
        //判断右子树是否有小于根节点的
        for(int i = currentIndex;i<=last;i++){
            if(seq[i] < root){
                return false;
            }
        }
        //递归判断左子树和右子树
        return verifySeq(seq,first,currentIndex-1)&&verifySeq(seq,currentIndex,last-1);
    }
}
