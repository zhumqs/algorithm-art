package com.sword;

import java.util.Scanner;
import java.util.Stack;

public class _21_StackPopOrder {


    // pushA=[a b c  d]   popA[c  b d a] 合法
    // pushA=[a b c  d]   popA[d c a b] 非法

    public static boolean IsPopOrder(int [] pushA,int [] popA) {
        //长度不一致直接返回false
        if(pushA.length != popA.length) {
            return false;
        }
        //借助辅助栈，将压入序列压入栈中
        Stack<Integer> stack = new Stack<>();
        //记录弹出序列的位置，从左往右
        int popIndex = 0;
        //将压入序列压入栈中的过程中判断压入元素是否跟弹出序列当前位置相等
        for(int i = 0;i<pushA.length;i++){
            //当前元素压入栈
            stack.push(pushA[i]);
            //栈不为空且辅助栈顶元素跟弹出序列当前位置相等
            while(!stack.isEmpty()&&stack.peek()==popA[popIndex]){
                stack.pop();
                popIndex++;
            }
        }
        //最后看辅助栈是否为空
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter push array: ");
        String push = sc.next();
        if (push == null || push.trim().length() == 0) {
            return;
        }
        char[] pushCharArray = push.toCharArray();
        int[] pushIntArray = new int[pushCharArray.length];
        for (int i = 0; i<pushCharArray.length; i++) {
            pushIntArray[i] = (int)pushCharArray[i];
        }

        System.out.print("enter pop array: ");
        String pop = sc.next();
        if (pop == null || pop.trim().length() == 0) {
            return;
        }
        char[] popCharArray = pop.toCharArray();
        int[] popIntArray = new int[popCharArray.length];
        for (int i = 0; i<popCharArray.length; i++) {
            popIntArray[i] = (int)popCharArray[i];
        }
        System.out.println(IsPopOrder(pushIntArray, popIntArray));
    }
}
