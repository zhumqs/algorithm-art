package com.sword;

import java.util.Arrays;

public class _45_isContinuous {
    public boolean isContinuous(int [] numbers) {
        //判断输入的数组是不是可以组成顺子
        //判断0的个数(最多有4个)，然后计算非0元素之间的距离(相差大于1才开始算距离)，如果小于等于0的个数则可以组成，否则不能组成
        //排序确定0的个数
        if(numbers == null || numbers.length!=5) return false;
        Arrays.sort(numbers);
        int countOf0 = 0,gap = 0;
        int i = 0;
        for(;i<4;i++){
            if(numbers[i] == 0){
                countOf0++;
            }else if (numbers[i+1] == numbers[i]){
                //有相同的牌直接返回false
                return false;
            }else{
                gap += numbers[i+1] - numbers[i] - 1;
            }
        }
        return countOf0>=gap;
    }
}
