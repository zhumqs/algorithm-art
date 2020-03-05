package com.sword;

public class _02_ReplaceSpace {
    public static String solution(StringBuffer str) {
        String str1 = str.toString();
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<str1.length();i++){
            if(str1.charAt(i)==' '){
                sb.append("%20");
            }else{
                sb.append(str1.charAt(i));
            }
        }
        return sb.toString();
    }
}
