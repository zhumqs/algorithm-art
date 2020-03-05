package com.zcy.sort;

import java.util.Scanner;

public class solution3 {
    public static int solution (int n , int s) {
        //n中挑s种输，n-s种赢
        int sum1 = 1;
        int sum2 = 1;
        for(int i = 0;i<s;i++) {
            sum1 *= n - i;
            sum2 *= s - i;
        }
        sum1 = (sum1/sum2) * (int)Math.pow(2,n-s);
        return sum1;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int s = in.nextInt();
        while (in.hasNextInt()) {//注意while处理多个case
            for(int i = 0;i<n;i++) {
                int a = in.nextInt();
            }
            System.out.println(solution(n,s));
        }
    }
}
