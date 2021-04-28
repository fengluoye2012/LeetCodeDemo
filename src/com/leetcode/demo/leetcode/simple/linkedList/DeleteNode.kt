package com.leetcode.demo.leetcode.simple.linkedList

//请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点。传入函数的唯一参数为 要被删除的节点 。
class DeleteNode {

    //静态方法
    companion object {
        fun test() {
            val node1 = ListNodeK.crateListNode()
            deleteNode(node1.next)
        }

        private fun deleteNode(node: ListNodeK?) {
            node!!.`val` = node.next!!.`val`
            node.next = node.next!!.next

            println(node.toString())
        }
    }


}