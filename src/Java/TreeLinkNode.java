package Java;

/**
 * @author parzulpan
 *
 * Definition for a parent binary tree node.
 */

public class TreeLinkNode {
    int val;
    TreeLinkNode left;
    TreeLinkNode right;
    TreeLinkNode parent;

    public TreeLinkNode() {
    }

    public TreeLinkNode(int val) {
        this.val = val;
    }

    public TreeLinkNode(int val, TreeLinkNode left, TreeLinkNode right, TreeLinkNode parent) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "TreeLinkNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                ", parent=" + parent +
                '}';
    }
}
