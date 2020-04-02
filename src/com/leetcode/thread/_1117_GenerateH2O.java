package com.leetcode.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class _1117_GenerateH2O {

    // 1. 只用h和o信号量 打印出来只能是HHO
    private Semaphore h = new Semaphore(2);
    private Semaphore o = new Semaphore(0);

    public _1117_GenerateH2O() {
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        h.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        o.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        o.acquire(2);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        h.release(2);
    }

    // 2. CyclicBarrier + Semaphore //当达到屏障的时候释放所有信号量
    static class H2O {

        private CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                semaphoreH.release(2);
                semaphoreO.release();
            }
        });

        private Semaphore semaphoreH;
        private Semaphore semaphoreO;

        public H2O() {
            semaphoreH = new Semaphore(3);
            semaphoreO = new Semaphore(1);
        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            semaphoreH.acquire();
            // releaseHydrogen.run() outputs "H". Do not change or remove this line.
            releaseHydrogen.run();
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            semaphoreO.acquire();
            // releaseOxygen.run() outputs "O". Do not change or remove this line.
            releaseOxygen.run();
            try {
                barrier.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }



    static class H2O1 {

        private Semaphore s1,s2,s3,s4;

        public H2O1() {
            s1 = new Semaphore(2); // H线程信号量
            s2 = new Semaphore(1); // O线程信号量

            s3 = new Semaphore(0); // 反应条件信号量
            s4 = new Semaphore(0); // 反应条件信号量
        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            s1.acquire(); // 保证只有2个H线程进入执行
            s3.release(); // 释放H原子到达信号
            s4.acquire(); // 等待O原子到达
            releaseHydrogen.run();
            s1.release(); // 相当于唤醒1个H线程
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            s2.acquire(); // 保证只有1个O线程进入执行
            s4.release(2); // 释放O原子到达信号，因为有2个H线程等待所以释放2个
            s3.acquire(2); // 等待H原子到达，2个原因同上
            releaseOxygen.run();
            s2.release(); // 相当于唤醒1个O线程
        }
    }

    public static void main(String[] args) {

//        H2O h2o = new H2O();
        _1117_GenerateH2O h2o = new _1117_GenerateH2O();
//        H2O1 h2o = new H2O1();
        int n = 20;

        new Thread(() -> {
            try {
                for (int i = 0; i < n; i++) {
                    h2o.hydrogen(() -> System.out.println("H"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < n; i++) {
                    h2o.hydrogen(() -> System.out.println("H"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < n; i++) {
                    h2o.oxygen(() -> System.out.println("O"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
