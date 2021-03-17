package com.leetcode.demo.thread;

public class ThreadSynchronizationDemo {

    public static void test() {
//        ThreadSynchronization threadSynchronization = new ThreadSynchronization();
//        threadSynchronization.threadSynchronization();

//        ThreadInterrupt threadInterrupt = new ThreadInterrupt();
//        threadInterrupt.test();

        SynchronizedTest synchronizedTest = new SynchronizedTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronizedTest.test1();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    synchronizedTest.test2();
                }
            }
        }).start();

    }
}
