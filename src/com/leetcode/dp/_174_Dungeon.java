package com.leetcode.dp;

import java.util.Arrays;

public class _174_Dungeon {

    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] hp = new int[m + 1][n + 1];
        // 初始化(填充数组)
        for (int i = 0; i <= m; i++) {
            Arrays.fill(hp[i], Integer.MAX_VALUE);
        }
        hp[m-1][n] = 1;
        hp[m][n-1] = 1;

        for (int i = m - 1; i >= 0; --i)  {
            for (int j = n - 1; j >=0; --j) {
                hp[i][j] = Math.max(1, Math.min(hp[i+1][j], hp[i][j+1]) - dungeon[i][j]);
            }
        }

        return hp[0][0];
    }
}
