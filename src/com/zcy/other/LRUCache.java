package com.zcy.other;

import java.util.HashMap;

public class LRUCache {
    private int capacity;
    private final HashMap<Integer, Node> map;
    private Node head;
    private Node end;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
    }

    public int get(int key) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            remove(node);
            setHead(node);
            return node.value;
        }
        return -1;
    }

    public void set(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            //更新原记录
            node.value = value;
            remove(node);
            setHead(node);
        }else {
            Node newNode = new Node(key, value);
            //map超出或者等于容量
            if(map.size() >= capacity){
                map.remove(end.key);
                remove(end);
                setHead(newNode);
            }else {
                setHead(newNode);
            }
            //插入新纪录
            map.put(key, newNode);
        }
    }

    private void setHead(Node node) {
        node.next = head;
        node.prev = null;

        //head不为空  则将head.prev置为node
        if(head != null) {
            head.prev = node;
        }

        head = node;

        //end为空，说明当前节点既是头又是尾
        if(end == null) {
            end = head;
        }

    }

    private void remove(Node node) {
        if(node.prev != null) {
            node.prev.next = node.next;
        }else {
            //node是当前的head
            head = node.next;
        }

        if(node.next != null) {
            node.prev.next = node.prev;
        }else {
            //node是当前的end
            end = node.prev;
        }
    }


    public static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
