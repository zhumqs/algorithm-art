package com.sword;

import java.util.ArrayList;

public class _24_FindPathInTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    public ArrayList<Integer> list = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        //当前节点为null直接返回
        if(root == null) return result;
        list.add(root.val);
        target -= root.val;
        //判断是否到叶子节点了，并target是否为0如果是则将当前的list加入到result中
        if(root.left==null&root.right== null&&target == 0){
            //这里不能直接用list，因为这里result执行list引用的ArrayList,
            //由于后序会改变list指向的List所以会改变前面保存的结果
            result.add(new ArrayList<Integer>(list));
        }
        //递归左右子树
        FindPath(root.left,target);
        FindPath(root.right,target);
        //移除最后一个元素，深度遍历完一条路径后要回退
        list.remove(list.size()-1);
        return result;
    }
}
