package com.sword;

public class _01_FindInMatrix {
    public boolean solution(int target, int[][] array) {
        if(array==null) {
            return false;
        }
        //首先统计行列的长度
        int row = array.length;
        int col = array[0].length;
        //从左下角开始判断
        int i = row-1,j=0;
        while(i>=0&&j<col){
            if(array[i][j]<target){
                j++;
            }else if(array[i][j]>target){
                i--;
            }else{
                return true;
            }
        }
        return false;
    }
}
