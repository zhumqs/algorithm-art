package com.zcy.sort;

import java.util.*;

public class solution4 {
    public static int shoot(int num[],int n){
        //用队列做而不是用set来做
        //1~n连续几枪
        Queue<Integer> queue = new LinkedList<>();
        //记录每个气球出现的次数
        Map<Integer,Integer> map = new HashMap<>();
        int min = Integer.MAX_VALUE;
        for(int i = 0;i<num.length;i++) {
            //当前元素是否等于队首元素
            queue.offer(num[i]);
            //加入map中
            if(map.containsKey(num[i])){
                int value = map.get(num[i]);
                map.put(num[i],value+1);
                while (map.get(queue.peek()) > 1) {
                    map.put(queue.peek(), map.get(queue.peek()) - 1);
                    queue.poll();
                }
            }else{
                map.put(num[i],1);
            }

            if(map.size() == 5) {
                min = Math.min(min, queue.size());
            }
        }
        return min;
    }

        public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {//注意while处理多个case
            int n = in.nextInt();
            int m = in.nextInt();
            int num[] = new int[n];
            for(int i = 0;i<n;i++) {
                int a = in.nextInt();
                num[i] = a;
            }
            System.out.println(shoot(num,m));
        }
    }
}
