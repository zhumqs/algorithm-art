package com.zcy.sort;

import java.util.Scanner;

public class solution2 {
    public static int solution(int start ,int end) {
        if(start == end) {
            return start % 2 == 0? start : start*(-1);
        }
        int len = end - start + 1;
        if(start%2 == 0){
            if(len %2 == 1) {
                return len/2*(-1) + end;
            }else {
                return (len/2)*(-1);
            }
        }else {
            if(len % 2 == 1){
                return (len/2) + end*(-1);
            }else {
                return len/2;
            }
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {//注意while处理多个case
            int times = in.nextInt();
            for(int i = 0 ;i<times;i++){
                int start = in.nextInt();
                int end = in.nextInt();
                System.out.println(solution(start,end));
            }
        }
    }
}
