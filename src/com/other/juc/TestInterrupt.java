package com.other.juc;

public class TestInterrupt {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("当前线程被中断了!");
                    // 不return 线程仍会一直运行
                    return;
                } else {
                    System.out.println("当前线程仍在运行!");
                    /*
                       如果在sleep()中被打断会清除中断标志, 重新设置标志位, 运行结果
                            当前线程仍在运行!
                            线程中断了，程序到这里了
                            线程在睡眠中被打断了!
                            当前线程被中断了!
                    */
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // 如果睡眠中被打断, 重新设置中断标志位
                        System.out.println("线程在睡眠中被打断了!");
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }, "testThread");

        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        /*
            没有设置睡眠的正常运行结果:
                当前线程仍在运行!
                当前线程仍在运行!
                当前线程仍在运行!
                当前线程仍在运行!
                线程中断了，程序到这里了
                当前线程被中断了!
         */
        System.out.println("线程中断了，程序到这里了");
    }
}
