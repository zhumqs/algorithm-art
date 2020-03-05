package com.zcy.tree;

import java.util.LinkedList;
import java.util.Queue;

public class IsCompleteBT {
    //利用层次遍历当前遍历到了NULL结点，如果后续还有非NULL结点，说明是非完全二叉树。
    //flag记录是否遍历到null了
    private boolean flag = false;
    public boolean isComplete(TreeNode root) {
        if(root == null ){
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode cur = queue.poll();
            if(cur != null) {
                //压队先判断是否出现过空了
                if(flag) {
                    return false;
                }
                //不管左右子树是否有空直接入队
                queue.offer(cur.right);
                queue.offer(cur.left);
            }else {
                flag = true;
            }
        }
        return true;
    }
}
