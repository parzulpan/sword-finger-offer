package Java;

/**
 * @author parzulpan
 *
 * Definition for a parent binary tree node.
 */

public class TreeLinKNode {
    int val;
    TreeLinKNode left;
    TreeLinKNode right;
    TreeLinKNode parent;

    public TreeLinKNode(int val, TreeLinKNode left, TreeLinKNode right, TreeLinKNode parent) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "TreeLinKNode [" + (left != null ? "left=" + left + ", " : "")
                + (parent != null ? "parent=" + parent + ", " : "") + (right != null ? "right=" + right + ", " : "")
                + "val=" + val + "]";
    }
}
