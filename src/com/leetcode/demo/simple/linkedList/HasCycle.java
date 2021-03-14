package com.leetcode.demo.simple.linkedList;

public class HasCycle {
    //快慢指针，慢指针一次只走一步；快指针一次走两步；
    public boolean hasCycle(ListNode head) {
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
}
