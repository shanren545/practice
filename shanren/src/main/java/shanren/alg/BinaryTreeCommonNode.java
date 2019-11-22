package shanren.alg;

/**
 * 找两个节点最近的公共祖先
 *
 * @author tanxianwen 2019年11月20日
 */
public class BinaryTreeCommonNode {

    public static void main(String[] args) {

    }

    /**
     * 二叉树无序
     */
    TreeNode findCommonNodeForPorQ(TreeNode parent, int p, int q) {
        if (null == parent) {
            return null;
        }
        if (parent.v == p || parent.v == q) {
            return parent;
        }
        TreeNode left = findCommonNodeForPorQ(parent.left, p, q);
        TreeNode right = findCommonNodeForPorQ(parent.right, p, q);
        if (null != left && null != right) {
            return parent;
        }
        return left == null ? right : left;
    }

    // 二叉树有序
    TreeNode findCommonNodeForPorQ2(TreeNode parent, int p, int q) {
        if (null == parent) {
            return null;
        }
        if (parent.v == p || parent.v == q) {
            return parent;
        }

        if (parent.v < p && parent.v > q) {
            return parent;
        }
        if (parent.v > p && parent.v < q) {
            return parent;
        }
        TreeNode left = findCommonNodeForPorQ2(parent.left, p, q);
        if (null != left) {
            return left;
        }
        TreeNode right = findCommonNodeForPorQ2(parent.right, p, q);
        if (null != right) {
            return right;
        }
        return null;
    }

    // 二叉树有序,非递归
    TreeNode findCommonNodeForPorQ3(TreeNode root, int p, int q) {
        if (null == root) {
            return null;
        }
        TreeNode node = root;
        while (null != node) {
            if (node.v < p && node.v < q) {
                node = node.left;
            } else if (node.v > p && node.v > q) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    public static class TreeNode {
        public TreeNode left;
        public TreeNode right;
        public int v;

    }


}
