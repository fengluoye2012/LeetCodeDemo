package com.leetcode.demo.java.base.thread;

import static java.lang.Thread.sleep;

public class SynchronizedTest {


    public void test1() {
        synchronized (this) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test1");
        }
    }

    public void test2() {
        synchronized (SynchronizedTest.class) {
//            try {
//                sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("test2");
        }
    }
}
