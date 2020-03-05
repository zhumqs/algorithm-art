package com.sword;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class _59_ZigZagPrintTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //用队列来做，reverse记录是否反转的标志
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if(pRoot == null) return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        //记录反转标志
        boolean reverse = false;
        //count记录每层的宽度
        int count = 0;
        while(!queue.isEmpty()){
            ArrayList<Integer> list = new ArrayList<>();
            count = queue.size();
            for(int i = 0;i<count;i++){
                TreeNode node = queue.poll();
                list.add(node.val);
                if(node.left != null)
                    queue.add(node.left);
                if(node.right != null)
                    queue.add(node.right);
            }
            if(reverse) Collections.reverse(list);
            reverse = !reverse;
            result.add(list);
        }
        return result;
    }
}
