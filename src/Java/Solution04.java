package Java;

/**
 * @author parzulpan

【剑指 Offer】04.二维数组中的查找  

在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。

示例:
现有矩阵 matrix 如下：
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
给定 target = 5，返回 true。
给定 target = 20，返回 false。

限制：
0 <= n <= 1000
0 <= m <= 1000

 */

public class Solution04 {
    public static void main(String[] args) {
        Solution04 s = new Solution04();
        int[][] matrix = {{1, 4, 7, 11, 15}, 
                          {2, 5, 8, 12, 19}, 
                          {3, 6, 9, 16, 22}, 
                          {10, 13, 14, 17, 24}, 
                          {18, 21, 23, 26, 30}};
        System.out.println(s.findNumberIn2DArray(matrix, 5));
        System.out.println(s.findNumberIn2DArray(matrix, 20));
    }

    /**
     * 方法一：线性查找
     * 
     * 根据数组的特点，从二维数组的右上角开始查找。如果当前元素等于目标值，则返回 true。
     * 如果当前元素大于目标值，则移到左边一列。如果当前元素小于目标值，则移到下边一行。
     * 
     * 时间复杂度 O(n + m) 空间复杂度 O(1)
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int rows = 0, cols = 0;
        // 边界条件
        if (matrix == null || (rows = matrix.length) == 0 || (cols = matrix[0].length) == 0) {
            return false;
        }

        int row = 0, col = cols - 1;
        int temp = 0;
        while (row < rows && col >= 0) {
            temp = matrix[row][col];
            if (temp == target) {
                return true;
            } else if (temp > target) {
                --col;
            } else {
                ++row;
            }
        }

        return false;
    }
}