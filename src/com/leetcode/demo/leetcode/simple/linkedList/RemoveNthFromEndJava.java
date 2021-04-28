package com.leetcode.demo.leetcode.simple.linkedList;

public class RemoveNthFromEndJava {

    public ListNodeK removeNthFromEnd(ListNodeK head, int n) {
        if (head == null) {
            return null;
        }
        ListNodeK slow = head;
        ListNodeK fast = head;

        for (int i = 0; i < n; i++) {
            fast = fast.getNext();
        }

        if (fast == null) {
            return head.getNext();
        }

        while (fast.getNext() != null) {
            fast = fast.getNext();
            slow = slow.getNext();
        }
        slow.setNext(slow.getNext().getNext());
        return head;
    }
}
