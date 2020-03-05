package com.sword;

public class _15_ReverseList {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode ReverseList(ListNode head) {
        //只用一个节点或者节点为空，直接返回head
        if(head == null||head.next==null) return head;
        ListNode newHead = null ,cur = head;
        //从头开始处理到尾
        while(cur!=null){
            //保存cur的next节点
            ListNode temp = cur.next;
            //使当前节点next指向前面处理后的头结点
            cur.next = newHead;
            //这样现在当前节点就应该是新的头节点
            newHead = cur;
            //接着处理，让当前节点后移到保存的节点
            cur = temp;
        }
        return newHead;
    }
}
