package com.leetcode.greed;

public class _55_JumpGame {
    public static boolean canJump(int[] nums) {
        int n = nums.length, longest = 0;
        for (int i = 0; i < n - 1; i++) {
            longest = Math.max(longest, nums[i] + i);
            // 碰到0
            if (longest <= i) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] nums = {3,2,1,0,5};
        System.out.println(canJump(nums));
    }

}
