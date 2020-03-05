package com.sword;

public class _51_Multiply {
    public int[] multiply(int[] A) {
        //先求前一部分
        //然后在前一部分的基础上求后一部分
        //首先B[i]=A[1]*A[2]*A[3]*...*A[i-1]
        int n = A.length;
        int[] B = new int[n];

        //先求前一部分
        B[0] = 1;
        for (int i = 1; i < n; i++) {
            //利用前面求过的值
            B[i] = B[i - 1] * A[i - 1];
        }

        /*
         * 然后求后一部分
         * 只需乘上后面的A[i+1]*A[i+2]*...*A[n-1]
         * pre保存前面求过的A[i+1]*...*A[n-1]
         */
        int pre = 1;
        for (int i = n - 2; i >= 0; i--) {
            pre *= A[i + 1];
            /*
             * B[n-1]=A[1]*...*A[n-2]所以前已经求出， 这里不需要再求B[n-1]
             * 相当于用上个for求的B[i]再乘上后面的一部分
             */
            B[i] = B[i] * pre;
        }
        return B;
    }
}
