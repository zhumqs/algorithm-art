package com.sword;

public class _33_UglyNumber {
    public int GetUglyNumber_Solution(int index) {
        if(index<=6) return index;
        int count = 1;
        int[] dp = new int[index];
        int i2=0,i3=0,i5=0;
        dp[0]=1;
        //dp[i2] dp[i3] dp[i5]是2,3,5最前面点
        //dp[count]是当前的点
        while(count<index){
            int n2 = dp[i2]*2;
            int n3 = dp[i3]*3;
            int n5 = dp[i5]*5;
            int min = Math.min(n2, Math.min(n3, n5));
            dp[count++] = min;
            if (min == n2) i2++;
            if (min == n3) i3++;
            if (min == n5) i5++;
        }
        return dp[index-1];
    }
}
