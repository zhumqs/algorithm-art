package com.sword;

import java.util.ArrayList;

public class _41_FindContinuousSequence {
    /*
     * small和big分别表示序列的最小值和最大值。
     * 首先把small初始化为1，big初始化为2.
     * 如果从small到big的序列的和大于s，我们可以从序列中去掉原来最小的值small,即small++。
     * 如果从small到big的序列的和小于s，我们可以再加入一个数,让序列包含更多的数字,即big++。
     * 相等时直接将这个序列加入到ArrayList中，然后将big++后求和，即向前移动接着操作
     * 因为这个序列至少要有两个数字，我们一直增加small到(1+s)/2为止。
     */
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int big = 2;
        int small = 1;
        int curSum = 3;
        while(small<(1+sum)/2&&big<sum){
            if(curSum == sum){
                ArrayList<Integer> list = new ArrayList<>();
                for(int i = small;i<=big;i++){
                    list.add(i);
                }
                result.add(list);
                curSum -= small;
                small++;
                big++;
                curSum += big;
            }else if(curSum<sum){
                big++;
                curSum += big;
            }else{
                curSum -= small;
                small++;
            }
        }
        return result;
    }
}
