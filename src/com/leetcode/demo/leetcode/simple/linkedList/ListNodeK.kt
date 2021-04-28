package com.leetcode.demo.leetcode.simple.linkedList

class ListNodeK(var `val`: Int) {
    var next: ListNodeK? = null

    companion object {
        fun crateListNode(): ListNodeK {
            val node1 = ListNodeK(1)
            val node2 = ListNodeK(2)
            node1.next = node2
            val node3 = ListNodeK(3)
            node2.next = node3
            val node4 = ListNodeK(4)
            node3.next = node4

            val node5 = ListNodeK(5)
            node4.next = node5
            return node1
        }
    }

    override fun toString(): String {
        return "ListNode(`val`=$`val`, next=$next)"
    }
}