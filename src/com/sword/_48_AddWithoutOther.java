package com.sword;

public class _48_AddWithoutOther {
    public int Add(int num1,int num2) {
        //不含进位的和
        int sum;
        //进位
        int add1;
        //复用num1和num2
        while(num2!=0){
            sum = num1^num2;
            //推出循环条件是不在产生进位
            add1 = (num1&num2) << 1;
            num1 = sum;
            num2 = add1;
        }
        return num1;
    }
}
