package com.sword;

public class _61_SerializeTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public String GolbalStr;
    public String Serialize(TreeNode root){
        if(root == null){
            return "#";
        }
        //类似与先序遍历
        return root.val+" "+ Serialize(root.left) +" "+ Serialize(root.right);
    }
    public TreeNode Deserialize(String str){
        GolbalStr = str;
        return Deserialize();
    }

    //利用先序恢复树
    public TreeNode Deserialize(){
        //树节点的值可能有几个字符
        if(GolbalStr.length() == 0) return  null;
        int index = GolbalStr.indexOf(" ");
        //当前节点的val值
        String node = (index == -1?GolbalStr:GolbalStr.substring(0,index));
        //剩下的字符串
        GolbalStr = (index == -1?"" :GolbalStr.substring(index+1));
        //是否为空节点，为空节点直接返回null
        if(node.equals("#")){
            return null;
        }
        TreeNode t = new TreeNode(Integer.valueOf(node));
        //这里不用全局变量会出现问题
        t.left = Deserialize();
        t.right = Deserialize();
        return t;
    }
}
