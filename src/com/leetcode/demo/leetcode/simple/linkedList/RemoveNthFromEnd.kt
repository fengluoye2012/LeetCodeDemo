package com.leetcode.demo.leetcode.simple.linkedList

//给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
//你能尝试使用一趟扫描实现吗？
class RemoveNthFromEnd {

    companion object {
        fun test() {
            val node1 = ListNodeK.crateListNode()
            val head = removeNthFromEnd2(node1, 2)
            print(head.toString())
        }

        fun removeNthFromEnd3(head: ListNodeK?, n: Int): ListNodeK? {
            var now1 = head
            var length = 0
            while (now1 != null) {
                length++
                now1 = now1.next
            }

            var nowHead = ListNodeK(-1)
            nowHead.next = head
            now1 = nowHead
            for (index in 0 until length - n) {
                now1 = now1!!.next
            }
            now1!!.next = now1!!.next?.next
            return nowHead.next
        }

        //kotlin 效率更低
        //1-2-3-4-5-6  2
        //快慢指针，快指针先走n步，然后快慢一起走，直到快指针走到最后，要注意的是可能是要删除第一个节点，这个时候可以直接返回head -> next
        private fun removeNthFromEnd2(head: ListNodeK?, n: Int): ListNodeK? {
            var slow = head
            var fast = head

            //in [0,n]
            //in until [0,n)
            //快指针先走n步
            for (i in 0 until n) {
                fast = fast!!.next
            }

            //快指针为空，删除的是第一个节点
            if (fast == null) {
                return head?.next
            }

            //slow的下一个节点就是要删除的节点
            while (fast!!.next != null) {
                fast = fast.next
                slow = slow!!.next
            }

            slow!!.next = slow.next!!.next
            return head
        }

        /**
         * 184ms
         * ? 和 !! 的执行效率 todo
         * 双指针  1-->2-->3-->4-->5    5   1   index = 4
         */
        private fun removeNthFromEnd(head: ListNodeK?, n: Int): ListNodeK? {
            var length = 0
            var tem = head;
            while (tem != null) {
                length++
                tem = tem.next
            }

            //删除节点的index;
            var deleteIndex = length - n
            if (deleteIndex == 0) {
                return head!!.next
            }

            tem = head
            while (tem!!.next != null) {
                //下一个节点就是要删除的节点
                if (deleteIndex == 1) {
                    tem.next = tem.next!!.next
                    break
                }
                tem = tem.next
                deleteIndex--
            }
            return head
        }
    }

}
