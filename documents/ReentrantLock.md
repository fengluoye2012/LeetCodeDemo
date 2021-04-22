**ReentrantLock**的流程



线程状态切换

CAS原理

公平锁和非公平锁





**ReentrantLock**实现锁依赖于**CAS**和**队列（双向链表AQS）**来实现的；



#### 非公平锁：

非公平锁：在线程释放锁时，线程A刚提交试图抢占锁，可能不会从等待队列中取出最先等待的线程执行；因为队列中的线程需要被唤醒才能被CPU调度；

lock流程

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

**AbstractQueuedSynchronizer**的shouldParkAfterFailedAcquire()方法：检查并且更新获取锁失败的节点状态，已经处于waitStatus为Node.SIGNAL状态为直接返回true；否则返回false；

waitStatus大于0 表示线程被取消，从等待队列中删除；其他情况则将waitStatus更新为Node.SIGNAL；

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

unlock() 流程

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