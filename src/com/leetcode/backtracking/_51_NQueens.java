package com.leetcode.backtracking;

import java.util.*;

public class _51_NQueens {
    public List<List<String>> res = new ArrayList<>();

    public List<List<String>> solveNQueens(int n) {
//        List<String> list = new ArrayList<String>() {//这个大括号相当于new接口
//            {//这个大括号就是构造代码块, 会在构造函数前调用
//                for (int i = 0; i < n; i++) {
//                    this.add(".");//this 可以省略这里加上
//                }
//            }
//        };
        char[][] board = new char[n][n];
        for (char[] chars : board) {
            Arrays.fill(chars, '.');
        }

        backtracking(board, 0);
        return res;
    }

    private void backtracking(char[][] board, int row) {
        int n = board.length;
        // add answer
        if (row == n) {
            List<String> list = new ArrayList<>();
            for (char[] chars : board) {
                list.add(String.valueOf(chars));
            }
            res.add(list);
            return;
        }

        for (int col = 0; col < n; col++) {
            // 排除不合法的
            if (!isValid(board, row, col)) {
                continue;
            }
            // 选择放皇后
            board[row][col] = 'Q';
            // 继续向下搜索
            backtracking(board, row + 1);
            // 回溯
            board[row][col] =  '.';
        }
    }

    // 为什么不用判断行？ 因为在某一行上只会选择一个位置放置皇后Q，然后回溯将这个位置置为.,接着选择这一行上其他位置放置皇后Q。
    private boolean isValid(char[][] board, int row, int col) {
        int n = board.length;
        // 检查列是否有皇后互相冲突
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q'){
                return false;
            }
        }
        // 检查右上方是否有皇后互相冲突
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q'){
                return false;
            }
        }
        // 检查左上方是否有皇后互相冲突
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q'){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        _51_NQueens queens = new _51_NQueens();
        List<List<String>> res = queens.solveNQueens(4);
        System.out.println(res.toString());

        _NQueens_2 queens_2 = new _NQueens_2();
        List<List<String>> res1 = queens_2.solveNQueens(4);
        System.out.println(res1.toString());
    }

    /**
     * 改进判断
     */
    public static class _NQueens_2 {
        /**
         * 优化isValid的查询，通过3个set来分别记录列、主对角线、副对角线上Q的情况，减少迭代的查询
         * Key值：colIndex, [r-c], [r + c] 作为set的key
         */
        private List<List<String>> res = new LinkedList<>();
        private Set<Integer> colSet = new HashSet<>();
        private Set<Integer> masterSet = new HashSet<>();
        private Set<Integer> slaveSet = new HashSet<>();

        public List<List<String>> solveNQueens(int n) {
            char[][] board = new char[n][n];
            for (char[] chars : board) {
                Arrays.fill(chars, '.');
            }
            backtrack(board, 0);
            return res;
        }

        /**
         * path: board in [0, row -1]
         * choices for a row : every cols
         * time to end: row == board.length
         *
         * @param board
         * @param row
         */
        private void backtrack(char[][] board, int row) {
            if (row == board.length) {
                res.add(charToString(board));
                return;
            }
            for (int col = 0; col < board[row].length; col++) {
                if (!isValid(board, row, col)) {
                    continue;
                }
                updateRecords(board, row, col);
                backtrack(board, row + 1);
                updateRecords(board, row, col);
            }
        }

        private void updateRecords(char[][] board, int row, int col) {
            if (colSet.contains(col)) {
                board[row][col] = '.';
                colSet.remove(col);
                masterSet.remove(row - col);
                slaveSet.remove(row + col);
            } else {
                board[row][col] = 'Q';
                colSet.add(col);
                masterSet.add(row - col);
                slaveSet.add(row + col);
            }
        }

        private boolean isValid(char[][] board, int row, int col) {
            return !colSet.contains(col)
                    && !masterSet.contains(row - col)
                    && !slaveSet.contains(row + col);
        }

        private static List<String> charToString(char[][] array) {
            List<String> result = new LinkedList<>();
            for (char[] chars : array) {
                result.add(String.valueOf(chars));
            }
            return result;
        }
    }
}
