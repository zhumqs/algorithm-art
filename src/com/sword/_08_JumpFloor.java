package com.sword;

public class _08_JumpFloor {
    public int JumpFloor(int target) {
        //跟斐波那契一样
        int f = 1, g = 2;
        while(target-->1) {
            g += f;
            f = g - f;
        }
        return f;
    }
}
