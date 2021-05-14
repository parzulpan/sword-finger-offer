package Java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author parzulpan

【剑指 Offer】06.从尾到头打印链表
输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

示例 1： 输入：head = [1,3,2] 输出：[2,3,1]

限制： 0 <= 链表长度 <= 10000
 */

public class Solution06 {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(3);
        ListNode n3 = new ListNode(2);
        n1.next = n2;
        n2.next = n3;
        n3.next = null;
        Solution06 s = new Solution06();
        System.out.println(Arrays.toString(s.reversePrint(n1)));
        System.out.println(Arrays.toString(s.reversePrint2(n1)));
    }

    ArrayList<Integer> tmp = new ArrayList<>();

    /**
     * 方法一：递归
     */
    public int[] reversePrint(ListNode head) {
        reCur(head);

        // int[] Integer[] List<Integer> 三种转换

        // int[] -> Integer[] 
        // Integer[] integers1 = Arrays.stream(data).boxed().toArray(Integer[]::new);
        // int[] -> List<Integer>
        // List<Integer> list1 = Arrays.stream(data).boxed().collect(Collectors.toList());

        // Integer[] -> int[]
        // int[] arr2 = Arrays.stream(integers1).mapToInt(Integer::valueOf).toArray();
        // Integer[] -> List<Integer>
        // List<Integer> list2 = Arrays.asList(integers1);

        // List<Integer> -> int[]
        // int[] arr1 = list1.stream().mapToInt(Integer::valueOf).toArray();
        // List<Integer> -> Integer[]
        // Integer[] integers2 = list1.toArray(new Integer[0]);

        return tmp.stream().mapToInt(Integer::valueOf).toArray();
    }

    void reCur(ListNode head) {
        if (head == null) {
            return;
        }
        reCur(head.next);
        tmp.add(head.val);
    }

    /**
     * 方法二：辅助栈
     */
    public int[] reversePrint2(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        int size = stack.size();
        int[] print = new int[size];
        for (int i = 0; i < size; ++i) {
            print[i] = stack.pop().val;
        }

        return print;
    }

}