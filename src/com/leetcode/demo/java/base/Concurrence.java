package com.leetcode.demo.java.base;

/**
 * 并发编程:https://www.codercc.com/backend/basic/juc/
 * Java内存模型：
 * 并发编程：
 * 1、可见性、有序性、原子性
 * 2、volatile:可见性、有序性
 * 3、实现线程安全：
 * 互斥同步（阻塞同步）：
 *     synchronized、ReentrantLock、闭锁(Latch、CountDownLatch)、栅栏(CyclicBarrier)
 * 非阻塞同步：CAS、原子类；可以实现无锁队列
 * 模型本身就是安全的，无需同步：
 * https://zhuanlan.zhihu.com/p/25577863
 */
public class Concurrence {

    public volatile int a =10;

}
