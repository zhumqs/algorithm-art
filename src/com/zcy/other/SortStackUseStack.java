package com.zcy.other;

import java.util.Stack;

public class SortStackUseStack {
    //如果cur小于或等于help的栈顶元素，则将cur直接压入help
    //如果cur大于help的栈顶元素，则将help的元素逐一弹出，逐一压入原来的栈中，知道cur小于或者等于help栈顶的元素，再将cur压入help
    public static void sortStack(Stack<Integer> stack) {
        Stack<Integer> helper = new Stack<>();
        while (! stack.isEmpty()) {
            int cur = stack.pop();
            while(! helper.isEmpty() && helper.peek() < cur) {
                stack.push(helper.pop());
            }
            helper.push(cur);
        }
        while (! helper.isEmpty()) {
            stack.push(helper.pop());
        }
    }
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(2);
        stack.push(4);
        stack.push(0);
        stack.push(3);
        sortStack(stack);
        for (int i : stack) {
            System.out.println(i);
        }
    }
}
