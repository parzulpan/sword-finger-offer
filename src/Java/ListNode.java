package Java;

/**
 * @author parzulpan
 *
 * Definition for singly-linked list.
 */

public class ListNode implements Cloneable{
    int val;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int var) {
        this.val = var;
    }

    public ListNode(int var, ListNode next) {
        this.val = var;
        this.next = next;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
