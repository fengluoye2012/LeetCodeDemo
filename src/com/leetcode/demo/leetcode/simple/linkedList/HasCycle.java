package com.leetcode.demo.leetcode.simple.linkedList;

import com.leetcode.demo.others.Node;

public class HasCycle {

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
        Node<Integer> node6 = new Node<Integer>(6);
        // node5.setNext(node6);
        node5.setNext(node3);

        boolean hasCycle = hasCycle3(node1);
    }

    //快慢指针，慢指针一次只走一步；快指针一次走两步；
    private static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (head != null) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
            head = head.next;
        }
        return false;
    }


    //1-2-3-4-5-3
    //快慢指针，慢指针一次只走一步  快指针一次走两步
    private static boolean hasCycle2(Node<Integer> head) {
        if (head == null || head.getNext() == null) {
            return false;
        }

        Node<Integer> slow = head.getNext();
        Node<Integer> fast = head.getNext().getNext();

        while (slow != null && fast != null && slow != fast) {
            slow = slow.getNext();
            if (fast.getNext() == null) {
                System.out.println("57 链表不存在环");
                return false;
            }
            fast = fast.getNext().getNext();
        }

        boolean hasCycle = slow != null && fast == slow;
        if (hasCycle) {
            System.out.println("slow = " + slow.getValue());
        } else {
            System.out.println("67 链表不存在环");
        }
        return hasCycle;
    }

    //1
    //1-2
    private static boolean hasCycle3(Node<Integer> head) {
        Node<Integer> slow = head;
        Node<Integer> fast = head;
        while (head != null) {
            if (fast == null || fast.getNext() == null) {
                System.out.println("链表无环");
                return false;
            }
            slow = slow.getNext();
            fast = fast.getNext().getNext();
            if (slow == fast) {
                System.out.println("slow = " + slow.getValue());
                return true;
            }
            head = head.getNext();
        }
        System.out.println("链表无环");
        return false;
    }
}
