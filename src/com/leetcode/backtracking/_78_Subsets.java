package com.leetcode.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _78_Subsets {

    /**
     * 1. bracktracking
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }
    private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
        list.add(new ArrayList<>(tempList));
        for(int i = start; i < nums.length; i++){
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    /**
     * 2. binary
     * leetcode地址: https://leetcode-cn.com/problems/subsets/solution/er-jin-zhi-wei-zhu-ge-mei-ju-dfssan-chong-si-lu-9c/
     *  1 << 10: 0000 0000 0000 0000 0000 0000 0000 0001
     *           0000 0000 0000 0000 0000 0010 0000 0000
     *
     *  (i >> j) & 1
     *      (1)(0 >> 0) & 1 :
     *                  0000 0000 0000 0000 0000 0000 0000 0000
     *                  0000 0000 0000 0000 0000 0000 0000 0000
     *                  0000 0000 0000 0000 0000 0000 0000 0001
     *                  = 0
     *      (2)(0 >> 1) & 1 :
     *                  0000 0000 0000 0000 0000 0000 0000 0000
     *                  0000 0000 0000 0000 0000 0000 0000 0000
     *                  0000 0000 0000 0000 0000 0000 0000 0001
     *                  = 0
     *
     *      ....
     *
     *      (5 >> 1) & 1:
     *                  0000 0000 0000 0000 0000 0000 0000 0101
     *                  0000 0000 0000 0000 0000 0000 0000 0010
     *                  0000 0000 0000 0000 0000 0000 0000 0001
     *                  = 0
     *      ...
     *
     */
    public static List<List<Integer>> binaryBit(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        // 1<<nums.length 等价于 2^nums.length,对于n个数字的数组，共2^n个子集
        for (int i = 0; i < (1 << nums.length); i++) {
            List<Integer> sub = new ArrayList<Integer>();
            for (int j = 0; j < nums.length; j++) {
                // 第2个问号处，以0~(2^n)-1，2^n个n位二进制数，对应于所有子集，从后往前第 j 个二进制位为 0 表示不放入第 j 个元素,同理1表示放入。" ((i >> j) & 1) == 1 " 表示对于二进制数i，从低位到高位逐个取其二进制位，并判断是否为1，若为1则放入对于nums中的元素。
                // 如nums={1,2,3},i=6,三位二进制为110，则对应子集 {2,3}
                if (((i >> j) & 1) == 1) {
                    sub.add(nums[j]);
                }
            }
            res.add(sub);
        }
        return res;
    }

    /**
     * 3. Enumerates
     */
    /**
     * 循环枚举
     */
    public static List<List<Integer>> enumerate(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        res.add(new ArrayList<Integer>());
        for (Integer n : nums) {
            int size = res.size();
            for (int i = 0; i < size; i++) {
                List<Integer> newSub = new ArrayList<Integer>(res.get(i));
                newSub.add(n);
                res.add(newSub);
            }
        }
        return res;
    }

    /**
     * 递归枚举
     */
    public static void recursion(int[] nums, int i, List<List<Integer>> res) {
        if (i >= nums.length) {
            return;
        }
        int size = res.size();
        for (int j = 0; j < size; j++) {
            List<Integer> newSub = new ArrayList<Integer>(res.get(j));
            newSub.add(nums[i]);
            res.add(newSub);
        }
        recursion(nums, i + 1, res);
    }

    /**
     * 4. DFS
     */
    /**
     * DFS，前序遍历
     */
    public static void preOrder(int[] nums, int i, ArrayList<Integer> subset, List<List<Integer>> res) {
        if (i >= nums.length) {
            return;
        }
        // 到了新的状态，记录新的路径，要重新拷贝一份
        subset = new ArrayList<Integer>(subset);

        // 这里
        res.add(subset);
        preOrder(nums, i + 1, subset, res);
        subset.add(nums[i]);
        preOrder(nums, i + 1, subset, res);
    }

    /**
     * DFS，中序遍历
     */
    public static void inOrder(int[] nums, int i, ArrayList<Integer> subset, List<List<Integer>> res) {
        if (i >= nums.length) {
            return;
        }
        subset = new ArrayList<Integer>(subset);

        inOrder(nums, i + 1, subset, res);
        subset.add(nums[i]);
        // 这里
        res.add(subset);
        inOrder(nums, i + 1, subset, res);
    }

    /**
     * DFS，后序遍历
     */
    public static void postOrder(int[] nums, int i, ArrayList<Integer> subset, List<List<Integer>> res) {
        if (i >= nums.length) {
            return;
        }
        subset = new ArrayList<Integer>(subset);

        postOrder(nums, i + 1, subset, res);
        subset.add(nums[i]);
        postOrder(nums, i + 1, subset, res);
        // 这里
        res.add(subset);
    }
}
