#### CountDownLatch

**CountDownLatch**主要用于：当前线程A等待一个或者多个线程执行完成之后，再执行；



**CountDownLatch**和**ReentrantLock**一样都是基于**AbstractQueuedSynchronizer(AQS)**实现线程阻塞的，**AQS**以**CAS**控制state状态类实现线程阻塞。



**CountDownLatch**主要方法方法比较简单，

```Java
private static final class Sync extends AbstractQueuedSynchronizer {}
private final Sync sync;

public CountDownLatch(int count) {
   if (count < 0) throw new IllegalArgumentException("count < 0");
   this.sync = new Sync(count);
}


```



构造函数中count进而设置state 重入锁

```java
public CountDownLatch(int count) {
    if (count < 0) throw new IllegalArgumentException("count < 0");
    this.sync = new Sync(count);
}
```

await()方法

```java
public void await() throws InterruptedException {
    sync.acquireSharedInterruptibly(1);
}
```

acquireSharedInterruptibly

```java
public final void acquireSharedInterruptibly(int arg)
        throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
        
    //state等于0，则返回1 表示state等于0，已经释放锁了，否则返回-1 表示当前线程依旧持有锁；
    if (tryAcquireShared(arg) < 0)
        doAcquireSharedInterruptibly(arg);
}
```

doAcquireSharedInterruptibly

```java
private void doAcquireSharedInterruptibly(int arg)
    throws InterruptedException {
    //将当前线程添加到AQS等待队列中;
    final Node node = addWaiter(Node.SHARED);
    boolean failed = true;
    try {
        for (;;) {
            //
            final Node p = node.predecessor();
            if (p == head) {
                int r = tryAcquireShared(arg);
                if (r >= 0) {
                    setHeadAndPropagate(node, r);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
            }
            if (shouldParkAfterFailedAcquire(p, node) &&
                parkAndCheckInterrupt())
                throw new InterruptedException();
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

