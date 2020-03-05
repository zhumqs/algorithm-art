package com.sword;

public class _66_CountingMoves {
    public int movingCount(int threshold, int rows, int cols)
    {
        //能经过多少格子即要去掉去过的格子，用visited[][]来记录是否访问过了
        boolean[][] visited = new boolean[rows][cols];
        return countSteps(threshold,rows,cols,0,0,visited);

    }
    public static int countSteps(int threshold,int rows,int cols,int row,int col,boolean[][] visited){
        //边界条件直接返回
        if(row>=rows||row<0||col>=cols||col<0||visited[row][col]||bitSum(row)+bitSum(col)>threshold){
            return 0;
        }
        visited[row][col] = true;
        return countSteps(threshold, rows, cols, row, col-1, visited)
                + countSteps(threshold, rows, cols, row-1, col, visited)
                + countSteps(threshold, rows, cols, row, col+1, visited)
                + countSteps(threshold, rows, cols, row+1, col, visited)
                +1 ;
    }
    public static int bitSum(int number){
        int sum = 0;
        while(number != 0){
            sum += number%10;
            number /= 10;
        }
        return sum;
    }
}
