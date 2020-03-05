package com.sword;

public class _09_JumpFloorII {
    public int JumpFloorII(int target) {
        //可以直接根据公式来推算
        /*
            f(n) = f(n-1) + f(n-2) + f(n-3) +...+f(1)
            f(n-1) = f(n-2) + f(n-3) +...+f(1)
            ....
            f(n) = 2*f(n-1)
        */
        //这接用位移运算来做
        int a = 1;
        a = a << (target-1);
        return a;
    }
}
