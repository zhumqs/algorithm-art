package com.leetcode.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class _1116_ZeroEvenOdd {

    // 1. lock + condition
    private int n;

    private volatile int start = 1;

    private volatile int who;
    private Lock lock = new ReentrantLock();
    private Condition zero = lock.newCondition();
    private Condition even = lock.newCondition();
    private Condition odd = lock.newCondition();

    public _1116_ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (start <= n) {
                if (who!=0) {
                    zero.await();
                }
                printNumber.accept(0);
                if (start % 2 == 0) {
                    who=2;
                    even.signal();
                } else {
                    who=1;
                    odd.signal();
                }
                zero.await();
            }
            odd.signal();
            even.signal();
        } finally {
            lock.unlock();
        }
    }

    //偶数
    public void even(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (start <= n) {
                if (who!=2) {
                    even.await();
                } else {
                    printNumber.accept(start++);
                    who=0;
                    zero.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    //基数
    public void odd(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try {
            while (start <= n) {
                if (who!=1) {
                    odd.await();
                } else {
                    printNumber.accept(start++);
                    who=0;
                    zero.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // 2. Semaphore
    public static class ZeroEvenOdd1 {
        private int n;

        public ZeroEvenOdd1(int n) {
            this.n = n;
        }

        Semaphore z = new Semaphore(1);
        Semaphore e = new Semaphore(0);
        Semaphore o = new Semaphore(0);

        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i=0; i<n;i++) {
                z.acquire();
                printNumber.accept(0);
                if((i&1)==0) {
                    o.release();
                }else {
                    e.release();
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i=2; i<=n; i+=2) {
                e.acquire();
                printNumber.accept(i);
                z.release();
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i=1; i<=n; i+=2) {
                o.acquire();
                printNumber.accept(i);
                z.release();
            }
        }
    }

    // 3. 无锁 volatile
    public static class ZeroEvenOdd2 {
        private int n;

        public ZeroEvenOdd2(int n) {
            this.n = n;
        }

        volatile int stage = 0;

        public void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i=0;i<n;i++) {
                while(stage>0) {};
                printNumber.accept(0);
                if((i&1)==0) {
                    stage = 1;
                }else {
                    stage = 2;
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for(int i=2; i<=n; i+=2) {
                while(stage!=2) {};
                printNumber.accept(i);
                stage = 0;
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i=1; i<=n; i+=2) {
                while(stage!=1) {};
                printNumber.accept(i);
                stage = 0;
            }
        }
    }

    // 4. synchronized
    public static class ZeroEvenOdd3 {
        private int n;
        private int num = 0;

        public ZeroEvenOdd3(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    while (num % 2 != 0) {
                        this.wait();
                    }
                    printNumber.accept(0);
                    num++;
                    this.notifyAll();
                }
            }

        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            for (int j = 2; j <= n; j = j + 2) {
                synchronized (this) {
                    while (num % 2 == 0 || num % 4 != 3) {
                        this.wait();
                    }
                    printNumber.accept(j);
                    num++;
                    this.notifyAll();
                }
            }

        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            for (int j = 1; j <= n; j = j + 2) {
                synchronized (this) {
                    while (num % 2 == 0 || num % 4 != 1) {
                        this.wait();
                    }
                    printNumber.accept(j);
                    num++;
                    this.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd2 odd2 = new ZeroEvenOdd2(10);

        new Thread(() -> {
            try {
                odd2.zero(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                odd2.odd(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                odd2.even(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        ZeroEvenOdd3 odd3 = new ZeroEvenOdd3(10);

        new Thread(() -> {
            try {
                odd2.zero(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                odd2.odd(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                odd2.even(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
