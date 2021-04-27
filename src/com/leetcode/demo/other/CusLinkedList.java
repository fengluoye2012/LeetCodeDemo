package com.leetcode.demo.other;

/**
 * 实现双向链表删除某个节点 好未来
 *
 * @param <T>
 */
public class CusLinkedList<T> {

    //头节点
    private Node<T> head;

    //
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            return;
        }

        Node<T> tem = head;
        while (tem.next != null) {
            tem = tem.next;
        }

        tem.next = newNode;
        newNode.pre = tem;
    }

    /**
     * 删除指定元素对应的节点
     *
     * @param value
     */
    public void remove(T value) {

        //先查找是否存在
        Node<T> tem = head;

        while (tem != null) {
            //存在
            if ((tem.value == null && value == null) || (value != null && value.equals(tem.value))) {
                //删除节点
                remove(tem);
                break;
            }
            tem = tem.next;
        }
    }

    //删除当前节点
    private void remove(Node<T> node) {
        if (node == head) {
            //只有一个头节点；
            if (node.next == null) {
                head = null;
                return;
            }

            //重新设置头节点；
            head = node.next;
            head.pre = null;
            return;
        }

        //尾节点
        if (node.next == null) {
            node.pre.next = null;
            return;
        }

        //将下一个节点的往前移动即可；
        node.pre.next = node.next;
        node.value = node.next.value;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "当前链表为空";
        }

        StringBuilder sb = new StringBuilder();
        Node<T> tem = head;
        while (tem != null) {
            sb.append(tem.value).append("-");
            tem = tem.next;
        }
        return sb.toString();
    }


    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    static class Node<T> {
        public T value;
        public Node<T> pre;
        public Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
