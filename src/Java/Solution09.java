package Java;

import java.util.LinkedList;

/**
 * @author parzulpan

【剑指 Offer】09.用两个栈实现队列

用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )

示例 1：
输入：
["CQueue","appendTail","deleteHead","deleteHead"]
[[],[3],[],[]]
输出：[null,null,3,-1]

示例 2：
输入：
["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
[[],[],[5],[2],[],[]]
输出：[null,-1,null,null,5,2]

提示：
1 <= values <= 10000
最多会对 appendTail、deleteHead 进行 10000 次调用

 */

public class Solution09 {
    public static void main(String[] args) {
        CQueue obj = new CQueue();
        obj.appendTail(3);
        System.out.println(obj.deleteHead());
        System.out.println(obj.deleteHead());
    }

    /**
     * 方法一：双栈
     * 维护两个栈，第一个栈支持插入操作，第二个栈支持删除操作，进行删除操作需要进行转栈。
     */
    static class CQueue {
        LinkedList<Integer> stack1, stack2;

        public CQueue() {
            stack1 = new LinkedList<>();
            stack2 = new LinkedList<>();
        }
        
        public void appendTail(int value) {
            stack1.addLast(value);
        }
        
        public int deleteHead() {
            if (!stack2.isEmpty()) {
                return stack2.removeLast();
            }
            if (stack1.isEmpty()) {
                return -1;
            }
            while (!stack1.isEmpty()) {
                stack2.addLast(stack1.removeLast());
            }

            return stack2.removeLast();
        }
    }
}