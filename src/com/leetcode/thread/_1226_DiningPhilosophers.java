package com.leetcode.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/*
    这道题本质上其实是想考察如何避免死锁。
    易知：当 55 个哲学家都拿着其左边(或右边)的叉子时，会进入死锁。

    PS：死锁的 4 个必要条件：
        (1)互斥条件：一个资源每次只能被一个进程使用，即在一段时间内某 资源仅为一个进程所占有。此时若有其他进程请求该资源，则请求进程只能等待。
        (2)请求与保持条件：进程已经保持了至少一个资源，但又提出了新的资源请求，而该资源 已被其他进程占有，此时请求进程被阻塞，但对自己已获得的资源保持不放。
        (3)不可剥夺条件:进程所获得的资源在未使用完毕之前，不能被其他进程强行夺走，即只能 由获得该资源的进程自己来释放（只能是主动释放)。
        (4)循环等待条件: 若干进程间形成首尾相接循环等待资源的关系。

    [1] 最多只允许4个哲学家去持有叉子，可保证至少有1个哲学家能吃上意大利面(即获得到2个叉子)。
    因为最差情况下是：4个哲学家都各自持有1个叉子，此时还剩余1个叉子可供使用，这4个哲学家中必然有1人能获取到这个剩余的1个叉子，从而手持2个叉子，可以吃意大利面。
    即：4个人中，1个人有2个叉子，3个人各持1个叉子，共计5个叉子。

    [2] 既然最多只允许4个哲学家去持有叉子，那么如果只允许3个哲学家去持有叉子是否可行呢？
    当然可行，3个哲学家可以先都各自持有1把叉子，此时还剩余2把叉子；接下来，这3个哲学家中必有2人能获取到这剩余的2把叉子，从而手持2把叉子，可以吃意大利面。而必然剩余1人只能持有1把叉子。
    即：3个人中，2个人各自持有2个叉子，1个人持有1个叉子，共计5个叉子。

    因此，如果只允许4个哲学家去持有叉子，在头1轮中只能有1人能吃上意大利面；如果只允许3个哲学家去持有叉子，在头一轮中能有2个人能吃上意大利面。直觉上来讲，一轮能有2个人完成吃面 在时间上 显然优于 一轮只有1个人吃面。

    用Semaphore去实现上述的限制：Semaphore eatLimit = new Semaphore(4);
    一共有5个叉子，视为5个ReentrantLock，并将它们全放入1个数组中。

    给叉子和哲学家都编号为 0, 1, 2, 3, 4, 哲学家i左右叉子编号分别是(i + 1) % 5 和 i
*/


public class _1226_DiningPhilosophers {
    //1个Fork视为1个ReentrantLock，5个叉子即5个ReentrantLock，将其都放入数组中
    private ReentrantLock[] lockList = {new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()};

    //限制 最多只有4个哲学家去持有叉子
    private Semaphore eatLimit = new Semaphore(4);

    public _1226_DiningPhilosophers() {

    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {

        int leftFork = (philosopher + 1) % 5;	//左边的叉子 的编号
        int rightFork = philosopher;	//右边的叉子 的编号

        eatLimit.acquire();	//限制的人数 -1

        lockList[leftFork].lock();	//拿起左边的叉子
        lockList[rightFork].lock();	//拿起右边的叉子

        pickLeftFork.run();	//拿起左边的叉子 的具体执行 [i, 1, 1]
        pickRightFork.run(); //拿起右边的叉子 的具体执行 [i, 2, 1]

        eat.run();	//吃意大利面 的具体执行 [i, 0, 3]

        putLeftFork.run();	//放下左边的叉子 的具体执行 [i, 1, 2]
        putRightFork.run();	//放下右边的叉子 的具体执行 [i, 2, 2]

        lockList[leftFork].unlock();	//放下左边的叉子
        lockList[rightFork].unlock();	//放下右边的叉子

        eatLimit.release();//限制的人数 +1
    }

    /*
    方案2:
        设置 1个临界区以实现1个哲学家 “同时”拿起左右2把叉子的效果。
        即进入临界区之后，保证成功获取到左右2把叉子并执行相关代码后，才退出临界区。

        乍看可能认为方法2就是“只让1个哲学家就餐”的思路，无需将叉子视为ReentrantLock, 但是2者之间还是有细微的差别,
        因为方法2是在成功拿起左右叉子之后就退出临界区，而“只让1个哲学家就餐”是在拿起左右叉子 + 吃意面 + 放下左右叉子 一套流程走完之后才退出临界区。

        前者的情况可大概分为2种，举具体例子说明：
            (1)1号哲学家拿起左右叉子(1号叉子 + 2号叉子)后就退出临界区，此时4号哲学家成功挤进临界区，他也成功拿起了左右叉子(0号叉子和4号叉子)，然后就退出临界区。
            (2)1号哲学家拿起左右叉子(1号叉子 + 2号叉子)后就退出临界区，此时2号哲学家成功挤进临界区，他需要拿起2号叉子和3号叉子，
                但2号叉子有一定的概率还被1号哲学家持有(1号哲学家意面还没吃完)，因此2号哲学家进入临界区后还需要等待2号叉子。
                至于3号叉子，根本没其他人跟2号哲学家争夺，因此可以将该种情况视为“2号哲学家只拿起了1只叉子，在等待另1只叉子”的情况。

        总之，第1种情况即先后进入临界区的2位哲学家的左右叉子不存在竞争情况，因此先后进入临界区的2位哲学家进入临界区后都不用等待叉子，直接就餐。
        此时可视为2个哲学家在同时就餐(当然前1个哲学家有可能已经吃完了，但姑且当作是2个人同时就餐)。

        第2种情况即先后进入临界区的2位哲学家的左右叉子存在竞争情况(说明这2位哲学家的编号相邻)，因此后进入临界区的哲学家还需要等待1只叉子，才能就餐。此时可视为只有1个哲学家在就餐。

        至于“只允许1个哲学家就餐”的代码，很好理解，每次严格地只让1个哲学家就餐，由于过于严格，以至于都不需要将叉子视为ReentrantLock。

        方法2有一定的概率是“并行”，“只允许1个哲学家就餐”是严格的“串行”。
    */
    static class DiningPhilosophers {
        //1个Fork视为1个ReentrantLock，5个叉子即5个ReentrantLock，将其都放入数组中
        private ReentrantLock[] lockList = {new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock()};

        //让 1个哲学家可以 “同时”拿起2个叉子(搞个临界区)
        private ReentrantLock pickBothForks = new ReentrantLock();

        public DiningPhilosophers() {

        }

        // call the run() method of any runnable to execute its code
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {

            int leftFork = (philosopher + 1) % 5;	//左边的叉子 的编号
            int rightFork = philosopher;	//右边的叉子 的编号

            pickBothForks.lock();	//进入临界区

            lockList[leftFork].lock();	//拿起左边的叉子
            lockList[rightFork].lock();	//拿起右边的叉子

            pickLeftFork.run();	//拿起左边的叉子 的具体执行
            pickRightFork.run();	//拿起右边的叉子 的具体执行

            pickBothForks.unlock();	//退出临界区

            eat.run();	//吃意大利面 的具体执行

            putLeftFork.run();	//放下左边的叉子 的具体执行
            putRightFork.run();	//放下右边的叉子 的具体执行

            lockList[leftFork].unlock();	//放下左边的叉子
            lockList[rightFork].unlock();	//放下右边的叉子
        }
    }

    /*
     方案3:
        本题的本质是考察如何避免死锁。

        当5个哲学家都左手持有其左边的叉子 或 当5个哲学家都右手持有其右边的叉子时，会发生死锁。故只需设计1个避免发生上述情况发生的策略即可。
        即可以让一部分哲学家优先去获取其左边的叉子，再去获取其右边的叉子；再让剩余哲学家优先去获取其右边的叉子，再去获取其左边的叉子。
    */

    static class DiningPhilosophers2 {
        //1个Fork视为1个ReentrantLock，5个叉子即5个ReentrantLock，将其都放入数组中
        private ReentrantLock[] lockList = {new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock()};

        public DiningPhilosophers2() {

        }

        // call the run() method of any runnable to execute its code
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {

            int leftFork = (philosopher + 1) % 5;    //左边的叉子 的编号
            int rightFork = philosopher;    //右边的叉子 的编号

            //编号为偶数的哲学家，优先拿起左边的叉子，再拿起右边的叉子
            if (philosopher % 2 == 0) {
                lockList[leftFork].lock();    //拿起左边的叉子
                lockList[rightFork].lock();    //拿起右边的叉子
            }
            //编号为奇数的哲学家，优先拿起右边的叉子，再拿起左边的叉子
            else {
                lockList[rightFork].lock();    //拿起右边的叉子
                lockList[leftFork].lock();    //拿起左边的叉子
            }

            pickLeftFork.run();    //拿起左边的叉子 的具体执行
            pickRightFork.run();    //拿起右边的叉子 的具体执行

            eat.run();    //吃意大利面 的具体执行

            putLeftFork.run();    //放下左边的叉子 的具体执行
            putRightFork.run();    //放下右边的叉子 的具体执行

            lockList[leftFork].unlock();    //放下左边的叉子
            lockList[rightFork].unlock();    //放下右边的叉子
        }
    }


    // 指定叉子：{1 : 左边, 2 : 右边}.
    // 指定行为：{1 : 拿起, 2 : 放下, 3 : 吃面}。
    public static void main(String[] args) {

//        _1226_DiningPhilosophers diningPhilosophers = new _1226_DiningPhilosophers();
//        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();
        DiningPhilosophers2 diningPhilosophers = new DiningPhilosophers2();

        for (int i = 0; i < 5; i++) {
            final int philosopher = i;
            new Thread(() -> {
                try {
                    diningPhilosophers.wantsToEat(philosopher,
                            () -> System.out.println(String.format("[%d, 1, 1]", philosopher)),
                            () -> System.out.println(String.format("[%d, 2, 1]", philosopher)),
                            () -> System.out.println(String.format("[%d, 0, 3]", philosopher)),
                            () -> System.out.println(String.format("[%d, 1, 2]", philosopher)),
                            () -> System.out.println(String.format("[%d, 2, 2]", philosopher)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }
}
