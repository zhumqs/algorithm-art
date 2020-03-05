package com.sword;

public class _30_FindGreatestSumOfSubArray {
    //贪心算法
    public int maxSubArray(int[] array) {
        //连续子数组的最大和
        if(array.length == 0) return 0;
        int tempMax = array[0];
        //Max可能一开始会很小
        int Max = Integer.MIN_VALUE;
        for(int i =1;i<array.length;i++){
            if(tempMax + array[i]> array[i]){
                tempMax = tempMax + array[i];
            }else{
                tempMax = array[i];
            }
            if(tempMax > Max){
                Max = tempMax;
            }
        }
        return Max;
    }

    //更为简洁的写法
    public int maxSubArray2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int preSum = nums[0];
        int maxSum = preSum;
        for (int i = 1; i < nums.length; i++) {
            preSum = preSum > 0 ? preSum + nums[i] : nums[i];
            maxSum = Math.max(maxSum, preSum);
        }
        return maxSum;
    }
}
