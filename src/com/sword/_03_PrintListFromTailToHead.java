package com.sword;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class _03_PrintListFromTailToHead {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
           this.val = val;
        }
    }

    ArrayList al = new ArrayList();

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if(listNode == null){
            return al;
        }
        Deque<Integer> stack = new ArrayDeque<>();
        while(listNode != null){
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        while(!stack.isEmpty()){
            al.add(stack.pop());
        }
        return al;
    }
}
