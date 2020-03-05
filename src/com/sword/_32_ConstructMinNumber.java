package com.sword;

import java.util.Arrays;
import java.util.Comparator;

public class _32_ConstructMinNumber {
    public String PrintMinNumber(int [] arr) {
        //相加根据字典序排序
        //if(arr == null||arr.length==0)return "";
        //整数数组换成字符数组
        String[] strArr = new String[arr.length];
        for(int i =0;i<arr.length;i++){
            strArr[i] = String.valueOf(arr[i]);
        }
        //直接根据Arrays.sort添加自定义的比较器来对数组进行排序
        Arrays.sort(strArr,new Comparator<String>(){
            @Override
            public int compare(String s1, String s2) {
                String left = s1+s2;
                String right = s2+s1;
                //比较是s1s2还是s2s1大
                /*
                 * 如果s1s2>s2s1,打印s1s2,即s2排在s1右边,则对应数组中应该认为s1<s2;
                 * compare根据比较结果正负判断大小，结果为正s1>s2即s1应该在右边,结果为负s2>s1即s2应该在右边;
                 * 所以这里加上'-'号就是求组合后最大的数了！！！
                 */
                return left.compareTo(right);
            }
        });
        //拼接排好序的字符串数组即可
        StringBuilder sb = new StringBuilder();
        for(String str:strArr){
            sb.append(str);
        }
        return sb.toString();
    }
}
