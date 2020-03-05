package com.sword;

import java.util.Stack;

public class _05_StackToQueue {
    //push先看stack1是否为空不为空全弹出push到stack2中
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        //先判断辅助栈是否为空，不为空先全部压入主栈中，然后将要添加的元素push到主栈中，再将主栈中全部元素push到辅助栈中
        while(!stack2.isEmpty()){
            stack1.push(stack2.pop());
        }
        stack1.push(node);
        while(!stack1.isEmpty()){
            stack2.push(stack1.pop());
        }
    }

    public int pop() {
        return stack2.pop();
    }



}
