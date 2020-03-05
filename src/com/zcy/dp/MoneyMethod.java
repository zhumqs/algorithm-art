package com.zcy.dp;

public class MoneyMethod {
    //暴力递归法 process1(arr,index,aim)用arr的index到length-1的值组成aim的方法
    public static int getMethod1(int[] arr,int aim) {
        if(arr == null || arr.length == 0 || aim <0)
            return 0;
        return process1(arr,0,aim);
    }

    public static int process1(int arr[],int index,int aim) {
        int count = 0;
        //到最后aim是否为0
        if(index == arr.length) {
            count = (aim == 0 ? 1 : 0);

        }else {
            //不断增加张数
            for (int i = 0; arr[index] * i <= aim; i++)
                count += process1(arr, index + 1, aim - arr[index] * i);
        }
        return count;
    }


    /**
     * 记忆搜索优化，准备全局变量map，记录已经计算过的递归过程的结果
     * map是一张二维表，map[i][j]表示递归过程p(i,j)的返回值。另外有一些特别值，map[i][j]==0表示递归过程p(i,j)从来没有计算过。
     * map[i][j]==-1表示递归过程p(i,j)计算过，但是返回是0.
     * 如果map[i][j]的值既不等于0，也不等于-1，记为a，则表示递归过程p(i,j)的返回值为a
     * 记忆化搜索的时间复杂度为O(N*aim^2)
     * @param arr
     * @param aim
     * @return
     */
    private static int getMethod2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] map = new int[arr.length + 1][aim + 1];
        return process2(arr, 0, aim, map);
    }

    private static int process2(int[] arr, int index, int aim, int[][] map) {
        int res = 0;
        if (index == arr.length) {
            res = aim == 0 ? 1 : 0;
        } else {
            int mapValue = 0;
            for (int i = 0; arr[index] * i <= aim; i++) {
                mapValue = map[index + 1][aim - arr[index] * i];
                if (mapValue != 0) {
                    res += mapValue == -1 ? 0 : mapValue;
                } else {
                    res += process2(arr, index + 1, aim - arr[index] * i, map);
                }
            }
        }
        map[index][aim] = res == 0 ? -1 : res;
        return res;
    }


    /**
     * 除第一行和第一列的其他位置，记为位置（i,j）。dp[i,j]的值是以下几个值的累加。
     *
     * 完全不用arr[i]货币，只用arr[0...i-1]货币时，方法数为dp[i-1][j];
     * 用1张arr[i]货币，剩下的钱用arr[0...i-1]货币组成时，方法数为dp[i-1][j-arr[i]];
     * 用2张arr[i]货币，剩下的钱用arr[0...i-1]货币组成时，方法数为dp[i-1][j-2*arr[i]];
     * ...
     * 用k张arr[i]货币，剩下的钱用arr[0...i-1]货币组成时，方法数为dp[i-1][j-k*arr[i]]，j-k*arr[i]>=0，k为非负整数.
     *
     * 动态规划方法
     * 生成生成行数为N，列数为aim+1的矩阵dp，dp[i][j]的含义是在使用arr[0...i]货币的情况下，组成钱数j有多少种方法。
     * 时间复杂度为O(N*aim^2)。
     *
     * @param arr
     * @param aim
     * @return
     */
    private static int getMethod3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; arr[0] * j <= aim; j++) {
            dp[0][arr[0] * j] = 1;
        }
        int num = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                num = 0;
                for (int k = 0; j - arr[i] * k >= 0; k++) {
                    num += dp[i - 1][j - arr[i] * k];
                }
                dp[i][j] = num;
            }
        }
        return dp[arr.length - 1][aim];
    }

    //动态规划法
    /**
     * 步骤3可以简化为dp[i][j] = dp[i-1][j]+dp[i][j-arr[i]]
     * 时间复杂度度为O(N*aim)
     *
     * @param arr
     * @param aim
     * @return
     */
    public static int getMethod4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; arr[0] * i <= aim; i++) {
            dp[0][arr[0] * i] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
            }
        }
        return dp[arr.length - 1][aim];
    }

    /**
     * 空间压缩
     * @param arr
     * @param aim
     * @return
     */
    public static int getMethod5(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[] dp = new int[aim + 1];
        for (int j = 0; j * arr[0] <= aim; j++) {
            dp[arr[0] * j] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[j] += j - arr[i] >= 0 ? dp[j - arr[i]] : 0;
            }
        }
        return dp[aim];
    }

    public static void main(String[] args) {
        int arr[] = {5,10,20,50};
        long begin1 = System.currentTimeMillis();
        System.out.println(getMethod1(arr,1000));
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - begin1);

        long begin2 = System.currentTimeMillis();
        System.out.println(getMethod2(arr,1000));
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - begin2);

        long begin3 = System.currentTimeMillis();
        System.out.println(getMethod3(arr,1000));
        long end3 = System.currentTimeMillis();
        System.out.println(end3 - begin3);

        long begin4 = System.currentTimeMillis();
        System.out.println(getMethod4(arr,1000));
        long end4 = System.currentTimeMillis();
        System.out.println(end4 - begin4);

        long begin5 = System.currentTimeMillis();
        System.out.println(getMethod5(arr,1000));
        long end5 = System.currentTimeMillis();
        System.out.println(end5 - begin5);
    }

}
