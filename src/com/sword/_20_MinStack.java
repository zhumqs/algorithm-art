package com.sword;

import java.util.Stack;

public class _20_MinStack {
    //正常栈用来记录压入和弹出的值
    Stack<Integer> stack1 = new Stack<>();
    //借助辅助栈每次栈顶都是保存当前栈最小元素，并且弹出时辅助栈也要弹出
    Stack<Integer> stack2 = new Stack<>();
    public void push(int node) {
        stack1.push(node);
        //如果当前节点比辅助栈的栈顶元素小则压入当前元素，否则接着压入辅助栈栈顶的元素
        if(stack2.isEmpty()){
            stack2.push(node);
        }else{
            stack2.push(stack2.peek()>node?node:stack2.peek());
        }
    }

    public void pop() {
        stack1.pop();
        stack2.pop();
    }

    public int top() {
        return stack1.peek();
    }

    public int min() {
        return stack2.peek();
    }
}
