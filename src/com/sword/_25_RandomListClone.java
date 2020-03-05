package com.sword;

public class _25_RandomListClone {
    public class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }
    public RandomListNode Clone(RandomListNode pHead){
        //两种方法
        //2.1 新加入节点插入到原节点的后面
        //2.2 设置random指针得时候只需要pHead.next.random = p.random.next;就可以
        //2.3 拆开链表
        if(pHead == null) return null;
        RandomListNode pre = pHead;
        while(pre!=null){
            RandomListNode newNode = new RandomListNode(pre.label);
            newNode.next = pre.next;
            pre.next = newNode;
            pre = newNode.next;
        }
        //遍历链表设置random指针.复用pre指针
        pre = pHead;
        while(pre!=null){
            //判断random是否为null
            if(pre.random!=null){
                pre.next.random = pre.random.next;
            }
            pre = pre.next.next;
        }
        //拆分两个链表(注意细节),并再次复用pre指针
        RandomListNode newHead = pHead.next;
        pre = pHead;
        while(pre!=null){
            RandomListNode temp = pre.next;
            pre.next = temp.next;
            if(temp.next!=null)
                temp.next = temp.next.next;
            pre = pre.next;
        }
        return newHead;
    }
}
