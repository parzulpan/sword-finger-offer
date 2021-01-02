import java.util.HashMap;

/**

【剑指 Offer】10-I.斐波那契数列

写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。斐波那契数列的定义如下：
F(0) = 0,   F(1) = 1
F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

 */

public class Solution10 {
    public static void main(String[] args) {
        Solution10 s = new Solution10();
        Solution so = s.new Solution();
        long t1 = System.currentTimeMillis();

        System.out.println(so.fib(20));
        long t2 = System.currentTimeMillis();
        System.out.println(t2- t1);

        System.out.println(so.fib2(20));
        long t3 = System.currentTimeMillis();
        System.out.println(t3- t2);
        
        System.out.println(so.fib3(20));
        long t4 = System.currentTimeMillis();
        System.out.println(t4- t3);
    }


    /**
     * 方法一：递归
     * 把 f(n) 问题的计算拆分成 f(n-1) 和 f(n-2) 两个子问题的计算，并递归，以 f(0) 和 f(1) 为终止条件。
     * 
     * 方法二：记忆化递归
     * 在递归法的基础上，新建一个哈希表，用于在递归时存储 f(0) 至 f(n) 的数字值，重复遇到某数字则直接取用，避免了重复的递归计算。
     * 
     * 方法三：动态规划
     * 以斐波那契数列性质 f(n + 1) = f(n) + f(n - 1) 为转移方程。
     * 
     */
    class Solution {
        final int CONSTANT = 1000000007;
        HashMap<Integer, Integer> map = new HashMap<>();

        public int fib(int n) {
            int first = 0, second = 1, sum;
            for (int i = 0; i < n ; ++i) {
                sum = (first + second) % CONSTANT;
                first = second;
                second = sum;
            }

            return first;
        }

        public int fib2(int n) {
            if (n < 2) {
                return n;
            }
            if (map.containsKey(n)) {
                return map.get(n);
            }

            int first = fib2(n - 1) % CONSTANT;
            map.put(n - 1, first);
            int second = fib2(n - 2) % CONSTANT;
            map.put(n - 2, second);
            int res = (first + second) % CONSTANT;
            map.put(n, res);

            return res;
        }

        public int fib3(int n) {
            if (n < 2) {
                return n;
            }

            int first = (fib3(n - 1) % CONSTANT);
            int second = (fib3(n - 2) % CONSTANT);
            return (first + second) % CONSTANT;
        }
    }
}