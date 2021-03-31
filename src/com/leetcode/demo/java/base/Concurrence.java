package com.leetcode.demo.java.base;

/**
 * 并发编程:https://www.codercc.com/backend/basic/juc/
 *         https://zhuanlan.zhihu.com/p/25577863
 * Java内存模型：
 * 并发编程：
 * 1、可见性、有序性、原子性
 * 2、volatile:可见性、有序性
 * 3、实现线程安全：
 *    - 互斥同步（阻塞同步）：synchronized、ReentrantLock、
 *      闭锁(Latch、CountDownLatch )：一个线程等待其他线程执行完成之后在执行
 *      栅栏(CyclicBarrier)、 线程到达栅栏的位置时调用await方法，该线程会被阻塞，直到所有线程都到达栅栏位置。
 *      当所有线程都到达栅栏位置，那么栅栏将打开，此时所有线程都被释放，栅栏将被重置以便下次使用。
 *      信号量：
 *      exchanges:
 *    - 非阻塞同步：CAS、原子类；可以实现无锁队列（ConcurrentLinkedQueue）
 *      CAS的ABA问题（原子引用，添加版本号解决）和cpu耗时（虚拟机）
 *    - 模型本身就是安全的，无需同步：ThreadLocal 线程本地空间中，
 *      ThreadLocal原理：ThreadLocal hash对长度取模，找到对应下标，判断是否为相等
 *
 *      底层数据结构是数组，数组存放ThreadLocalMap，
 *      在插入过程中，根据ThreadLocal对象的hash值，定位到table中的位置i
 */
public class Concurrence {

    public volatile int a =10;

}
