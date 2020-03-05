package com.zcy.sort;

import java.util.Scanner;

public class solution {
    //biggest代表最大面值，price代表商品价格
    public static int lessNum(int biggest,int price ) {
        //如果商品价格小于等于biggest直接就付一个就行
        int number = 0 , re = 0;
        number = price/biggest;
        re = price % number;
        if( re == 0){
            return number;
        }else{
            return number + 1;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {//注意while处理多个case
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(lessNum(a,b));
        }
    }
}
