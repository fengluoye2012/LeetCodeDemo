package com.leetcode.demo.simple.linkedList;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode create(){
        ListNode  node1 =new  ListNode(1);
        ListNode node2 =new ListNode(2);
        node1.next = node2;
        ListNode node3 =new ListNode(3);
        node2.next = node3;
        ListNode node4 =new ListNode(4);
        node3.next = node4;

        ListNode node5 =new ListNode(5);
        node4.next = node5;

        node5.next = node3;
        return node1;
    }
}
