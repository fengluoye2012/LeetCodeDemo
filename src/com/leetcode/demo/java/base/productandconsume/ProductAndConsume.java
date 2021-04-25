package com.leetcode.demo.java.base.productandconsume;

import java.util.LinkedList;
import java.util.Random;

public class ProductAndConsume {

    private final Object object = new Object();
    private LinkedList<Integer> queue = new LinkedList<>();

    int index = 0;

    public void productAndConsume() {
        Thread product1 = new Thread(new ProductRunnable());
        Thread product2 = new Thread(new ProductRunnable());
        Thread consume1 = new Thread(new ConsumeRunnable());
        Thread consume2 = new Thread(new ConsumeRunnable());

        product1.start();
        product2.start();
        consume1.start();
        consume2.start();
    }

    private class ProductRunnable implements Runnable {
        @Override
        public void run() {
            product();
        }
    }

    private void product() {
        while (true) {
            synchronized (object) {
                if (queue.size() > 10) {
                    try {
                        System.out.println("队列满了");
                        object.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                index++;
                queue.addFirst(index);
                object.notifyAll();

                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class ConsumeRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                consume();
            }
        }

        private void consume() {
            synchronized (object) {
                if (queue.isEmpty()) {
                    try {
                        System.out.println("队列空了");
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!queue.isEmpty()) {
                    Integer integer = queue.removeLast();
                    System.out.println("integer:" + integer);
                }

                object.notifyAll();

                try {
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
