package com.sword;

public class _13_ReOrderArray {
    //不改变相对位置，使数组奇数都排到偶数前面去
    public void reOrderArray(int [] array) {
        if(array==null||array.length==0)
            return;
        int i = 0,j;
        while(i<array.length){
            //找到偶数
            while(i<array.length&&array[i]%2==1)
                i++;
            j = i+1;
            //然后从偶数后面开始找第一个奇数
            while(j<array.length&&array[j]%2==0)
                j++;
            //将i到j-1后移一位然后将j放到i的位置
            if(j<array.length){
                int tmp = array[j];
                for (int j2 = j-1; j2 >=i; j2--) {
                    array[j2+1] = array[j2];
                }
                //隐含了i++
                array[i++] = tmp;
            }else{// 查找失敗
                break;
            }
        }
    }
}
