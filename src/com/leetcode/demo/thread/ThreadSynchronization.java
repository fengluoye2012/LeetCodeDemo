package com.leetcode.demo.thread;

public class ThreadSynchronization {

    private final Object lock = new Object();

    public void threadSynchronization() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronized (lock) {
                        //notify（）也必须在同步方法或同步代码块中调用，用来唤醒等待该对象的其他线程，
                        //notify方法调用后，当前线程不会立刻释放对象锁，要等到当前线程执行完毕后再释放锁
                        lock.notifyAll();
                        System.out.println(Thread.currentThread().getName() + "-" + i);
                        try {
                            //wait方法只能在同步方法或同步代码块中使用，而且必须是内建锁。wait方法调用后立刻释放对象锁；
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread threadA = new Thread(runnable, "A");
        Thread threadB = new Thread(runnable, "B");
        threadA.start();
        threadB.start();
    }
}
