package Java;

/**
 * @author parzulpan

 【剑指 Offer】15. 二进制中 1 的个数

 请实现一个函数，输入一个整数，输出该数二进制表示中 1 的个数。
 例如，把 9 表示成二进制是 1001，有 2 位是 1。因此，如果输入 9，则该函数输出 2。

 */

public class Solution15 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.hammingWeight(9));
    }

    static class Solution {

        /**
         * 方法一：逐位判断
         * 1. 根据与运算，设二进制数字为 n，则：
         *   1.1 若 n&1 = 0，则 n 二进制最右一位为 0；
         *   1.2 若 n&1 = 1，则 n 二进制最右一位为 1；
         * 2. 进行循环判断：
         *   2.1 判断 n 最右一位是否为 1，根据结果计数
         *   2.2 将 n 无符号右移一位
         */
        public int hammingWeight(int n) {
            int res = 0;
            while (n != 0) {
                res += n & 1;
                n >>>= 1;
            }
            return res;
        }
    }
}
