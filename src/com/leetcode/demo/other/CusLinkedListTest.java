package com.leetcode.demo.other;

public class CusLinkedListTest {
    public static void test() {
        CusLinkedList<Integer> linkedList = new CusLinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
//        linkedList.add(null);
//        linkedList.add(3);
//        linkedList.add(4);
//        linkedList.add(5);

        linkedList.remove(4);

        System.out.println(linkedList.toString());
    }
}
