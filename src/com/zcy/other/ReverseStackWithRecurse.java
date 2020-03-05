package com.zcy.other;

import java.util.Stack;

public class ReverseStackWithRecurse {

    //设计递归函数, 将stack栈底最后一个元素移除并返回(即只删除最后一个元素其他元素还是原来的位置)
    //所以需要将返回的元素重新push进去
    public static int removeAndGetLastFromStack(Stack<Integer> stack) {
        int cur = stack.pop();
        if(stack.isEmpty()) {
            return cur;
        }else {
            int last = removeAndGetLastFromStack(stack);
            stack.push(cur);
            return last;
        }
    }
    
    //再设计递归函数进行reverse
    public static void reverse(Stack<Integer> stack) {
        if(stack.isEmpty()){
            return;
        }
        int cur = removeAndGetLastFromStack(stack);
        //stack已经是移除最后一个元素的栈
        reverse(stack);
        stack.push(cur);
    }
    
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        for(int i : stack){
            System.out.print(i + " ");
        }
        System.out.println();
        reverse(stack);
        for (int i : stack){
            System.out.print(i+" ");
        }
    }
}
