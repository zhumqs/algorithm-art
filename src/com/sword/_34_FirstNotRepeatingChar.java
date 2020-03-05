package com.sword;

import java.util.HashMap;

public class _34_FirstNotRepeatingChar {
    public int FirstNotRepeatingChar(String str) {
        //使用整型数组来做
        //hashmap来做，key是字符，值是字符出现的次数
        //将全部字符放入hashmap中返回第一个
        if(str == null || str.length() == 0) return -1;
        //先将字符串转成字符数组
        char[] ch = str.toCharArray();
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i =0;i<ch.length;i++){
            if(map.containsKey(ch[i])){
                int time = map.get(ch[i])+1;
                map.put(ch[i],time);
            }else{
                map.put(ch[i],1);
            }
        }
        //遍历数组寻找第一个值为1的key
        for(int i = 0;i<ch.length;i++){
            if(map.get(ch[i]) == 1){
                return i;
            }
        }
        //返回的是索引的位置，如果比遍历完都没有则返回-1
        return -1;
    }
}
