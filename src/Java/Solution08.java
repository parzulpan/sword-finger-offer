package Java;

/**
 * @author parzulpan

【剑指 Offer】08.二叉树的下一个节点

给定一颗二叉树和其中的一个节点，找出中序遍历序列的下一个节点。树中的节点除了有两个分别指向左右节点的指针，还有一个指向父节点的指针。

 */

public class Solution08 {
    public static void main(String[] args) {
        
    }

    /**
     * 方法一：回溯，分情况讨论
     * 1. 当前节点有右子树，则下一个节点为右子树的最左子节点，如果无左子节点，则为右子节点本身；
     * 2. 当前节点没有右子树，如果父节点的左子节点就是当前节点，则下一个节点为父节点；
     * 3. 当前节点没有右子树，且父节点的左子节点不是当前节点，则应往上溯，
     * 直到存在一个向上遍历的过程中存在一个节点的父节点的左子节点正好是该节点自身，则这个父节点就是下一个节点。
     */
    public TreeLinKNode getNext(TreeLinKNode node) {
        if (node == null) {
            return null;
        }

        // 1.
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        // 2. 返回一个父节点是它左子节点的节点
        while (node.parent != null) {
            if (node.parent.left == node) {
                return node.parent;
            }

            // 3. 回溯
            node = node.parent;
        }

        return null;
    }
}


