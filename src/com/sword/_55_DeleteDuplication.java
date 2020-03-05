package com.sword;

public class _55_DeleteDuplication {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode deleteDuplication(ListNode pHead)
    {
        // 设置一个节点指向头结点
        ListNode first = new ListNode(-1);
        first.next = pHead;
        // last总是指向已去重序列的最后一个节点
        ListNode last = first;
        while (pHead != null && pHead.next != null) {
            if (pHead.val == pHead.next.val) {
                int val = pHead.val;
                //遇到重复元素一直往后移动直到找到不重复节点，然后接到last后面
                while (pHead != null && pHead.val == val)
                    pHead = pHead.next;
                last.next = pHead;
            } else {
                last = pHead;
                pHead = pHead.next;
            }
        }
        return first.next;
    }
}
