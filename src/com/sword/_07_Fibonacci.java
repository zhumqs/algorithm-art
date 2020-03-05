package com.sword;

public class _07_Fibonacci {
    public int Fibonacci(int n) {
        int f = 0, g = 1;
        while(n-->0) {
            g += f;
            f = g - f;
        }
        return f;
    }
}
