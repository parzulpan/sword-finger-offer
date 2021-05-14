package Java;

import java.rmi.UnexpectedException;

/**
 * @author parzulpan

【剑指 Offer】18. 删除链表的节点

给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
返回删除后的链表的头节点。

示例 1:
输入: head = [4,5,1,9], val = 5
输出: [4,1,9]
解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.

示例 2:
输入: head = [4,5,1,9], val = 1
输出: [4,5,9]
解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.

说明：
题目保证链表中节点的值互不相同
若使用 C 或 C++ 语言，你不需要 free 或 delete 被删除的节点
 */

public class Solution18 {
    public static void main(String[] args) {

        // 尾插法创建链表
        ListNode headNode = null;
        ListNode tailNode = null;
        int [] node = {4, 5, 1, 9};
        for (int n : node) {
            ListNode curNode = new ListNode(n);
            if (headNode == null) {
                tailNode = headNode = curNode;
            }
            tailNode.next = curNode;
            tailNode = curNode;
        }
        System.out.println(headNode);

        Solution solution = new Solution();
        System.out.println(solution.deleteNode(headNode, 5));
    }

    static class Solution {
        /**
         * 方法一：比较法
         * 节点不为空且不满足条件继续往下走，注意边界条件
         *
         * 时间复杂度 O(n)
         */
        public ListNode deleteNode(ListNode head, int val) {
            // 零个节点的情况
            if (head == null) {
                return null;
            }

            // 一个节点的情况
            if (head.val == val) {
                return head.next;
            }

            ListNode cur = head;
            // 节点不为空且不满足条件继续往下走
            while (cur.next != null && cur.next.val != val) {
                cur = cur.next;
            }
            if (cur.next != null) {
                cur.next = cur.next.next;
            }

            return head;
        }
    }
}

