package com.leetcode.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.IntConsumer;

public class _1195_FizzBuzz {

    // 1. synchronized 12ms
    private int n;
    private int start;
    private Object lock = new Object();

    public _1195_FizzBuzz(int n) {
        this.n = n;
        this.start = 1;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        synchronized (lock) {
            while (start <= n) {
                // 只能被3整除
                if (start % 3 == 0 && start % 5 != 0) {
                    printFizz.run();
                    start++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
            lock.notifyAll();
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        synchronized (lock) {
            while (start <= n) {
                // 只能被3整除
                if (start % 3 != 0 && start % 5 == 0) {
                    printBuzz.run();
                    start++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
            lock.notifyAll();
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        synchronized (lock) {
            while (start <= n) {
                // 只能被3整除
                if (start % 3 == 0 && start % 5 == 0) {
                    printFizzBuzz.run();
                    start++;
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
            lock.notifyAll();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        synchronized (lock) {
            while (start <= n) {
                // 只能被3整除
                if (start % 3 != 0 && start % 5 != 0) {
                    printNumber.accept(start++);
                    lock.notifyAll();
                } else {
                    lock.wait();
                }
            }
            lock.notifyAll();
        }
    }

    // 2. Semaphores 9ms
    static class FizzBuzz {

        // Initialize the permit, limit to one.
        private Semaphore sema = new Semaphore(1);
        // The current number.
        private int curNum = 1;
        private int n;

        public FizzBuzz(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            for(;;) {
                // Acquire the permit, try to run the logic exclusively.
                sema.acquire(1);

                try {
                    if (curNum > n) {
                        return;
                    }

                    if ((curNum % 3 == 0) && (curNum % 5 != 0)) {
                        printFizz.run();
                        curNum++;
                    }
                } finally {
                    // Release the permit anyway.
                    sema.release(1);
                }
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            for(;;) {
                sema.acquire(1);

                try {
                    if (curNum > n) {
                        return;
                    }

                    if ((curNum % 3 != 0) && (curNum % 5 == 0)) {
                        printBuzz.run();
                        curNum++;
                    }
                } finally {
                    sema.release(1);
                }
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for(;;) {
                sema.acquire(1);

                try {
                    if (curNum > n) {
                        return;
                    }

                    if ((curNum % 3 == 0) && (curNum % 5 == 0)) {
                        printFizzBuzz.run();
                        curNum++;
                    }
                } finally {
                    sema.release(1);
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            for(;;) {
                sema.acquire(1);

                try {
                    if (curNum > n) {
                        return;
                    }

                    if ((curNum % 3 != 0) && (curNum % 5 != 0)) {
                        printNumber.accept(curNum);
                        curNum++;
                    }
                } finally {
                    sema.release(1);
                }
            }
        }
    }

    // 3. CyclicBarrier 8ms
    // 是如何保证执行顺序的？？？
    // 因为每一个输出都要等四个线程全轮完，对于每个i只要一个方法满足条件会打印，其他会await, 但为什么时间反而更快呢？
    static class FizzBuzz2 {

        // 等待所有线程都阻塞后，一起唤醒
        private static CyclicBarrier barrier = new CyclicBarrier(4);

        private int n;
        public FizzBuzz2(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 3 == 0 && i % 5 != 0) {
                    printFizz.run();
                }
                try {
                    barrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 3 != 0 && i % 5 == 0) {
                    printBuzz.run();
                }
                try {
                    barrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 3 == 0 && i % 5 == 0) {
                    printFizzBuzz.run();
                }
                try {
                    barrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            for (int i = 1; i <= n; i++) {
                if (i % 3 != 0 && i % 5 != 0) {
                    printNumber.accept(i);
                }
                try {
                    barrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 4. AtomicInteger 9ms
    static class FizzBuzz3 {

        // Initialize the flag.
        private AtomicInteger state = new AtomicInteger(1);
        // The current number.
        private int curNum = 1;
        private int n;

        public FizzBuzz3(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {

            for(;;) {
                // Update the current state using CAS in orde to run the logic exclusively.
                while (!this.state.compareAndSet(1, 0)) {
                    // Alleviate the busy spin.
                    LockSupport.parkNanos(1L);
                }

                if (this.curNum > n) {
                    // Reset the state before return, then other waiting threads can terminate.
                    this.state.set(1);
                    return;
                }

                if ((this.curNum % 3 == 0) && (this.curNum % 5 != 0)) {
                    printFizz.run();
                    this.curNum++;
                }

                this.state.set(1);
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {

            for(;;) {
                while (!this.state.compareAndSet(1, 0)) {
                    LockSupport.parkNanos(1L);
                }

                if (this.curNum > n) {
                    this.state.set(1);
                    return;
                }

                if ((this.curNum % 3 != 0) && (this.curNum % 5 == 0)) {
                    printBuzz.run();
                    this.curNum++;
                }

                this.state.set(1);
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {

            for(;;) {
                while (!this.state.compareAndSet(1, 0)) {
                    LockSupport.parkNanos(1L);
                }

                if (this.curNum > n) {
                    this.state.set(1);
                    return;
                }

                if ((this.curNum % 3 == 0) && (this.curNum % 5 == 0)) {
                    printFizzBuzz.run();
                    this.curNum++;
                }

                this.state.set(1);
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {

            for(;;) {
                while (!this.state.compareAndSet(1, 0)) {

//                    public static void parkNanos(long nanos) {
//                        if (nanos > 0)
//                            UNSAFE.park(false, nanos);
//                    }

                    //LockSupport中的park() 和 unpark() 的作用分别是阻塞线程和解除阻塞线程，而且park()和unpark()不会遇到“Thread.suspend和Thread.resume所可能引发的死锁”问题。因为park()和unpark()有许可的存在；
                    //调用 park() 的线程和另一个试图将其 unpark() 的线程之间的竞争将保持活性。实现原理是利用java的unsafe接口调用本地方法实现。
                    //sleep()是单纯的在指定时间内让当前正在执行的线程暂停执行，但不会释放“锁标志”，使当前线程进入阻塞状态，在指定时间内不会执行。

                    LockSupport.parkNanos(1L);
                }

                if (this.curNum > n) {
                    this.state.set(1);
                    return;
                }

                if ((this.curNum % 3 != 0) && (this.curNum % 5 != 0)) {
                    printNumber.accept(this.curNum);
                    this.curNum++;
                }

                this.state.set(1);
            }
        }
    }


    public static void main(String[] args) {
//        _1195_FizzBuzz fizzBuzz = new _1195_FizzBuzz(20);
//        FizzBuzz fizzBuzz = new FizzBuzz(20);
//        FizzBuzz2 fizzBuzz = new FizzBuzz2(20);
        FizzBuzz3 fizzBuzz = new FizzBuzz3(20);

        new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.println("fizz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.println("buzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.println("fizzbuzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.number(value -> System.out.println(value));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
