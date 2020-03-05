package com.zcy.tree;

public class CreatBinaryTreeByArray {
    static class TreeNode {
        int value ;
        TreeNode left;
        TreeNode right;
        public TreeNode(int value) {
            this.value = value;
        }
    }

    //构造树
    public static TreeNode ceateBinaryTree(int[] array,int index) {
        if(index >= array.length) {
            return null;
        }
        int value = array[index];
        TreeNode tn = new TreeNode(value);
        tn.left = ceateBinaryTree(array,2*index + 1);
        tn.right = ceateBinaryTree(array,2*index + 2);
        return tn;
    }
    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7,8,9,10};
        TreeNode newTree = ceateBinaryTree(arr,0);
        //然后遍历这个数组看是否则是否正确
        print(newTree);
    }

    private static void print(TreeNode newTree) {
        if(newTree == null) {
            return;
        }
        System.out.println(newTree.value);
        print(newTree.left);
        print(newTree.right);
    }


}
