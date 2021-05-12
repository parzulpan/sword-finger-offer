package Java;

/**
 * @author parzulpan

【剑指 Offer】14-II. 剪绳子II
 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m - 1] 。
 请问 k[0]*k[1]*...*k[m - 1] 可能的最大乘积是多少？
 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是1
 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

 示例 1：
 输入: 2
 输出: 1
 解释: 2 = 1 + 1, 1 × 1 = 1

 示例2:
 输入: 10
 输出: 36
 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36

 提示：
 2 <= n <= 1000
 */

public class Solution14_1 {
    public static void main(String[] args) {
        Solution14_1.Solution solution = new Solution();
        System.out.println(solution.cuttingRope(10));
    }

    /**
     * 方法一：数学法
     * 两个重要推论：
     * 1. 将绳子以相等的长度等分为多段，得到的乘积最大。
     * 2. 尽可能将绳子以长度 3 等分为多段时，乘积最大。
     * 注意要考虑大数越界情况下的求余问题
     */
    static class Solution {
        public int cuttingRope(int n) {
            if (n <= 3) {
                return n - 1;
            }

            int a = n / 3, b = n % 3, p = 1000000007;
            long ret = 1;

            // 验算前 a - 1 段
            for (int i = 1; i < a; ++i) {
                ret = ret * 3 % p;
            }

            // 满足被 3 整除，加上前一段
            if (b == 0) {
                return (int)(ret * 3 % p);
            }

            // 满足被 3 整除余 1，加上前一段
            if (b == 1) {
                return (int)(ret * 4 % p);
            }

            // 满足被 3 整除余 2，加上前一段
            return (int)(ret * 6 % p);
        }
    }
}
