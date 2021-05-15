package Java;

/**
 * @author parzulpan

【剑指 Offer】19. 正则表达式匹配

请实现一个函数用来匹配包含 '.' 和 '*' 的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）。
在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串 "aaa"与模式 "a.a" 和 "ab*ac*a" 匹配，但与 "aa.a" 和 "ab*a" 均不匹配。

示例 1:
输入:
s = "aa"
p = "a"
输出: false
解释: "a" 无法匹配 "aa" 整个字符串。

示例 2:
输入:
s = "aa"
p = "a*"
输出: true
解释: 因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。

示例3:
输入:
s = "ab"
p = ".*"
输出: true
解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。

示例 4:
输入:
s = "aab"
p = "c*a*b"
输出: true
解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。

示例 5:
输入:
s = "mississippi"
p = "mis*is*p*."
输出: false
s 可能为空，且只包含从 a-z 的小写字母。
p 可能为空，且只包含从 a-z 的小写字母以及字符 . 和 * ，无连续的 '*'。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/zheng-ze-biao-da-shi-pi-pei-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

 */

public class Solution19 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isMatch("aa", "a"));
        System.out.println(solution.isMatch("aa", "a*"));
        System.out.println(solution.isMatch("ab", ".*"));
        System.out.println(solution.isMatch("aab", "c*a*b"));
        System.out.println(solution.isMatch("mississippi", "mis*is*p*."));
    }

    static class Solution {
        /**
         * 方法一：动态规划
         * 设 s 的长度为 n ， p 的长度为 m ；本题可转化为求 s[:n] 是否能和 p[:m] 匹配。
         *
         * 从 s[:1] 和 p[:1] 是否能匹配开始判断，每轮添加一个字符并判断是否能匹配，直至添加完整个字符串 s 和 p 。展开来看，假设 s[:i] 与 p[:j] 可以匹配，那么下一状态有两种：
         * 1. 添加一个字符 s_{i+1} 后是否能匹配？
         * 2. 添加一个字符 p_{i+1} 后是否能匹配？
         *
         * 状态定义： 设动态规划矩阵 dp， dp[i][j] 代表字符串 s 的前 i 个字符和 p 的前 j 个字符能否匹配。
         *
         * 初始化： 需要先初始化 dp 矩阵首行，以避免状态转移时索引越界。
         * dp[0][0] = true： 代表两个空字符串能够匹配。
         * dp[0][j] = dp[0][j - 2] 且 p[j - 1] = '*'： 首行 s 为空字符串，因此当 p 的偶数位为 * 时才能够匹配（即让 p 的奇数位出现 0 次，保持 p 是空字符串）。因此，循环遍历字符串 p ，步长为 2（即只看偶数位）。
         *
         * 转移方程： 需要注意，由于 dp[0][0] 代表的是空字符的状态， 因此 dp[i][j] 对应的添加字符是 s[i - 1] 和 p[j - 1] 。
         * 当 p[j - 1] = '*' 时， dp[i][j] 在当以下任一情况为 true 时等于 true ：
         * 情况 1：dp[i][j - 2]： 即将字符组合 p[j - 2] * 看作出现 0 次时，能否匹配；
         * 情况 2：dp[i - 1][j] 且 s[i - 1] = p[j - 2]: 即让字符 p[j - 2] 多出现 1 次时，能否匹配；
         * 情况 3：dp[i - 1][j] 且 p[j - 2] = '.': 即让字符 '.' 多出现 1 次时，能否匹配；
         * 当 p[j - 1] != '*' 时， dp[i][j] 在当以下任一情况为 true 时等于 true ：
         * 情况 4：dp[i - 1][j - 1] 且 s[i - 1] = p[j - 1]： 即让字符 p[j - 1] 多出现一次时，能否匹配；
         * 情况 5：dp[i - 1][j - 1] 且 p[j - 1] = '.'： 即将字符 . 看作字符 s[i - 1] 时，能否匹配；
         *
         * 返回值： dp 矩阵右下角字符，代表字符串 s 和 p 能否匹配。
         *
         * 时间复杂度 O(MN)： 其中 M, N 分别为 s 和 p 的长度，状态转移需遍历整个 dp 矩阵。
         */
        public boolean isMatch(String s, String p) {
            int m = s.length() + 1;
            int n = p.length() + 1;
            boolean[][] dp = new boolean[m][n];
            dp[0][0] = true;

            // 初始化首行
            for (int j = 2; j < n; j += 2) {
                dp[0][j] = (dp[0][j - 2] && p.charAt(j - 1) == '*');
            }

            // 状态转移
            for (int i = 1; i < m; ++i) {
                for (int j = 1; j < n; ++j) {
                    if (p.charAt(j - 1) == '*') {
                        // 情况 1
                        if (dp[i][j - 2]) {
                            dp[i][j] = true;
                        }
                        // 情况 2
                        else if (dp[i - 1][j] && s.charAt(i - 1) == p.charAt(j - 2)) {
                            dp[i][j] = true;
                        }
                        // 情况 3
                        else if (dp[i - 1][j] && p.charAt(j - 2) == '.') {
                            dp[i][j] = true;
                        }
                    } else {
                        // 情况 4
                        if (dp[i - 1][j - 1] && s.charAt(i - 1) == p.charAt(j - 1)) {
                            dp[i][j] = true;
                        }
                        // 情况 5
                        else if (dp[i - 1][j - 1] && p.charAt(j - 1) == '.') {
                            dp[i][j] = true;
                        }
                    }
                }
            }
            return dp[m - 1][n - 1];
        }
    }
}
