package com.sword;

public class _35_InversePairs {
    //定义成员变量count和temp数组
    public long count = 0;
    public int[] temp ;

    public int InversePairs(int [] array) {
        temp = new int[array.length];
        mergeArray(array,0,array.length-1);
        return (int) (count % 1000000007);
    }

    private void mergeArray(int[] array, int left, int right) {
        if(right-left<1) return;
        int mid = left + (right-left)/2;
        mergeArray(array,left,mid);
        mergeArray(array,mid+1,right);
        merge(array,left,mid,right);
    }

    private void merge(int[] array, int left, int mid, int right) {
        int i = left,j = mid+1,k = left;
        while(i<=mid||j<=right){
            if(i>mid)
                temp[k] = array[j++];
            else if(j>right){
                temp[k] = array[i++];
            }else if(array[i]<array[j]){
                temp[k] = array[i++];
            }else{
                //当左边大于右边时
                temp[k] = array[j++];
                //则array[i]到array[mid]都是大于array[j]
                this.count += mid-i+1;
            }
            k++;

        }
        for(k = left;k<=right;k++){
            //更新到原数组中去
            array[k] = temp[k];
        }
    }
}
