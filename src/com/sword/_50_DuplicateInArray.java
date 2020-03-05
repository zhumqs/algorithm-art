package com.sword;

public class _50_DuplicateInArray {
    public static boolean duplicate(int numbers[],int length,int [] duplication) {
        if(numbers == null || length<2) return false;
        for ( int i= 0 ; i<length; i++) {
            int index = numbers[i];
            if (index >= length) {
                index -= length;
            }
            if (numbers[index] >= length) {
                duplication[0] = numbers[i]-length;
                return true;
            }
            numbers[index] = numbers[index] + length;
        }
        return false ;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2,3,1,0,2,5,3};
        System.out.println(duplicate(arr,arr.length,new int[1]));
    }
}
