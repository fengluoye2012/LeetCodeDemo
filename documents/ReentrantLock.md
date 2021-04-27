**ReentrantLock**的源码分析

由于synchronized是java关键字，无法通过阅读源码深入学习实现线程安全或者阻塞的原理和过程，可以通过分析**ReentrantLock**如何实现线程安全和阻塞的原理，更加深入理解学习。

可以带着一下问题去思考：

- 线程A持有锁，其他线程等待着抢占锁，等待线程如何处理呢？
- 线程A释放锁后，以哪种顺序从等待队列唤醒等待线程？
- 重入锁如何实现的，lock()和unlock()方法为什么成对出现？
- 使用Codition的await()、signal()/signalAll()方法，如何实现线程间通信的？

#### 线程状态切换

线程状态分别为：

**NEW**：创建状态：创建线程了，还没有开始调用start()方法；

**RUNNABLE**：可执行状态：调用了start()方法或者正在被Java虚拟机执行，实际上可以分为等待执行状态和正在执行中；

**BLOCKED**：阻塞状态：1）等待其他线程释放锁，进入同步代码块；2）调用了Object类的wait()方法、Codition类的await()方法后，再次进入同步代码块；

**WAITING**：等待状态：分为以下三种情况：1）调用了**Object**类的wait()方法、**Codition**类的await()方法，没有等待超时；2）调用了**Thread**的join()方法没有等待超时，常见情况线程A调用join()方法，等待线程B执行完成再执行；3）调用了**LockSupport**的park()方法，该方法在下文中经常看到；

**TIMED_WAITING**：指定时间的等待状态，分为以下几种：1）调用了**Thread**的sleep()方法；2）调用了**Object**的wait(long) 方法；3）调用**Thread**的join(long)方法；4）调用**LockSupport**的parkNanos()方法；5）调用**LockSupport**的parkUntil()方法；

**TERMINAED**：终止状态，线程执行完成；

![线程状态切换](/Users/mac/Desktop/AppDemo/LeetCodeDemo/documents/线程状态切换.png)

#### CAS原理

主要原理是线程工作内存在对变量进行修改时，先从主内存中（内存地址V）拷贝一份到工作内存中（旧的预期值A），在工作内存中重新拷贝一份进行修改（要修改的新值B），修改完成之后，写入到主内存时，先判断内存地址V和旧的预期值A是否一致，一致则表示没有其他线程修改过，线程安全，将要修改的新值B写入到主内存中。

详细问题可参考其他文章详解；

#### **ReentrantLock**

**ReentrantLock**实现锁依赖于**队列（双向链表AQS）**和**CAS**来实现的；主要提供对外暴露方法，主要包含以下三个类：

- 抽象的Sync继承AQS(AbstractQueuedSynchronizer)是非公平锁NonfairSync和公平锁FairSync的父类；

  - ```java
    final boolean nonfairTryAcquire(int acquires) {}
    protected final boolean tryRelease(int releases) {}
    ```

- 非平锁NonfairSync重写了lock和tryAcquire()方法；线程A释放锁时，新线程提交，会抢占锁，不按照等待顺序执行；

- 公平锁FairSync重写了lock和tryAcquire()方法；按照线程等待时间顺序执行；

**ReentrantLock**锁的特性

- 手动调用lock和unlock 方法，lock和unlock()方法成对出现；尤其是unlock()放在finally方法中执行，防止异常造成死锁；
- 可主动终止；
- 重入锁：当前线程持有锁，再次获取锁直接将state加一，不会阻塞自己；

#### AbstractQueuedSynchronizer(AQS双向链表)

**AQS**提供阻塞锁和其他相关的锁(如：semaphores、CountDownLatch)的基础框架，**AQS**依赖于原子类来实现状态改变（如更新state的value、设置头节点、尾节点、设置节点的waitStatus状态、设置下一个节点操作都是CAS来实现的保证线程安全）。

```java
//lock流程主要方法
public final void acquire(int arg) {}
protected boolean tryAcquire(int arg) {}
private Node addWaiter(Node mode) {}
private Node enq(final Node node) {}
final boolean acquireQueued(final Node node, int arg) {}
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {}
private final boolean parkAndCheckInterrupt() {}
private void cancelAcquire(Node node) {}
//unlock流程主要方法
public final boolean release(int arg) {}
private void unparkSuccessor(Node node) {}
```



#### AQS静态内部类Node

```java
static final class Node {
   
    //线程的状态：默认值为0，如是否取消、等待唤醒等状态
    volatile int waitStatus;

    //上一个节点
    volatile Node prev;

    //下一个节点
    volatile Node next;

    //将被执行的线程；
    volatile Thread thread;

    //下一个nextWaiter节点；分为share的模式和抢占模式；
    Node nextWaiter;

    Node() {    // Used to establish initial head or SHARED marker
    }
    
    //addWaiter()方法初始化node调用的构造函数；
    Node(Thread thread, Node mode) {     // Used by addWaiter
        this.nextWaiter = mode;
        this.thread = thread;
    }
    
    //
    Node(Thread thread, int waitStatus) { // Used by Condition
        this.waitStatus = waitStatus;
        this.thread = thread;
    }
}
```

#### lock流程

非公平锁的lock()方法

```java
final void lock() {
    //使用CAS判断预期值是否为0，如果主内存中的value和预期值相等，则更新为1；
    //主内存中的value为0，则表示没有线程持有锁，CAS操作成功，当前线程持有了锁，将被CPU调度执行；
    if (compareAndSetState(0, 1))
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}
```

**AbstractQueuedSynchronizer**的acquire()方法：1）其他线程不持有锁，则当前线程将持有锁，等待CPU调度执行；2）其他线程正在持有锁，当前线程阻塞，将被添加到队列中去；

- tryAcquire()：尝试获取锁，返回值true 表示持有了锁，等待执行；false表示被阻塞，将添加到等待队列中；

- addWaiter()：主要是将当前等待线程添加到队列中；返回值为当前添加到队列尾的线程节点；
- acquireQueued()：不断的判断当前线程对应的节点是否位于队列头并且获得锁，返回值true表示当前线程对应的节点位于队列头，并且获取到锁；

```java
public final void acquire(int arg) {
  //其他线程正在持有锁，当前线程无法获取锁，只能等待，则将当前线程添加到等待队列中；
  //不断的判断当前线程对应的节点是否位于队列头并且获得锁；
  if (!tryAcquire(arg) &&
      acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
    selfInterrupt();//自我中断
}
```

**AbstractQueuedSynchronizer**的tryAcquire()-->**NonfairSync**的tryAcquire()-->**ReentrantLock**内部类**Sync**的nonfairTryAcquire()：判断当前线程是否能够持有锁；

```java
//true 表示当前线程将持有锁；false则表示将被阻塞等待；
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    //没有其他线程持有锁，
    if (c == 0) {
        //通过CAS改变状态，主内存中的value和预期值相等，则set成功；当前线程持有了锁，将被cpu调度执行；
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    //当前线程再次获取锁（重复锁），将状态+1；
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```

**AbstractQueuedSynchronizer**的addWaiter()方法：将当前节点添加到队列尾，

```java
private Node addWaiter(Node mode) {
   //创建节点
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    //队列已经初始化；
    if (pred != null) {
        node.prev = pred;
        //使用CAS将node设置为尾节点；
        if (compareAndSetTail(pred, node)) {
            pred.next = node;
            return node;
        }
    }
    //将当前节点添加到队列中；
    enq(node);
    return node;
}
```

**AbstractQueuedSynchronizer**的enq()：将当前节点添加到队列中；

```java
private Node enq(final Node node) {
    for (;;) {
        Node t = tail;
        //tail节点为空，则初始化头节点和为节点；
        if (t == null) { // Must initialize
            if (compareAndSetHead(new Node()))
                tail = head;
        } else {
            //当前节点的prev设置为为节点；
            node.prev = t;
            //将当前节点设置为为节点；
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return t;
            }
        }
    }
}
```

**AbstractQueuedSynchronizer**的acquireQueued()方法：不断的判断节点nod的上一个节点是否为头节点并且能够获取锁；

返回值true表示node节点的上一个节点为头节点，并且当前线程获取到锁；

```java
//
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        //线程一直处于死循环中，直到队列头的线程获取到锁或者线程被取消执行；
        for (;;) {
            //获取当前节点(尾节点)的上一个节点（由于shouldParkAfterFailedAcquire()方法可能会删除已取消线程的节点，所以节点p可能发生变化）
            final Node p = node.predecessor();
            //等待队列中只有当前一个等待执行的任务，并且获取到锁；
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return interrupted;
            }
            //节点p中的waitStatus为Node.SIGNAL，并且节点p中的线程被中断；
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)
            //删除node节点之前的状态为CANCELLED的节点；
            //1）将waitStatus状态设置为CANCELLED，如果node为尾节点更新尾节点；
            //2）pred节点不是head节点，改变pred节点的waitStatus状态；否则唤醒节点的后续节点
            cancelAcquire(node);
    }
}
```

**AbstractQueuedSynchronizer**的shouldParkAfterFailedAcquire()方法：检查并且更新获取锁失败的节点状态；

- 1）已经处于waitStatus为Node.SIGNAL状态为直接返回true；否则返回false；
- 2）waitStatus大于0 表示线程被取消，从等待队列中删除；
- 3）其他情况则将waitStatus更新为Node.SIGNAL；

```java
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL)
        /*
         * This node has already set status asking a release
         * to signal it, so it can safely park.
         */
        return true;
    //根据ws状态为CANCELLED线程被取消，从等待队列中删除已取消的线程节点；
    if (ws > 0) {
        /*
         * Predecessor was cancelled. Skip over predecessors and
         * indicate retry.
         */
        do {
            node.prev = pred = pred.prev;
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else {
        /*
         * waitStatus must be 0 or PROPAGATE.  Indicate that we
         * need a signal, but don't park yet.  Caller will need to
         * retry to make sure it cannot acquire before parking.
         */
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
    }
    return false;
}
```



**AbstractQueuedSynchronizer**的parkAndCheckInterrupt()方法：阻塞线程并且判断是否中断；true表示线程被中断；

```java
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this);//阻塞线程
    return Thread.interrupted();//判断是否中断；
}
```

**ReentrantLock**的tryLock()方法分为几种情况：

- 1）没有其他线程持有锁，则当前线程持有锁，state状态加一，返回true；
- 2）当前线程持有了锁，状态加一，返回true;
- 3） 其他线程持有锁，当前等待一定时间内持有锁则返回true；否则被中断，返回false;

```java
public boolean tryLock(long timeout, TimeUnit unit)
        throws InterruptedException {
    return sync.tryAcquireNanos(1, unit.toNanos(timeout));
}
```

**AbstractQueuedSynchronizer**的tryAcquireNanos() 在一定时间内是否能够抢占锁；

```java
public final boolean tryAcquireNanos(int arg, long nanosTimeout)
        throws InterruptedException {
    //线程被中断则直接跑出异常；
    if (Thread.interrupted())
        throw new InterruptedException();
    //tryAcquire() 当前线程尝试抢占锁；
    return tryAcquire(arg) ||
        doAcquireNanos(arg, nanosTimeout);
}
```

**AbstractQueuedSynchronizer**的doAcquireNanos()：在一定时间内尝试抢占锁；

```java
private boolean doAcquireNanos(int arg, long nanosTimeout)
        throws InterruptedException {
    if (nanosTimeout <= 0L)
        return false;
    final long deadline = System.nanoTime() + nanosTimeout;
    //将当前线程添加到等待队列尾；
    final Node node = addWaiter(Node.EXCLUSIVE);
    boolean failed = true;
    try {
        for (;;) {
            //获取上一个节点；
            final Node p = node.predecessor();
            //如果节点p是头节点，尝试抢占锁；抢占成功则重新设置头节点，返回true;
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null; // help GC
                failed = false;
                return true;
            }
            
            nanosTimeout = deadline - System.nanoTime();
            //如果超时，直接返回false；
            if (nanosTimeout <= 0L)
                return false;
            //检查并且更新获取锁失败的节点状态，1）已经处于waitStatus为Node.SIGNAL状态为直接返回true；否则返回false；
            //2）waitStatus大于0 表示线程被取消，从等待队列中删除；
            //3）其他情况则将waitStatus更新为Node.SIGNAL；
            if (shouldParkAfterFailedAcquire(p, node) &&
                nanosTimeout > spinForTimeoutThreshold)
                LockSupport.parkNanos(this, nanosTimeout);
            if (Thread.interrupted())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

#### unlock() 流程

unlock() 流程：返回true 表示调state的值减为0，释放锁了，同时存在可唤醒的节点，唤醒节点中阻塞的线程，等待CPU调度；否则释放锁还在持有锁；

```java
public final boolean release(int arg) {
    //当前线程释放锁了；
    if (tryRelease(arg)) {
        Node h = head;
        //唤醒头节点的下一个节点中的线程，线程处于Runnable状态；可以被CPU调度；
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);
        return true;
    }
    return false;
}
```

tryRelease()：尝试释放锁，更新state状态，多次调用unlock()将state的值减到0，才释放锁；

```java
protected final boolean tryRelease(int releases) {
    int c = getState() - releases;
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    //由于Lock是可重入锁，只有状态清零后，才会释放锁；
    if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
    }
    //更新状态；
    setState(c);
    return free;
}
```

**AbstractQueuedSynchronizer**的unparkSuccessor() 唤醒节点的后续节点；

```java
private void unparkSuccessor(Node node) {
    /*
     * If status is negative (i.e., possibly needing signal) try
     * to clear in anticipation of signalling.  It is OK if this
     * fails or if status is changed by waiting thread.
     */
    int ws = node.waitStatus;
    if (ws < 0)
        compareAndSetWaitStatus(node, ws, 0);

    /*
     * Thread to unpark is held in successor, which is normally
     * just the next node.  But if cancelled or apparently null,
     * traverse backwards from tail to find the actual
     * non-cancelled successor.
     */
    Node s = node.next;
    //如果后续节点为空或者线程被取消；
    if (s == null || s.waitStatus > 0) {
        s = null;
        //从尾节点往前找，找到waitStatus不大于0的节点；
        for (Node t = tail; t != null && t != node; t = t.prev)
            if (t.waitStatus <= 0)
                s = t;
    }
    if (s != null)
        //解除阻塞状态，线程处于runnable状态；
        LockSupport.unpark(s.thread);
}
```

#### 公平锁FairSync的lock和unlock

**公平锁FairSync**的lock()流程和**非公平锁NonfairSync**的lock()流程基本一致，**不同点**在tryAcquire()方法中，调用lock()方法时，没有线程持有锁，**公平锁等待队列中为空，当前线程才会抢占锁**；**非公平锁直接抢占锁，不管等待队列是否为空**；

```java
final void lock() {
    acquire(1);
}
```

**FairSync**的tryAcquire()：公平锁等待队列中为空，当前线程才会抢占锁；如果当前线程已经持有锁，直接更新state状态；

```java
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    //没有线程持有锁；
    if (c == 0) {
        //先判断队列中是否存在等待节点；没有等待节点，使用CAS改变state状态成功，当前线程持有锁；
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    //lock是重入锁；当前线程已经持有锁，再次获取锁更新state状态，不阻塞自己；
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```

unlock()和**非公平锁NonfairSync**的unlock()流程完全一致。



#### Condition的await()和signal流程

**AQS内部类ConditionObject**主要是用来配合**ReentrantLock**实现线程间通信类似Synchronized和Object的wai t()和notify()、notifyAll()方法；主要方法有await()和signal()方法。

**AQS内部类ConditionObject**类中维持了一个condition队列（实际上是双向链表）；

```java
public class ConditionObject implements Condition, java.io.Serializable {
    private static final long serialVersionUID = 1173984872572414699L;
    /** First node of condition queue. */
    //头节点
    private transient Node firstWaiter;
    /** Last node of condition queue. */
    //尾节点
    private transient Node lastWaiter;
    
    //将线程由可执行状态切换为阻塞状态
    public final void await() throws InterruptedException {}
    
    //添加到等待队列中；
    private Node addConditionWaiter() {}
    
    //将线程由阻塞状态切换为可执行状态
    public final void signal() {}
  
    //从ConditionObject队列头找到第一个没有取消的线程唤醒，添加到AQS的队列中，等待CPU调度；
    private void doSignal(Node first) {}
   
    //如果节点waitStatus不为Node.CONDITION，说明线程已经被取消，返回false；否则将waitStatus状态设置为0；将节点添加到AQS的等待队列中，返回true；
    final boolean transferForSignal(Node node) {}
}
```



**AQS内部类ConditionObject**的await()主要1）将**ConditionObject**中waitStatus不等于Node.CONDITION的节点删除，将调用await()方法的线程添加到Condination队列尾；2）释放当前线程持有的锁；3）判断当前线程是否存在AQS的队列中，不存在，则阻塞，返回线程是否处于中断状态；如果中断，当前线程在AQS队列中返回THROW_IE（-1），不在队列中，返回REINTERRUPT（1）；

```java 
public final void await() throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
    //删除waitStatus不等于Node.CONDITION状态的节点，将等待线程添加到队列尾,；
    Node node = addConditionWaiter();
    //释放当前线程持有的锁，返回锁的状态；
    int savedState = fullyRelease(node);
    int interruptMode = 0;
    //节点node不存在AQS等待队列中；
    while (!isOnSyncQueue(node)) {
        //阻塞线程；
        LockSupport.park(this);
        //判断线程是否已经中断；1）已经中断，1.1）添加到AQS等待队列中 返回THROW_IE(-1);
        //1.2)没有添加到AQS等待队列中，返回REINTERRUPT(1);2)没有中断则返回0；
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
    }
    
    //没有将线程添加到AQS等待队列中,则不断的判断当前线程对应的节点是否位于队列头并且获得锁，
    //返回值true表示当前线程对应的节点位于队列头，并且获取到锁；具体参考lock流程；
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
  
    //删除Condination队列中waitStatus不为Node.CONDITION的节点；
    if (node.nextWaiter != null) // clean up if cancelled
        unlinkCancelledWaiters();
  
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
}
```

addConditionWaiter()：将等待线程添加到等待队列尾，返回尾节点；

```java
private Node addConditionWaiter() {
    Node t = lastWaiter;
    // If lastWaiter is cancelled, clean out.
    //清除waitStatus 不为Node.CONDITION的节点；
    if (t != null && t.waitStatus != Node.CONDITION) {
        //从头节点开始删除waitStatus不等于Node.CONDITION状态的节点；
        unlinkCancelledWaiters();
        t = lastWaiter;
    }
    
    //创建waitStatus为Node.CONDITION的节点；
    Node node = new Node(Thread.currentThread(), Node.CONDITION);
    //添加到节点中；
    if (t == null)
        firstWaiter = node;
    else
        t.nextWaiter = node;
    lastWaiter = node;
    return node;
}
```

**AQS的fullyRelease()方法**：释放锁，唤醒节点中阻塞的线程；如果释放锁失败，则取消线程；release()方法的解析具体可参考unlock()流程；

```java
final int fullyRelease(Node node) {
    boolean failed = true;
    try {
        int savedState = getState();
        //返回true 表示调state的值减为0，释放锁了，同时存在可唤醒的节点，唤醒节点中阻塞的线程，等待CPU调度；否则释放锁还在持有锁；
        if (release(savedState)) {
            failed = false;
            return savedState;
        } else {
            throw new IllegalMonitorStateException();
        }
    } finally {
        if (failed)
            node.waitStatus = Node.CANCELLED;
    }
}
```



**AQS**的isOnSyncQueue()：判断节点是否在**AQS**的队列中，**不同于Codination类维持的队列**；

```java
final boolean isOnSyncQueue(Node node) {
    //节点的waitStatus为Node.CONDITION 或者 prev节点为空；
    if (node.waitStatus == Node.CONDITION || node.prev == null)
        return false;
    //有后序节点肯定在AQS的队列中
    if (node.next != null) // If has successor, it must be on queue
        return true;
    
    //遍历AQS的等待队列，判断node是否存在;
    return findNodeFromTail(node);
}
```

**AQS**的checkInterruptWhileWaiting()：判断线程是否中断，没有中断直接返回0；已经中断了，1）waitStatus为Node.CONDITION，使用CAS更新为0，将线程添加到**AQS**等待队列中，返回THROW_IE（-1）；否则返回REINTERRUPT（1）；

```java
private int checkInterruptWhileWaiting(Node node) {
    return Thread.interrupted() ?
        (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
        0;
}
```

**AQS内部类ConditionObject**的await(long time, TimeUnit unit)方法主要流程和await()方法类似，主要的不同在于超过等待时间没有被唤醒，将当前线程添加到**AQS**等待队列中；

返回值false表示等待超时，也没有被主动唤醒；其他情况返回true

```java
public final boolean await(long time, TimeUnit unit)
        throws InterruptedException {
    //等待时间；
    long nanosTimeout = unit.toNanos(time);
    if (Thread.interrupted())
        throw new InterruptedException();
    //添加到Contition队列中；
    Node node = addConditionWaiter();
    //释放锁
    int savedState = fullyRelease(node);
    //等待时间；
    final long deadline = System.nanoTime() + nanosTimeout;
    boolean timedout = false;
    int interruptMode = 0;
  
    //不在AQS等待队列中，超时时，尝试添加到等待队列中；
    while (!isOnSyncQueue(node)) {
        //等待超时,1）直接更新状态为0，添加到AQS队列中等待队列中，timeout为true；
        //2）不在AQS队列中，将线程转换为可执行状态；返回false;
        if (nanosTimeout <= 0L) {
            timedout = transferAfterCancelledWait(node);
            break;
        }
      
        //等待时间超过1000纳秒，则让线程阻塞；
        if (nanosTimeout >= spinForTimeoutThreshold)
            LockSupport.parkNanos(this, nanosTimeout);
      
        //线程已经中断则直接退出；
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
        //剩余等待时间；
        nanosTimeout = deadline - System.nanoTime();
    }
    
   //interruptMode不为THROW_IE（没有将线程添加到AQS等待队列红），尝试获取锁；
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
  
    //删除Condination队列中waitStatus不为Node.CONDITION的节点；
    if (node.nextWaiter != null)
        unlinkCancelledWaiters();
  
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
    return !timedout;
}
```

signal()方法：只能唤醒一个调用过await()方法的线程，从ConditionObject队列头找到第一个没有取消的线程唤醒，添加到AQS的队列中，等待CPU调度；

```java
public final void signal() {
    //当前线程不持有锁，则抛出异常；
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
  
    //取出头节点；
    Node first = firstWaiter;
    //头节点不为空；
    if (first != null)
        doSignal(first);
}
```

doSignal()：只能唤醒一个调用过await()方法的线程，从**ConditionObject**队列头找到第一个没有取消的线程唤醒，添加到AQS的队列中，等待CPU调度；

```java
private void doSignal(Node first) {
    //
    do {
        //队列为空，将尾节点置null；
        if ( (firstWaiter = first.nextWaiter) == null)
            lastWaiter = null;
      
        first.nextWaiter = null;
      
      //头节点不为null;并且唤醒头节点中的线程失败，继续往下找；直到找到没有取消的线程唤醒；
    } while (!transferForSignal(first) &&
             (first = firstWaiter) != null);
}
```

transferForSignal()：如果节点waitStatus不为Node.CONDITION，说明线程已经被取消，返回false；否则将waitStatus状态设置为0；将节点添加到AQS的等待队列中，返回true；

```java
final boolean transferForSignal(Node node) {
    /*
     * If cannot change waitStatus, the node has been cancelled.
     */
    //如果waitStatus不为Node.CONDITION，说明线程已经被取消；
    if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
        return false;

    //将节点添加到AQS等待队列中；
    Node p = enq(node);
    int ws = p.waitStatus;
    //线程被取消或者没法设置为SIGNAL状态，唤醒线程；
    if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
        LockSupport.unpark(node.thread);
    return true;
}
```

signalAll()方法和signal()方法类似，主要区别在唤醒**ConditionObject**所有没有取消的等待线程；

```java
public final void signalAll() {
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
    Node first = firstWaiter;
    if (first != null)
        //内部遍历链表，依次调用transferForSignal()方法；
        doSignalAll(first);
}
```

doSignalAll() 遍历链表依次调用transferForSignal()方法，将所有没有被取消的线程添加到AQS队列中。

```java

private void doSignalAll(Node first) {
    //置空队列；
    lastWaiter = firstWaiter = null;
    do {
        Node next = first.nextWaiter;
        first.nextWaiter = null;
        transferForSignal(first);
        first = next;
    } while (first != null);
}
```

#### 结论

**ReentrantLock**使用**AQS**来时线程线程安全的，**AQS**内部以CAS来实现线程阻塞。

**ReentrantLock**锁的特性

- 手动调用lock和unlock 方法，lock和unlock()方法成对出现；尤其是unlock()放在finally方法中执行，防止异常造成死锁；
- 可手动终止
- 重入锁：当前线程持有锁，再次获取锁直接将state加一，不会阻塞自己；



以上就是**ReentrantLock**的主要流程，和相关代码分析。如有误差，请多指教，谢谢！