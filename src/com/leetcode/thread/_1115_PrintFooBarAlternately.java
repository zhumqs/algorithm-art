package com.leetcode.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//交替打印的场景下有点类似红绿灯交替变换的情境, 信号量是首选
public class _1115_PrintFooBarAlternately {
    // 1. Semaphore
    private int n;

    public _1115_PrintFooBarAlternately() {}

    public _1115_PrintFooBarAlternately(int n) {
        this.n = n;
    }

    Semaphore foo = new Semaphore(1);
    Semaphore bar = new Semaphore(0);

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            foo.acquire();
            printFoo.run();
            bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            bar.acquire();
            printBar.run();
            foo.release();
        }
    }

    // 2. Lock
    static class FooBar1 extends _1115_PrintFooBarAlternately{
        private int n;

        public FooBar1(int n) {
            this.n = n;
        }

        Lock lock = new ReentrantLock(true);
        volatile boolean permitFoo = true;

        @Override
        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; ) {
                lock.lock();
                try {
                    if(permitFoo) {
                        printFoo.run();
                        i++;
                        permitFoo = false;
                    }
                }finally {
                    lock.unlock();
                }
            }
        }

        @Override
        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; ) {
                lock.lock();
                try {
                    if(!permitFoo) {
                        printBar.run();
                        i++;
                        permitFoo = true;
                    }
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    // 3. 无锁volatile
    static class FooBar2 extends _1115_PrintFooBarAlternately{
        private int n;

        public FooBar2(int n) {
            this.n = n;
        }

        volatile boolean permitFoo = true;

        @Override
        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; ) {
                if(permitFoo) {
                    printFoo.run();
                    i++;
                    permitFoo = false;
                }
            }
        }

        @Override
        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; ) {
                if(!permitFoo) {
                    printBar.run();
                    i++;
                    permitFoo = true;
                }
            }
        }
    }

    // 4. CyclicBarrier
    static class FooBar3 extends _1115_PrintFooBarAlternately{
        private int n;

        public FooBar3(int n) {
            this.n = n;
        }

        CyclicBarrier cb = new CyclicBarrier(2);
        volatile boolean fin = true;

        @Override
        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                while(!fin) {
                }
                printFoo.run();
                fin = false;
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                try {
                    cb.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                printBar.run();
                fin = true;
            }
        }
    }

    // 5. synchronized
    static class FooBar4 extends _1115_PrintFooBarAlternately{
        private int n;
        //控制变量
        private int flag = 0;
        //定义Object对象为锁
        private Object lock = new Object();

        public FooBar4(int n) {
            this.n = n;
        }

        @Override
        public void foo(Runnable printFoo) throws InterruptedException {
            synchronized(lock) {
                for (int i = 0; i < n; ) {
                    while (flag != 0){
                        lock.wait();
                    }
                    printFoo.run();
                    flag = 1;
                    i++;
                    lock.notifyAll();
                }
            }

        }

        @Override
        public void bar(Runnable printBar) throws InterruptedException {
            synchronized(lock) {
                for (int i = 0; i < n; ) {
                    while (flag != 1){
                        lock.wait();
                    }
                    printBar.run();
                    flag = 0;
                    i++;
                    lock.notifyAll();
                }
            }
        }
    }


    public static void test(_1115_PrintFooBarAlternately o) {
        new Thread(() -> {
            try {
                o.foo(() -> System.out.println("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                o.bar(() -> System.out.println("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // main()是静态方法, 所以main里要使用内部类, 内部类需要定义成static的
    // 一般情况下,类没有实例化就不能访问, 如果方法前有关键字static,即使未创建类实例,也可以通过类名直接访问.
    // 由于main作为程序的入口, JVM需要在其它流程之前调用main()方法, 所以main()方法不应依赖于要创建的任何类的实例,必须声明为static.
    public static void main(String[] args) {
//        test(new _1115_PrintFooBarAlternately(10));
//        test(new FooBar1(10));
//        test(new FooBar2(10));
//        test(new FooBar3(10));
        test(new FooBar4(10));
        // 在类外面调用main方法直接用类名即可
        _1114_PrintInOrder.main(new String[]{"xx"});
    }
}
