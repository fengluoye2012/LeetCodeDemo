package com.leetcode.demo.simple.linkedList;

//请判断一个链表是否为回文链表。
public class IsPalindromeLinkedList {

    public static void test() {
        ListNode listNode = ListNode.create();
        isPalindrome(listNode);
    }

    //1->2->3->4
    //2->1
    //反转之后，判断每个节点的value是否相等；
    public static boolean isPalindrome(ListNode head) {
        //反转单链表
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        //判断是否为回文链表
        while (head != null && pre != null) {
            if (head.val != pre.val) {
                return false;
            }
            head = head.next;
            pre = pre.next;
        }
        return true;
    }
}
