package Java;


/**
 * @author parzulpan
 *
 * 反转链表系列问题
 */
public class SolutionListReverse {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = null;

//        ListNode temp1 = null;
//        try {
//            temp1 = (ListNode) n1.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        ListNode temp2 = null;
//        try {
//            temp2 = (ListNode) n1.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }

//        System.out.println(n1);
//        System.out.println(new SolutionListReverse().reverseList(n1));
//        System.out.println();

        System.out.println(n1);
        System.out.println(new SolutionListReverse().reverseBetween(n1, 2, 4));
        System.out.println();
    }

    /** 反转链表 */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /** 从指定区间反转链表 */
    public ListNode reverseBetween(ListNode node, int start, int end) {
        ListNode listNode = new ListNode(0);
        listNode.next = node;
        ListNode prev = listNode;

        for (int i = 0; i < start - 1; i++) {
            prev = prev.next;
        }

        ListNode curr = prev.next;
        for (int i = 0; i < end - start; i++) {
            ListNode next = curr.next;
            curr.next = next.next;
            next.next = prev.next;
            prev.next = next;
        }

        return listNode.next;
    }
}

