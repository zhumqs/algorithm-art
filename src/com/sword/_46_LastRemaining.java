package com.sword;

public class _46_LastRemaining {
    //约瑟夫环问题
    public int LastRemaining_Solution(int n, int m) {
        //直接递推式来做
        if(n<1||m<1) return -1;
        int p=0;
        for(int i = 2;i<=n;i++){
            p = (p+m)%i;
        }
        return p;
    }
}
