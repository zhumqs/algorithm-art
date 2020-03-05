package com.sword;

public class _56_EntryNodeOfLoop {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode EntryNodeOfLoop(ListNode pHead)
    {
        //判断是否包含环用快慢指针，如果有环直接快慢指针肯定会相遇的
        if(pHead == null) return null;
        //快指针fast 慢指针slow
        ListNode fast = pHead,slow = pHead;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            //存在环时获取环的入口
            if(fast == slow){
                //让快指针到链表头，然后速度变成跟慢指针一样
                fast = pHead;
                //再相遇时就是在环的入口了
                while(fast != slow){
                    fast = fast.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        //显然当循环遇到null说明没有环
        return null;
    }
}
