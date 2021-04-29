package com.leetcode.demo.java.base.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

//CountDownLatch:线程A等待 一个或者多个线程执行完成后，再执行；
public class CountDownLatchTest {


    public static void test() {
        int n = 5;
        CountDownLatch doneSignal = new CountDownLatch(n);

        for (int i = 0; i < n; ++i) // create and start threads
            Executors.newCachedThreadPool().execute(new Worker(doneSignal, i));

        try {
            System.out.println("主线程调用await()");

            doneSignal.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("主线程等待所有子线程操作完成后再执行");
    }

    static class Worker implements Runnable {
        CountDownLatch doneSignal;
        int index;

        public Worker(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.index = i;
        }

        @Override
        public void run() {
            doWork();
            doneSignal.countDown();
        }

        public void doWork() {
            try {
                Thread.sleep(200);
                System.out.println("index == " + index);
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
