package com.sword;

public class _65_HasPath {
    public boolean hasPath(char[] matrix , int rows ,int cols , char[] str){
        //参数校验
        if(matrix == null || matrix.length != rows*cols || str == null || str.length <1){
            return  false;
        }
        //boolean矩阵来记录是否访问过
        boolean[] visited = new boolean[rows*cols];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
        //记录结果的数组
        int pathLength[] = {0};
        //从每个位置都进行搜索一遍
        for(int i= 0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                if(hasPathCore(matrix,rows,cols,str,visited,i,j,pathLength))
                    return true;
            }
        }
        return false;
    }
    public static boolean hasPathCore(char[] matrix,int rows ,int cols , char[] str, boolean[] visited,int row,int col,int[] pathLength){
        if(pathLength[0] == str.length)
            return true;
        boolean hasPath = false;
        //判断当前位是否合法
        if(row>=0 && row<rows && col<cols && col>=0 && matrix[row*cols + col] == str[pathLength[0]]
                &&!visited[row*cols + col]) {
            visited[row*cols + col] = true;
            pathLength[0]++;
            //当前位符合判断下一个位置是否符合，不符合当前位则要回溯
            //即当前位的上下左右四个位置
            hasPath = hasPathCore(matrix, rows, cols, str, visited, row, col - 1, pathLength)
                    || hasPathCore(matrix, rows, cols, str, visited, row - 1, col, pathLength)
                    || hasPathCore(matrix, rows, cols, str, visited, row, col + 1, pathLength)
                    || hasPathCore(matrix, rows, cols, str, visited, row + 1, col, pathLength);
            //如果没找到符合的需要回溯
            if (!hasPath) {
                pathLength[0]--;
                visited[row * cols + col] = false;
            }
        }
        return hasPath;
    }
}
