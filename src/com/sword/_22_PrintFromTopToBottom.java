package com.sword;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class _22_PrintFromTopToBottom {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        //从上往下打印二叉树和将二叉树打印成多行不一样
        //打印出多行需要记录没行有多少个元素，使用标记来记录最右的节点或者在入队的时候记录每层入队的元素葛个数
        //或者是直接用两个队来实现

        //从上往下直接打印则只需用一个队实现即可，不需要记录每行
        Queue<TreeNode> queue = new LinkedList<>();
        if(root == null) return list;
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode temp = queue.poll();
            list.add(temp.val);
            if(temp.left != null){
                queue.offer(temp.left);
            }
            if(temp.right != null){
                queue.offer(temp.right);
            }
        }
        return list;
    }
}
