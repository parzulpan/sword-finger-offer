package Java;

import java.util.HashMap;

/**
 * @author parzulpan

【剑指 Offer】07.重建二叉树

输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。

示例：
前序遍历 preorder = [3,9,20,15,7]
中序遍历 inorder = [9,3,15,20,7]
返回如下的二叉树：

    3
   / \
  9  20
    /  \
   15   7

限制：
0 <= 节点个数 <= 5000

 */

public class Solution07 {
    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        Solution07 s = new Solution07();
        TreeNode t = s.buildTree(preorder, inorder);
        System.out.println(t);
    }

    /**
     * 方法一：递归重建
     * 从前序遍历中找到根节点位置，然后得到其左右子树，最后递归重建
     */
    int [] preorder;
    HashMap<Integer, Integer> map = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        for (int i = 0; i < inorder.length; ++i) {
            map.put(inorder[i], i);
        }

        return reBuild(0, 0, inorder.length - 1);
    }

    public TreeNode reBuild(int root, int left, int right) {
        if (left > right) {
            return null;
        }

        // 建立根节点
        TreeNode node = new TreeNode(preorder[root]);
        int i = map.get(preorder[root]);

        // 划分根节点、左右子树
        node.left = reBuild(root + 1, left, i - 1);
        node.right = reBuild(root + 1 + i - left, i + 1, right);
        return node;
    }
}


