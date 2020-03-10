package com.leetcode.divide;

public class _108_ConvertSortedArrayToBinarySearchTree {

    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }

    public TreeNode buildTree(int[] nums, int l, int r) {
        if (l > r) {
            return null;
        }
        int mid = l + (r - l) / 2;

        TreeNode node = new TreeNode(nums[mid]);
        node.left = buildTree(nums, l, mid - 1);
        node.right = buildTree(nums, mid + 1, r);

        return node;
    }

     public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
      }
}
