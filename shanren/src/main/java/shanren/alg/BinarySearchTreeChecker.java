package shanren.alg;


public class BinarySearchTreeChecker {



    private Integer pre;

    // 中序遍历法
    public boolean walkInorder(TreeNode parent) {
        if (null == parent) {
            return true;
        }

        boolean left = walkInorder(parent.left);
        if (!left) {
            return false;
        }

        if (null != pre && pre >= parent.v) {
            return false;
        }
        pre = parent.v;

        return walkInorder(parent.right);
    }

    // 递归判断法， 左子树的最大值小于root，右子树最小值大于root
    public Rt isValid(TreeNode parent) {
        if (null == parent) {
            return new Rt(true);
        }

        // isLeaf = parent.left == null && parent.right == null;
        // if (isLeaf) min= max = parent.v; return true

        Rt left = isValid(parent.left);
        if (!left.valid || left.max >= parent.v) {
            return new Rt(false);
        }

        Rt right = isValid(parent.right);
        if (!right.valid || right.min <= parent.v) {
            return new Rt(false);
        }

        int min = min(parent.v, left.min, right.min);
        int max = max(parent.v, left.max, right.max);

        return new Rt(true, min, max);
    }

    private int max(int v, Integer max, Integer max2) {
        // TODO Auto-generated method stub
        return 0;
    }

    private int min(int v, Integer min, Integer min2) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class Rt {


        public Rt(boolean valid, Integer min, Integer max) {
            super();
            this.valid = valid;
            this.min = min;
            this.max = max;
        }

        public Rt(boolean valid) {
            super();
            this.valid = valid;
        }

        boolean valid;
        Integer min;
        Integer max;
    }

    public static class TreeNode {
        public TreeNode left;
        public TreeNode right;
        public int v;

    }

}
