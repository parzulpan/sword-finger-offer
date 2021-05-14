package Java;

/**
 * @author parzulpan
 *
 * Definition for a binary tree node.
 */

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        return "TreeNode [" + (left != null ? "left=" + left + ", " : "")
                + (right != null ? "right=" + right + ", " : "") + "val=" + val + "]";
    }
}
