
/**
 
【剑指 Offer】12.矩阵中的路径

请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。
如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径。
[["a","b","c","e"],
["s","f","c","s"],
["a","d","e","e"]]
但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。

示例 1：
输入：board = [
["A","B","C","E"],
["S","F","C","S"],
["A","D","E","E"]], word = "ABCCED"
输出：true
示例 2：
输入：board = [
["a","b"],
["c","d"]], word = "abcd"
输出：false
 
提示：
1 <= board.length <= 200
1 <= board[i].length <= 200

 */

public class Solution12 {
    public static void main(String[] args) {
        Solution12 s = new Solution12();
        Solution so = s.new Solution();
        System.out.println( so.exist(new char[][] {{'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'}}, "ABCCED") );
    }

    class Solution {

        /**
         * 方法一：典型的矩阵搜索问题，可以用 DFS + 剪枝
         * DFS: 可以理解为暴力法遍历矩阵中所有字符串可能性，实质也是递归，先朝一个方向搜到底，再回溯至上个节点，沿着另一个方向搜索，以此类推。
         * 剪枝：在搜索中，遇到 这条路不可能和目标字符串匹配成功 的情况（例如：此矩阵元素和目标字符不同、此元素已被访问），则应立即返回，
         * 这被称之为可行性剪枝。
         * 
         * DFS 分析：
         *   1. 递归参数：当前元素在矩阵 board 中的行列索引 i 和 j ，当前目标字符在 word 中的索引 k 。
         *   2. 终止条件：1). 返回 false。即行或列索引越界，或者当前矩阵元素和目标字符不同。
         *              2). 返回 true。字符串全部匹配。
         *   3. 递推工作：1). 标记当前矩阵元素：将 board[i][j] 修改为 空字符 '' ，代表此元素已访问过，防止之后搜索时重复访问。
         *              2). 搜索下一单元格：朝当前元素的 上、下、左、右 四个方向开启下层递归，并记录结果至 res。
         *              3). 还原当前矩阵元素：将 board[i][j] 元素还原至初始值，即 word[k]，用作下一次遍历。
         */
        public boolean exist(char[][] board, String word) {
            for (int i = 0; i < board.length; ++i) {
                for (int j = 0; j < board[0].length; ++j) {
                    if (dfs(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }

            return false;
        }

        public boolean dfs(char[][] board, String word, int i, int j, int k) {
            // 终止条件 1
            if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != word.charAt(k)) {
                return false;
            }

            // 终止条件 2
            if (k == word.length() - 1) {
                return true;
            }
            
            board[i][j] = '\0'; // 标记当前矩阵元素

            // 搜索下一单元格
            boolean res = dfs(board, word, i + 1, j, k + 1) || dfs(board, word, i - 1, j, k + 1) || 
                          dfs(board, word, i, j + 1, k + 1) || dfs(board, word, i, j - 1, k + 1);
            
            board[i][j] = word.charAt(k);   // 还原当前矩阵元素

            return res;
        }
    }
}