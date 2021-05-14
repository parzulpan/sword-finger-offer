package Java;

/**
 * @author parzulpan

【剑指 Offer】16. 数值的整数次方

实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，x^n）。不得使用库函数，同时不需要考虑大数问题。

示例 1：
输入：x = 2.00000, n = 10
输出：1024.00000

示例 2：
输入：x = 2.10000, n = 3
输出：9.26100

示例 3：
输入：x = 2.00000, n = -2
输出：0.25000
解释：2^-2 = 1/2^2 = 1/4 = 0.25

提示：
-100.0 < x < 100.0
-2^31 <= n <= 2^31-1
-10^4 <= x^n <= 10^4


 */

public class Solution16 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.myPow(2.00000, 10));
        Solution solution1 = new Solution();
        System.out.println(solution1.myPow(2.10000, 3));
        Solution solution2 = new Solution();
        System.out.println(solution2.myPow(2.00000, -2));

        Solution solution3 = new Solution();
        System.out.println(solution3.myPow2(2.00000, 10));
        Solution solution4 = new Solution();
        System.out.println(solution4.myPow2(2.10000, 3));
        Solution solution5 = new Solution();
        System.out.println(solution5.myPow2(2.00000, -2));
    }

    static class Solution {
        /**
         * 方法一：循环相乘
         * 通过循环将 n 个 x 乘起来，依次求 x^1, x^2, ..., x^{n-1}, x^n
         *
         * 时间复杂度为 O(n)
         */
        public double myPow(double x, int n) {
            double result = 1;
            if (n >= 0) {
                for (int i = 0; i < n; ++i) {
                    result *= x;
                }
            } else {
                for (int i = 0; i < -n; ++i) {
                    result *= x;
                }
                result = 1 / result;
            }

            return result;
        }

        /**
         * 方法：二分降幂法
         * 当 n 为偶数时，x^n = x^(n/2) * x^(n/2) = (x^2)^(n/2)
         * 当 n 为奇数时，x^n = x^(n/2) * x^(n/2) * x = (x^2)^(n/2) * x
         * 所以，可循环 x = x^2 操作，这样能把幂 n 降为 n / 2，直至将幂降为 0
         *
         * 注意，Java 代码中 int32 变量 n∈[−2147483648,2147483647]，
         * 因此当 n = -2147483648 时执行 n = -n 会因越界而赋值出错。
         * 解决方法是先将 n 存入 long 变量 b ，后面用 b 操作即可。
         *
         * 时间复杂度为 O(log2n)
         */
        public double myPow2(double x, int n) {
            if (x == 0) {
                return 0;
            }

            long b = n;
            double result = 1.0;
            // 当 b 为负数时
            if (b < 0) {
                x = 1 / x;
                b = -b;
            }
            while (b > 0) {
                // 当 b 为奇数时
                if ((b & 1) == 1) {
                    result *= x;
                }
                x *= x;
                b >>= 1;
            }
            return result;
        }
    }
}
