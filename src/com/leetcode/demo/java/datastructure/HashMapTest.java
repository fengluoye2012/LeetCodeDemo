package com.leetcode.demo.java.datastructure;

/**
 * 泛型：编译时转换为Object类型，在运行时确定具体类型；
 *
 * 数组的查找效率为什么高 O(1) 是如何实现查找的 不通过遍历直接找到对应的index，如何直接找到呢？
 *   - 数组通过index查找的效率为O(1) 是因为1）数组的内存空间是连续的;2）在c/c++中 存在数组的指针(第一个元素的指针)，
 *     找到第N个元素时，直接在数组指针+n*单位偏移数 就是第N个节点的开始指针 通过内存，读取出对应的value，所以查找效率高；
 *
 * HashMap(1.8)：
 *  - 结构:数组+单链表/红黑树(单链表长度大于8，并且数组长度大于64)
 *  - put流程:1）hash 对数组长度取模，找到对应index，对应value为空，则直接插入
 *           2) 判断是否存在相同key,存在则update value;不存在，往队列尾添加/或者判断是否转换为红黑树
 *              (链表长度大于8，并且数组长度大于64；数组长度小于64，扩容，调用resize())；
 *           3) 扩容，遍历，1）没有hash冲突，直接计算index，插入;
 *                        2）链表拆分，以hash & oldCap == 0作为判断条件拆分两部分：分别为j和j+oldCap 两个index；
 *                          例如：hash=16 oldCap = 16 oldIndex=0 newCap = 32  newIndex =16; todo 存在拆分后的两部分链表长度>0吗
 *                        3)）红黑树拆分：和链表拆分判断条件相同；树小于6则转换为数组，否则转为红黑树；
 *
 *  - HashMap的数组长度为什么一定是2的次幂？让hash分布更均匀，最主要体现在扩容时hash冲突index的计算和链表的拆分；
 *    https://www.cnblogs.com/ysocean/p/9054804.html
 *
 * ConcurrentHashMap:
 *    put:死循环，1）index 对应的value 为null,通过CAS 添加元素；
 *        2）是否在扩容中，参与扩容
 *        3）存在hash冲突，对Header加锁，3.1)链表：判断是否存在key,存在update value；不存在则往队列尾插入；
 *          如果链表超过8，数组长度小于64则扩容，否则树化；3.2) 红黑树：插入到红黑树
 *
 *
 *   - 1.8和1.7 的区别
 *    - 1.7 ReentrantLock+Segment+HashEntry
 *    - 1.8 synchronized+CAS+HashEntry+红黑树,用 Synchronized + CAS 代替 Segment ，这样锁的粒度更小了，并且不是每次都要加锁了，CAS尝试失败了在加锁。
 *
 *
 * ArrayMap/SpareArray:替代HashMap 用两个数组的实现
 * 线程安全的数据结构：Vector、HashTable、ConcurrentHashMap、ConcurrentLinkedQueue(无锁队列)、
 * CurrentLinkedHashMap、
 */
public class HashMapTest {
}