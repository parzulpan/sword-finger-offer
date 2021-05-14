package Java;

import java.util.Arrays;

/**
 * @author parzulpan

【剑指 Offer】17. 打印从 1 到最大的 n 位数

输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。

示例 1:
输入: n = 1
输出: [1,2,3,4,5,6,7,8,9]

说明：
用返回一个整数列表来代替打印
n 为正整数

 */

public class Solution17 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.printNumbers(3)));
        System.out.println(Arrays.toString(solution.printNumbers2(3)));
        System.out.println(solution.printNumbers3(3));
    }

    static class Solution {
        /**
         * 方法一：不考虑大数问题
         * 最大的 n 位数 end 和 位数 n 关系为：end = 10^n - 1
         *
         * 时间复杂度 O(10^n)
         */
        public int[] printNumbers(int n) {
            int end = (int) (Math.pow(10, n) - 1);
            int[] res = new int[end];
            for (int i = 0; i < end; i++) {
                res[i] = i + 1;
            }
            return res;
        }

        /**
         * 方法二：考虑大数问题
         * 数字的取值范围都是有限的，因此大数的表示应该用字符串 String 类型。
         * 观察可知，生成的列表实际上是 n 位 0 - 9 的 全排列，可以通过递归实现。
         *
         * 时间复杂度 O(10^n)
         */
        char [] num;
        int [] res;
        int count;
        int n;

        public int[] printNumbers2(int n) {
            this.n = n;
            num = new char[n];
            res = new int[(int) (Math.pow(10, n) - 1)];
            dfs(0);
            return res;
        }

        private void dfs(int n) {
            // 代表数字全排列完成
            if (n == this.n) {
                String s = String.valueOf(num);
                int curNum = Integer.parseInt(s);
                if (curNum != 0) {
                    res[count++] = curNum;
                }
                return;
            }
            for (char i = '0'; i <= '9'; i++) {
                num[n] = i;
                dfs(n + 1);
            }
        }

        /**
         * 方式三：同方法二
         * 只不过明确使用 String 表示，因为方法二 int curNum = Integer.parseInt(s); 还是没绕开大数问题
         */
        char [] num3;
        StringBuilder res3;
        int n3;

        public String printNumbers3(int n) {
            this.n3 = n;
            num3 = new char[n];
            res3 = new StringBuilder();
            dfs3(0);
            res3.deleteCharAt(res3.length() - 1);
            return res3.toString();
        }

        private void dfs3(int n) {
            // 代表数字全排列完成
            if (n == this.n3) {
                // 正则表达式，去掉 字符串 开头的 0
                String s = String.valueOf(num3).replaceAll("^(0+)", "");
                if (!"".equals(s)) {
                    res3.append(s).append(",");
                }
                return;
            }
            for (char i = '0'; i <= '9'; i++) {
                num3[n] = i;
                dfs3(n + 1);
            }
        }
    }
}
