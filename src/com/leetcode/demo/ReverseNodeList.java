package com.leetcode.demo;

import java.util.Stack;

public class ReverseNodeList {

    public static void test() {
        Node<Integer> headNode = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        headNode.setNext(node2);

        Node<Integer> node3 = new Node<>(3);
        node2.setNext(node3);

        Node<Integer> node4 = new Node<>(4);
        node3.setNext(node4);

        Node<Integer> node5 = new Node<>(5);
        node4.setNext(node5);

        reverseNodeList(headNode);
//        reverseNodeListByStack(headNode);
    }

    private static void printNode(Node<Integer> node) {
        StringBuilder stringBuilder = new StringBuilder();
        while (node != null) {
            stringBuilder.append(node.getValue()).append('-');
            node = node.getNext();
        }
        System.out.println(stringBuilder.toString());
    }

    //递归 就相当于入栈，先进后出    5  4 3  2  1
    private static Node<Integer> reverseNodeList(Node<Integer> headNode) {
        if (headNode == null || headNode.getNext() == null) {
            return headNode;
        }
        Node<Integer> tem = headNode.getNext();
        Node<Integer> newHead = reverseNodeList(headNode);
        tem.setNext(headNode);
        headNode.setNext(null);
        return newHead;
    }
}
