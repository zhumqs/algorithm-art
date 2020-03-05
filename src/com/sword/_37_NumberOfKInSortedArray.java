package com.sword;

public class _37_NumberOfKInSortedArray {
    public int[] searchRange(int[] nums, int target) {
        //target为原数的最左边界
        int first = binarySearch(nums, target);
        //target+1的最左边界 - 1
        int last = binarySearch(nums, target + 1) - 1;
        if (first == nums.length || nums[first] != target) {
            return new int[]{-1, -1};
        } else {
            return new int[]{first, Math.max(first, last)};
        }
    }

    private int binarySearch(int[] nums, int target) {
        int l = 0, h = nums.length; // 注意 h 的初始值
        while (l < h) {
            int m = l + (h - l) / 2;
            if (nums[m] >= target) {
                h = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
}
