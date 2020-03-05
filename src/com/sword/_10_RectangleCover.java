package com.sword;

public class _10_RectangleCover {
    public int RectCover(int target) {
        //同理也是斐波那契的思路,但是确实0,1,2,3,5,8，.....
        //对于2*n的大矩阵 只能是一个2*1箱子竖着放，或者两个2*1箱子平着放
        if(target == 0) return 0;
        if(target == 1) return 1;
        int a = 1 , b = 1;
        while(--target>0){
            b += a;
            a = b-a;
        }
        return b;
    }
}
