package com.sword;

public class _12_Power {
    public double Power(double base, int exponent) throws Exception{
        //首先判断底数不能为0且指数为0时结果为1
        double result = 1;
        if(base == 0){
            return 0;
        }
        if(exponent == 0)
            return result;
        //将指数统一为正
        int n = exponent>0?exponent:(-exponent);
        //二分求解，然后平方
        result = Power(base,n>>1);
        result = result*result;
        //判断奇偶性，是奇数还得再乘上一个base
        if((n &0x1) == 1)
            result = result*base;
        //然后根据奇偶来确定结果
        result = exponent>0?result:1/result;
        return result;
    }
}
