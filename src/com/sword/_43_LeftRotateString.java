package com.sword;

public class _43_LeftRotateString {
    public String LeftRotateString(String str,int n) {
        if(str == null || n<0 || n>str.length()){
            return new String();
        }
        //直接先加上原来的str然后从n开始截原来长度的字符串即可
        int len = str.length();
        return str.concat(str).substring(n,n+len);
    }
}
