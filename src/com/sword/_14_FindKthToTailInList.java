package com.sword;

import java.util.ArrayDeque;
import java.util.Deque;

public class _14_FindKthToTailInList {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode FindKthToTail(ListNode head,int k) {
        //解法1 使用栈来做，先全都入栈，然后出栈第k个就是了
        if(head == null || k == 0) return null;
        Deque<ListNode> stack = new ArrayDeque<>();
        //统计个数
        int count =0;
        while(head!=null){
            stack.push(head);
            count++;
            head = head.next;
        }
        //如果k比节点数多或者k==0则返回空
        while(k>count)
        {
            return null;
        }
        //一次弹出前k-1个
        while(k-->1){
            stack.pop();
        }
        return stack.pop();
    }
}
