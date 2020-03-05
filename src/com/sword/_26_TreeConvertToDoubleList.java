package com.sword;

public class _26_TreeConvertToDoubleList {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    private TreeNode pre = null;
    private TreeNode head = null;

    public TreeNode Convert(TreeNode root) {
        inOrder(root);
        return head;
    }

    private void inOrder(TreeNode node) {
        if (node == null)
            return;
        //先处理左边节点
        inOrder(node.left);
        //使当前节点的前驱指向pre节点，因为如果当前节点的左孩子有右子树的话，node的left就不能再指向左孩子了
        node.left = pre;
        //将左节点的right指向当前节点
        if (pre != null)
            pre.right = node;
        //当前节点置为pre
        pre = node;
        if (head == null)
            head = node;
        //再处理右节点
        inOrder(node.right);
    }
}
