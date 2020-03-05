package com.sword;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class _64_MaxInWindows {
    public ArrayList<Integer> maxInWindows(int [] num, int size)
    {
        ArrayList<Integer> result = new ArrayList<>();
        if(size>num.length ||size<1)
            return result;
        PriorityQueue<Integer> heap = new PriorityQueue<>(((o1, o2) -> o2-o1));
        for(int i = 0;i<size;i++){
            heap.add(num[i]);
        }
        result.add(heap.peek());
        for(int i = 0,j=i+size;j<num.length;i++,j++){
            heap.remove(num[i]);
            heap.add(num[j]);
            result.add(heap.peek());
        }
        return result;
    }
}
