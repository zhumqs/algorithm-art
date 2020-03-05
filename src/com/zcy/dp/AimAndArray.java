package com.zcy.dp;

public class AimAndArray {
    //递归版本
    public static boolean aim1(int arr[],int i , int sum ,int aim) {
        if(i == arr.length) {
            return sum == aim;
        }
        return aim1(arr,i+1,sum,aim) || aim1(arr,i+1,sum + arr[i],aim);
    }

    //非递归版本
    public static boolean aim2(int arr[],int aim) {
        //用bool数组来记录状态
        if(arr.length == 0 || aim < 0){
            return false;
        }
        boolean[][] dp = new boolean[arr.length+1][aim+1];
        for(int i = 0;i<dp.length;i++){
            dp[i][aim] = true;
        }
        //
        for(int i = arr.length-1;i>=0;i--){
            for(int j = aim -1 ;j>=0;j--){
                dp[i][j] = dp[i+1][j];
                if(arr[i] + j <= aim ){
                    dp[i][j] = dp[i][j] || dp[i+1][arr[i] + j];
                }
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[] arr = {1,4,95};
        int aim = 12;
        System.out.println(aim2(arr,aim));
    }
}
