package com.leetcode.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class _1114_PrintInOrder implements _1114_PrintInOrderInterface {

    //控制变量
    private int flag = 0;
    //定义Object对象为锁
    private Object lock = new Object();

    @Override
    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (lock){
            //如果flag不为0则让first线程等待，while循环控制first线程如果不满住条件就一直在while代码块中，防止出现中途跳入，执行下面的代码，其余线程while循环同理
            while( flag != 0){
                lock.wait();
            }
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            //定义成员变量为 1
            flag = 1;
            //唤醒其余所有的线程
            lock.notifyAll();
        }
    }

    @Override
    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock){
            //如果成员变量不为1则让二号等待
            while (flag != 1){
                lock.wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            //如果成员变量为 1 ，则代表first线程刚执行完，所以执行second，并且改变成员变量为 2
            flag = 2;
            //唤醒其余所有的线程
            lock.notifyAll();
        }
    }

    @Override
    public void third(Runnable printThird) throws InterruptedException {
        synchronized (lock){
            //如果flag不等于2 则一直处于等待的状态
            while (flag != 2){
                lock.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            //如果成员变量为 2 ，则代表second线程刚执行完，所以执行third，并且改变成员变量为 0
            printThird.run();
            flag = 0;
            lock.notifyAll();
        }
    }

    // 1. CountDownLatch
    // countDownLatch使一个线程等待其他线程各自执行完毕后再执行。
    // 通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
    public static class PrintInOrder1 implements _1114_PrintInOrderInterface {

        CountDownLatch stage1;
        CountDownLatch stage2;

        public PrintInOrder1() {
            stage1 = new CountDownLatch(1);
            stage2 = new CountDownLatch(1);
        }

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            stage1.countDown();
        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException {
            stage1.await();
            printSecond.run();
            stage2.countDown();
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException {
            stage2.await();
            printThird.run();
        }
    }

    // 2. Semaphore
    // 信号量Semaphore用于限制可以访问某些资源（物理或逻辑的）的线程数目，他维护了一个许可证集合，有多少资源需要限制就维护多少许可证集合，
    // 假如这里有N个资源，那就对应于N个许可证，同一时刻也只能有N个线程访问。一个线程获取许可证就调用acquire方法，用完了释放资源就调用release方法。
    public static class PrintInOrder2 implements _1114_PrintInOrderInterface {

        Semaphore stage1;
        Semaphore stage2;
        Semaphore stage3;

        public PrintInOrder2() {
            stage1 = new Semaphore(1);
            // release()释放一个许可，别忘了在finally中使用，多次调用该方法，会使信号量的许可数增加，达到动态扩展的效果，如：初始permits为1， 调用了两次release，最大许可会改变为3
            stage2 = new Semaphore(0);
            stage3 = new Semaphore(0);
        }

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            stage1.acquire();
            printFirst.run();
            stage2.release();
        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException {
            stage2.acquire();
            printSecond.run();
            stage3.release();
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException {
            stage3.acquire();
            printThird.run();
        }
    }

    // 3. Lock + Condition
    // Lock不是Java语言内置的, 而synchronized是在JVM层面上实现的,如果代码执行出现异常,JVM会自动释放锁,但是Lock不行,
    // 要保证锁一定会被释放,就必须将unLock放到finally{}中(手动释放);

    // 没有Lock之前，我们使用synchronized来控制同步，配合Object的wait()、notify()系列方法可以实现等待/通知模式。
    // 在Java SE5后，Java提供了Lock接口，相对于Synchronized而言，Lock提供了条件Condition，对线程的等待、唤醒操作更加详细和灵活。

    // 有时候获得锁的线程发现其某个条件不满足导致不能继续后面的业务逻辑，此时该线程只能先释放锁，等待条件满足。
    // 那可不可以不释放锁的等待呢？比如将await方法替换为sleep方法(sleep不会释放锁)，显然不行，因为等待的条件显然和共享的资源是有关的
    // 如果使用sleep(不释放锁)来等待而不是await(释放锁)来等待，则会导致某个条件永远满足不了
    public static class PrintInOrder3 implements _1114_PrintInOrderInterface {

        Lock lock = new ReentrantLock();
        volatile int stage = 1;
        Condition stage2 = lock.newCondition();
        Condition stage3 = lock.newCondition();

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            lock.lock();
            try {
                printFirst.run();
                stage = 2;
                stage2.signal();
            }finally {
                lock.unlock();
            }
        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException {
            lock.lock();
            try {
                while(stage!=2) {
                    stage2.await();
                }
                printSecond.run();
                stage = 3;
                stage3.signal();
            }finally {
                lock.unlock();
            }
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException {
            lock.lock();
            try {
                while(stage!=3) {
                    stage3.await();
                }
                printThird.run();
            }finally {
                lock.unlock();
            }
        }
    }

    // 4. 无锁, 只使用volatile
    // volatile能保证变量可见性和禁止指令重排序, 并不能保证变量的原子性。
    // 自增运算"a++"或"b++"之中，用Javap反编译这段代码后会得到如下代码清单，
    // 发现只有一行代码的b++方法在Class文件中是由以下4条字节码指令构成的，从字节码层面上很容易就分析出并发失败的原因了,
    // 当getstatic指令把a或b的值取到操作栈顶时，volatile关键字保证了b的值在此时是正确的，但是在执行iconst_1、 iadd这些指令的时候，
    // 其他线程可能已经把b的值加大了，而在操作栈顶的值就变成了过期的数据，所以putstatic指令执行后就可能把较小的b值同步回主内存之中。
    /*
        0：getstatic#13;//Field b
        3：iconst_1
        4：iadd
        5：putstatic#13;//Field b
    */

    // 什么时候使用volatile？
    //  (1) 运算结果并不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值。
    //  (2) 变量不需要与其他的状态变量共同参与不变约束。

    // 关于使用volatile和不适用volatile一样都可以比较有争议的几个观点
    //  (1)volatile的作用是，所修饰的field如果变化，则所有的读操作都能够看到，这个field直接就刷新到主存中了，就像同步一样，而且读取操作本身就在主存，
    // 非volatile修饰的field上的操作不会刷新到主存。以上的操作都是原子操作，而++这种操作是每次都要依赖于上一次的值，根本不是原子操作，
    // 换句话说，volatile要正常工作，至少所修饰的field不能依赖其他值，而volatile还没强大到影响操作原子性的地步，所以这论证意义不大。
    // 最后，如果安全要求高，首要选择就是synchronized。

    //  (2)不能加volatile，加了理论上可能出现线程二比线程一先执行，原因是volatile的作用是通过加屏障不让字节码执行重新排序，
    // 从而保证栈内的变量一旦被修改就会立马刷新到堆中再继续执行方法栈的其他代码，而一旦堆中的count变量更新了，
    // 线程二栈中的count也会马上得到更新从而开始执行，而这时候线程一可能还未执行完，那么就无法保证谁先执行打印方法

    //  (3)count是不是volatile变量不影响结果。影响的只会是时间。因为能count值修改的线程只会有一个，不影响。但是count如果是普通变量的话，
    // 在线程内的工作内存修改完后不是马上刷新回主内存的。所以后续线程会一直等待将count最新值刷新回主内存，使得后续线程内工作内存失效重新读取主内存count的最新值，
    // 从而跳出for循环。而volatile变量在修改完后回马上刷新回主内存，其他线程也就马上能得到最新的值了，节省等待刷新主内存的时间。
    public static class PrintInOrder4 implements _1114_PrintInOrderInterface {
        volatile int count = 1;

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            count = 2;
        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException {
            while(count !=2) {}
            printSecond.run();
            count = 3;
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException {
            while(count !=3) {}
            printThird.run();
        }

    }

    // 5. CyclicBarrier
    // CyclicBarrier的字面意思是可循环使用(Cyclic)的屏障(Barrier)。
    // 作用是让一组线程到达一个屏障(也可以叫同步点)时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续运行。
    // 默认的构造方法是CyclicBarrier(int parties)，其参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，
    // 然后当前线程被阻塞, CyclicBarrier可以用于多线程计算数据，最后合并计算结果的场景。
    public static class PrintInOrder5 implements _1114_PrintInOrderInterface{
        CyclicBarrier cb1 = new CyclicBarrier(2);
        CyclicBarrier cb2 = new CyclicBarrier(2);

        @Override
        public void first(Runnable printFirst) throws InterruptedException {
            printFirst.run();
            try {
                cb1.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void second(Runnable printSecond) throws InterruptedException{
            try {
                cb1.await();
                printSecond.run();
                cb2.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void third(Runnable printThird) throws InterruptedException{
            try {
                cb2.await();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            printThird.run();
        }
    }

    //##########################################test分割线###############################################
    private static void testSemaphore() {
        Semaphore semaphore = new Semaphore(1);
        semaphore.release();
        semaphore.release();
        System.out.println(semaphore.availablePermits());
    }

    private int a = 0;
    private volatile int b = 0;
    // 测试的时候线程总数和自增数应该尽量大
    private void testVolatile() throws InterruptedException{
        List<Thread> ts = new ArrayList<Thread>(600);

        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    a++;
                    b++;
                }
            });
            ts.add(t);
        }

        for (Thread t : ts) {
            t.start();
        }
        // 等待所有线程执行完成
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(a); //364648
        System.out.println(b); //363308 volatile并不能保证原子性，这里可以使用原子类AtomicInteger
    }

    public static void runner(_1114_PrintInOrderInterface order) {
        new Thread(() -> {
            try {
                order.first(() -> System.out.println("first"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                order.second(() -> System.out.println("second"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                order.third(() -> System.out.println("third"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void main(String[] args) {
        runner(new _1114_PrintInOrder());
        runner(new PrintInOrder1());
        runner(new PrintInOrder2());
        runner(new PrintInOrder3());
        runner(new PrintInOrder4());
        runner(new PrintInOrder5());

//        try {
//            new _1114_PrintInOrder().testVolatile();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
