package com.sword;

public class _40_FindNumsAppearOnce {
    //num1,num2分别为长度为1的数组。传出参数
    //将num1[0],num2[0]设置为返回结果
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        //直接用位运算异或来做
        /*
        两个不相等的元素在位级表示上必定会有一位存在不同。
        将数组的所有元素异或得到的结果为不存在重复的两个元素异或的结果。
        diff &= -diff 得到出 diff 最右侧不为 0 的位，也就是不存在重复的两个元素在位级表示上最右侧不同的那一位，利用这一位就可以将两个元素区分开来。
         */
        int diff = 0;
        for(int num:array){
            //所有元素异或后的结果为不重复两个元素的异或结果
            diff ^= num;
        }
        //从有往左第一位为1的位,除了这一位为1其他全为0
        diff &= -diff;
        //再次遍历数组让diff跟所有元素的异或即可得到两个不重复的值
        for(int num:array){
            //这些重复的元素可以分成两拨：一波跟其中一个不重复在这位上相同，一波跟另一个不重复在这位上不同
            if((diff&num) == 0) num1[0] ^= num;
            else num2[0] ^= num;
        }
    }
}
