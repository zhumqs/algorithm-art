package com.sword;

public class _06_MinInRotateArray {
    public int minNumberInRotateArray(int [] array) {
        //为空或者元素个数为0返回0
        if(array==null || array.length == 0)return 0;
        int left = 0;
        int right = array.length-1;
        //先判断是否旋转过,没旋转直接返回首元素
        if(array[0]<array[right]){
            return array[0];
        }
        int mid;
        while(left<right){
            mid = left+(right-left)/2;
            //array[mid]>array[right]则最小元素一定在右边
            if(array[mid]>array[right]){
                left = mid +1;
                //array[mid] == array[right]则需再判断
            }else if(array[mid] == array[right]){
                right--;
                //array[mid]<array[right]则一定在左边
                //如果待查询的范围最后只剩两个数，那么mid 一定会指向下标靠前的数字,如果high = mid - 1，就会产生错误， 因此high = mid
            }else{
                right = mid;
            }
        }
        return array[left];
    }
}
