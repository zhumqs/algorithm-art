package com.sword;

public class TestReservesPrint {
    // 链表定义
    public static class LinkedNode {
        public int data;
        public LinkedNode next;
        public int getData() {
            return data;
        }
        public void setData(int data) {
            this.data = data;
        }
        public LinkedNode getNext() {
            return next;
        }
        public void setNext(LinkedNode next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        // 组成链表
        int len = 10;
        LinkedNode head = new LinkedNode();
        head.setData(0);
        LinkedNode temp = head;
        for (int i = 0; i<len; i++) {
            LinkedNode node = new LinkedNode();
            node.setData(i + 1);
            temp.setNext(node);
            temp = node;
        }
        print(head);
    }

    //递归遍历链表
    public static void print(LinkedNode L) {
        if(L.next != null) {
            print(L.next);
        }
        System.out.println(L.data);
    }

}
