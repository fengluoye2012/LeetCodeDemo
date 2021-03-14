package com.leetcode.demo.base;

import com.leetcode.demo.Node;

//反转单链表
public class ReverseSingleLinkedList {

    public static void test() {
        Node<Integer> node1 = new Node<Integer>(1);
        Node<Integer> node2 = new Node<Integer>(2);
        node1.setNext(node2);
        Node<Integer> node3 = new Node<Integer>(3);
        node2.setNext(node3);
        Node<Integer> node4 = new Node<Integer>(4);
        node3.setNext(node4);
        Node<Integer> node5 = new Node<Integer>(5);
        node4.setNext(node5);
        node5.setNext(null);

        Node<Integer> reverse = reverseRecursion2(node1);
        printLinkedList(reverse);
    }


    //反转单链表--遍历法
    public static Node<Integer> reverse(Node<Integer> head) {

        Node<Integer> pre = null;
        Node<Integer> cur = null;
        while (head != null) {
            pre = cur;//null 1 2 3 4
            cur = head;//1,  2 3 4 5
            head = head.getNext();//先调用，否则链表 只打印一条数据就变成空
            cur.setNext(pre);
        }
        return cur;
    }

    public static Node<Integer> reverse2(Node<Integer> head) {
        Node<Integer> pre;
        Node<Integer> cur = null;

        while (head != null) {
            pre = cur;
            cur = head;
            head = head.getNext();
            cur.setNext(pre);
        }
        return cur;
    }

    //curNode    head
    //1-null     2-3-4-5
    //2-1-null   3-4-5
    //3-2-1-null 4-5
    //遍历法反转单链表
    public static Node<Integer> reverse3(Node<Integer> head) {
        Node<Integer> nextNode = null;
        Node<Integer> curNode = null;
        while (head != null) {
            nextNode = curNode;
            curNode = head;
            head = head.getNext();
            curNode.setNext(nextNode);
        }
        return curNode;
    }

    //递归相当于入栈
    //5 4 3 2 1
    public static Node<Integer> reverseRecursion(Node<Integer> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        System.out.println("head = " + head.getValue());
        Node<Integer> tem = head.getNext();
        Node<Integer> newHead = reverseRecursion(head.getNext());
        tem.setNext(head);
        head.setNext(null);
        return newHead;
    }

    //递归相当于入栈 5 4 3 2 1
    //5-4
    //5-4-3
    //5-4-3-2
    //5-4-3-2-1
    public static Node<Integer> reverseRecursion2(Node<Integer> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Node<Integer> tem = head.getNext();
        Node<Integer> newHead = reverseRecursion2(head.getNext());
        tem.setNext(head);
        head.setNext(null);

        return newHead;
    }

    public static void printLinkedList(Node<Integer> head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.getValue()).append("-");
            head = head.getNext();
        }
        System.out.println("sb = " + sb.toString());
    }
}
