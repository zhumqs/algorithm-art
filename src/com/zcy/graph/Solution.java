package com.zcy.graph;

class Solution {
    public static boolean checkInclusion(String s1, String s2) {
        int l1 = s1.length();
        int l2 = s2.length();
        int [] count = new int [128];
        if(l1 > l2)
            return false;
        for(int i = 0; i<l1; i++){
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        if(allZero(count))
            return true;
        for(int i = l1; i<l2; i++){
            count[s2.charAt(i) - 'a']--;
            count[s2.charAt(i-l1) - 'a']++;
            if(allZero(count))
                return true;
        }
        return false;
    }
    public static boolean allZero(int [] count){
        int l = count.length;
        for(int i = 0; i < l; i++){
            if(count[i] != 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "sb";
        String s2 = "gsfsdbhmbasbv";
        System.out.println(checkInclusion(s1,s2));

    }
}