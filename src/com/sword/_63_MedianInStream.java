package com.sword;

import java.util.PriorityQueue;

public class _63_MedianInStream {
    //小顶堆保存大的一部分，大顶堆保存小的一部分
    PriorityQueue<Integer> right = new PriorityQueue<>();
    PriorityQueue<Integer> left = new PriorityQueue<>((o1, o2) -> o2 - o1);
    int N = 0;
    public void Insert(Integer num) {
        if(N%2 ==0){
            //为偶数时先插入到大顶堆中选出最大的，然后插入到小顶堆
            left.add(num);
            right.add(left.poll());
        }else{
            //为奇数时先插入到小顶堆选出右边最小的即为左边最大的然后插入到左边
            right.add(num);
            left.add(right.poll());
        }
        N++;
    }

    public Double GetMedian() {
        //为偶数则返回两个堆顶的平均值
        if(N%2 == 0){
            return (left.peek()+right.peek())/2.0;
        }else{
            //为奇数直接返回大顶堆堆顶
            return (double)right.peek();
        }
    }
}
