package Java;

/**
 * @author parzulpan

【剑指 Offer】10-II.青蛙跳台阶问题

一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

 */

public class Solution10_1 {
    public static void main(String[] args) {
        Solution so = new Solution();
        System.out.println(so.numWays(7));
    }

    /**
     * 方法一：斐波拉契数列的变种，动态规划
     */
    static class Solution {
        final int CONSTANT = 1000000007;

        public int numWays(int n) {
            int first = 0, second = 1, sum;
            for (int i = 0; i < n; ++i) {
                sum = (first + second) % CONSTANT;
                first = second;
                second = sum;
            }

            return second;
        }
    }
}