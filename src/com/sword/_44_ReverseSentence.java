package com.sword;

public class _44_ReverseSentence {
    public String ReverseSentence(String str) {
        //str为null
        if(str == null) return null;
        //str只有空格时,这里必须搞清楚，因为可能全是空格，这是后不需要进行反转！！！！
        if(str.trim().equals("")) return str;
        //先反转每个单词然后反转整个句子或者先反转整个句子然后反正每个单词
        StringBuilder result = new StringBuilder();
        //根据空格将str字符串数组
        String[] strArr = str.trim().split(" ");
        for(int i = strArr.length-1;i>=0;i--) {
            //最后一个不用加" "
            if(i == 0) {
                result.append(strArr[i]);
            }else {
                result.append(strArr[i]).append(" ");
            }
        }
        return result.toString();
    }
}
