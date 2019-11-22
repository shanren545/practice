package shanren.alg;

public class ReverseLinkList {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    // 以封装的方式
    public LinkList reverse(LinkList list) {
        if (null == list) {
            return null;
        }
        LinkList rt = new LinkList();
        Node node = list.head;
        while (null != node) {
            rt.insertFirst(node);
            node = node.next;
        }
        return rt;
    }

    // 不封装
    public Node reverse(Node head) {
        if (null == head) {
            return null;
        }
        Node node = head;
        head = null;
        while (null != node) {
            if (null == head) {
                head = node;
                node.next = null;
            } else {
                node.next = head;
                head = node;
            }
            node = node.next;
        }
        return head;
    }

    public static class LinkList {
        Node head;
        Node tail;

        public void insertFirst(Node node) {
            if (null == head) {
                head = node;
                node.next = null;
                tail = node;
                return;
            }
            node.next = head;
            head = node;
        }
    }
    public static class Node {
        int v;
        Node next;
    }

}
