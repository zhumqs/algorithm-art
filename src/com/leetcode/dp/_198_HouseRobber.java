package com.leetcode.dp;

import java.util.Arrays;

public class _198_HouseRobber {
    // recursive relation : rob(i) = Math.max( rob(i - 2) + currentHouseValue, rob(i - 1) )
    // 1 recursive (top-down)
    public int rob(int[] nums) {
        return rob(nums, nums.length - 1);
    }

    private int rob(int[] nums, int index) {
        if(index < 0){
            return 0;
        }
        return Math.max(rob(nums, index-2)+ nums[index], rob(nums, index-1));
    }

    // 2 Recursive + memo (top-down)
    int[] memo;
    public int rob1(int[] nums) {
        memo = new int[nums.length + 1];
        Arrays.fill(memo, -1);
        return rob(nums, nums.length - 1);
    }

    private int rob1(int[] nums, int index) {
        if(index < 0){
            return 0;
        }
        if(memo[index] >= 0){
            return memo[index];
        }
        int ans = Math.max(rob(nums, index-2)+nums[index], rob(nums, index - 1));
        memo[index] = ans;
        return ans;
    }

    //4. Iterative + memo (bottom-up)
    public int rob3(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }

        int memo[] = new int[nums.length+1];
        memo[0] = 0;
        memo[1] = nums[0];
        for(int i = 1;i<nums.length; i++){
            int val = nums[i];
            memo[i+1] = Math.max(memo[i], memo[i-1] + val);
        }
        return memo[nums.length];
    }

    // 5. Iterative + 2 variables (bottom-up)
    // the order is : prev2, prev1, num ....
    public int rob4(int[] nums) {
        if(nums.length == 0){
            return 0;
        }
        int prev1 = 0;
        int prev2 = 0;
        for(int num : nums) {
            int tmp = prev1;
            prev1 = Math.max(prev2 + num, prev1);
            prev2 = tmp;
        }
        return prev1;
    }

}
