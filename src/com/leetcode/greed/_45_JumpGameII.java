package com.leetcode.greed;

import com.zcy.dp.FaceBook;

import java.util.Arrays;

public class _45_JumpGameII {
    // 1. dp超时
    public int jump(int[] nums) {
        int n = nums.length;
        memo = new int[n];
        Arrays.fill(nums, n);
        // helper定义：从索引 p 跳到最后一格，至少需要 helper(dp, p) 步
        return helper(nums, 0);
    }

    public int helper(int[] nums, int p) {
        int n = nums.length;
        // base case
        if (p >= n - 1) {
            return 0;
        }
        // 子问题已经计算过
        if (memo[p] != n) {
            return memo[p];
        }
        int steps = nums[p];
        // 你可以选择跳 1 步，2 步...
        for (int i = 1; i <= steps; i++) {
            // 穷举每一个选择
            // 计算每一个子问题的结果
            int subProblem = helper(nums, p + i);
            // 取其中最小的作为最终结果
            memo[p] = Math.min(memo[p], subProblem + 1);
        }
        return memo[p];
    }

    public int[] memo;

    // 2. greed
    public int jump1(int[] nums) {
        int n = nums.length, steps = 0, end = 0, longest = 0;

        for (int i = 0; i < n - 1; i++){
            longest = Math.max(longest, i + nums[i]);
            if (end == i) {
                end = longest;
                steps++;
            }
        }

        return steps;
    }
}
