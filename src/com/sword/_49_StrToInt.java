package com.sword;

public class _49_StrToInt {
    public static int StrToInt(String str) {
        if(str == null || str.trim().equals("")) return 0;
        char[] ch = str.toCharArray();
        int sum = 0;
        int first = 0;
        //判断是否有符号
        if(ch[0] == '-' || ch[0] == '+'){
            first = 1;
        }
        //判断符号后面是否有非数组的字符
        for(int i = first;i<ch.length;i++){
            if(ch[i]<='9'&&ch[i]>='0'){
                sum = sum*10 + ch[i] - 48;
            }else{
                return 0;
            }
        }
        return first == 1?(ch[0] == '-'?(-1)*sum:sum):sum;
    }
    public static void main(String[] args) {
        System.out.println(StrToInt("-890"));
    }
}
