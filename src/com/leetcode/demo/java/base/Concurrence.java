package com.leetcode.demo.java.base;

/**
 * 并发编程:https://www.codercc.com/backend/basic/juc/
 *         https://zhuanlan.zhihu.com/p/25577863
 * Java内存模型：
 * 并发编程：
 * 1、可见性、有序性、原子性
 * 2、volatile:可见性、有序性
 * 3、实现线程安全：
 *    - 互斥同步（阻塞同步）：
 *      synchronized：Java关键字，在对象头标记阻塞状态，自动加锁和释放，无法主动中断，使用的是非公平锁；配合Object的wait()、notify() 实现线程间通信；
 *      ReentrantLock：Java类，手动的调用lock()、unlock()方法，可手动中断；处理不当可能会造成死锁，可以设置公平锁、非公平锁；配合Condition的await()、signal()实现线程间通信；
 *
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
 *
 *    - synchronized、Lock是可重入锁：当前线程已经持有锁，再次获取锁，状态增加，不会出现自己阻塞自己的情况；
 *    - 公平锁：公平锁按照任务提交时间顺序执行；
 *    - 非公平锁：抢占式，其他线程任务等待时间长；cpu正在执行当前线程任务，在等待的线程处于休眠状态，当前任务执行完成后，恰好有个线程提交，可能会执行刚提交的线程；
 *
 *    - synchronized的线程屏障 todo
 *
 *    - ReentrantLock原理：https://crossoverjie.top/2018/01/25/ReentrantLock/ todo
 *
 *      - AbstractQueuedSynchronizer(AQS)内存在双向链表，将等待执行的线程添加到队列中，先进先出；
 *      - 公平锁：调用lock时判断是否为阻塞状态，1）非阻塞状态，链表中是否存在已等待的线程任务，不存在，使用CAS改变状态，；存在的话，将当前任务插入队列尾，取出；
 *      2）阻塞状态，判断是否同一个线程，是,状态加1；
 *
 * 4、synchronized对象锁和类锁的区别？
 *
 *   - 同一个锁同一个时间点只能有一个线程持有，其他线程等待；其他线程可以访问普通方法；
 *   - 类锁（synchronized修饰的静态方法）：类中同时存在两个类锁方法；线程A持有锁时，其他线程访问类锁方法等待；
 *   - 对象锁（synchronized 修饰的非静态方法）：类中同时存在两个类锁方法；线程A持有锁时，其他线程访问对象锁方法等待；
 *   - 类锁和对象锁是不同的锁：类中存在类锁和对象锁，不同线程可以同时访问这两个方法；两个方法是异步执行；
 *   - 类锁对同一个类中的同一个锁互斥；对象锁不同对象之间不存在互斥；
 *   - 不同的锁不存在阻塞关系
 *
 */
public class Concurrence {

    public volatile int a =10;

}
