
/**
【剑指 Offer】05.替换空格

请实现一个函数，把字符串 s 中的每个空格替换成"%20"。

示例 1：
输入：s = "We are happy."
输出："We%20are%20happy."

限制：
0 <= s 的长度 <= 10000

 */

public class Solution05 {
    public static void main(String[] args) {
        Solution05 s = new Solution05();
        System.out.println(s.replaceSpace("We are happy."));
        System.out.println(s.replaceSpace2("We are happy."));
    }

    /**
     * 方法一：遍历添加
     * 在 Python 和 Java 等语言中，字符串都被设计成「不可变」的类型，
     * 即无法直接修改字符串的某一位字符，需要新建一个字符串实现，所以无法原地修改。
     * 
     * 时间复杂度 O(n) 空间复杂度 O(n)
     * @param s
     * @return
     */
    public String replaceSpace(String s) {
        StringBuilder res = new StringBuilder();
        char[] arr = s.toCharArray();
        for (char c : arr) {
            if (c == ' ') {
                res.append("%20");
            } else {
                res.append(c);
            }
        }

        return res.toString();
    }

    /**
     * 方法二：调用 API
     */
    public String replaceSpace2(String s) {
        return s.replace(" ", "%20");
    }
}