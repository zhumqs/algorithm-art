package com.sword;

public class _36_FindFirstCommonNode {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    // 时间复杂度 l1.length+l2.length
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode l1 = pHead1,l2 = pHead2;
        while (l1!=l2){
            l1 = (l1 != null ? l1.next : pHead2);
            l2 = (l2 != null ? l2.next : pHead1);
        }
        return l1;
    }
}
