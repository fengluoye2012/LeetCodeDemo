package com.leetcode.demo.leetcode.simple.linkedList;

//反转单链表
public class ReverseNoeList3 {

    public static void test() {
        ListNodeK head = ListNodeK.Companion.crateListNode();
        ListNodeK listNodeK = reverseList3(head);
        if (listNodeK != null) {
            System.out.println(listNodeK.toString());
        }
    }

    //1-->2-->3-->4-->5

    //5 4 3 2 1
    //递归
    public static ListNodeK reverseList3(ListNodeK head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        ListNodeK pre = head.getNext();
        ListNodeK newHead = reverseList3(head.getNext());
        pre.setNext(head);
        head.setNext(null);
        return newHead;
    }

    //效率高  耗内存；
    //1-->2-->3-->4-->5
    public static ListNodeK reverseList(ListNodeK head) {
        ListNodeK cur = null;
        ListNodeK pre = null;

        while (head != null) {
            cur = new ListNodeK(head.getVal());
            cur.setNext(pre);
            pre = cur;
            head = head.getNext();
        }
        return cur;
    }

    //循环
    public static ListNodeK reverseList2(ListNodeK head) {
        ListNodeK pre = null;
        ListNodeK cur = head;

        while (cur != null) {
            ListNodeK next = cur.getNext();
            cur.setNext(pre);
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
