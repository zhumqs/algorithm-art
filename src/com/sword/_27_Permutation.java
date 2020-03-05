package com.sword;

import java.util.ArrayList;
import java.util.Collections;

public class _27_Permutation {
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> result = new ArrayList<>();
        //按字典需打印，先将输入字符进行排序
        //转成字符数组方便操作
        char[] ch = str.toCharArray();
        //Arrays.sort(ch);
        permutation(result,0,ch);
        Collections.sort(result);
        return result;
    }
    public static void permutation(ArrayList<String> result, int index,char[] ch){
        if(index == ch.length-1){
            result.add(new String(ch));
        }
        for(int i = index;i<ch.length;i++){
            if(index !=i&&ch[index]==ch[i]){
                continue;
                //交换index和i的位置
            }
            swap(ch,index,i);
            permutation(result,index+1,ch);
            //得到结果之后换回来
            swap(ch,index,i);
        }
    }

    public static void swap(char[] ch,int a,int b){
        char temp = ch[a];
        ch[a] = ch[b];
        ch[b] = temp;
    }
}
