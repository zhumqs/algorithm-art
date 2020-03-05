package com.sword;

public class _47_SumWithoutOther {
    public int Sum_Solution(int n) {
        //用递归的方式做
        //1.需利用逻辑与的短路特性实现递归终止。
        //2.当n==0时，(n>0)&&((sum+=Sum_Solution(n-1))>0)只执行前面的判断，为false，然后直接返回0；
        //3.当n>0时，执行sum+=Sum_Solution(n-1)，实现递归计算Sum_Solution(n)。
        int sum = n;
        boolean ans = (n>0)&&((sum += Sum_Solution(n-1))>0);
        return sum;
    }
}
