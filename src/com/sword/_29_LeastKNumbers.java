package com.sword;

import java.util.ArrayList;

public class _29_LeastKNumbers {
    //很多做法
    //partition、优先队列(堆)、排序
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> output = new ArrayList<>();
        if(input.length == 0 || k<= 0 || k>input.length)
            return output;
        int start = 0;
        int end = input.length-1;
        int index = partition(input,start,end);
        while(index != k-1){
            if(index > k-1){
                end = index -1;
                index = partition(input,start ,end);
            }
            else{
                start = index+1;
                index = partition(input,start ,end);
            }
        }
        for(int i = 0;i<k;i++){
            output.add(input[i]);
        }
        return output;

    }

    public int partition(int[] arr, int left, int right) {
        int result = arr[left];
        if (left > right)
            return -1;

        while (left < right) {
            while (left < right && arr[right] >= result) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] < result) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = result;
        return left;
    }
}
