package com.leetcode.demo.java.base.thread;

public class ThreadInterrupt {

    public void test() {
        MyThread thread = new MyThread();
        thread.start();
        thread.cancel();
    }


    private class MyThread extends Thread {
        private volatile boolean cancel = false;

        @Override
        public void run() {
            int i = 0;
            while (i < 100 && !cancel) {
                System.out.println("i=" + i);
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }

        public void cancel() {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cancel = true;
            //interrupt();//只是发送一个请求，不会立刻中断
        }
    }
}
