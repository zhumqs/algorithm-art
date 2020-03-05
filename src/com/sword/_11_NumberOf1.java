package com.sword;

public class _11_NumberOf1 {
    public int NumberOf1(int n) {
        //当一个数减一之后它的二进制从右到左的第一个1会变成0，而后面的都会变成1
        //然后再和原来的数进行与运算就相当于把后面的1也变成了0所以这样就去掉了二进制中的一个1,循环知道全部去掉1则变成0
        int count=0;
        while(n!=0){
            n = n&(n-1);
            count++;
        }
        return count;
    }
}
