package com.sword;

public class _31_NumberOf1Between1AndN {
    public int solution(int number){
        int count = 0;
        for(int base = 1;base <= number;base *= 10){
            //整数部分
            int a = number/base;
            //余数部分
            int b = number%base;
            //如果当前位为0或者1，(a+8)/10直接为0前面一部分没有只需计算后面剩余的
            //如果为1直接为余数+1，为0就没有
            count += (a+8)/10*base +  (a%10 == 1?b+1:0);
        }
        return count;
    }
}
